package cartella.clinica.back_end_capstone.studi;

import cartella.clinica.back_end_capstone.GiorniApertura.GiornoApertura;
import cartella.clinica.back_end_capstone.GiorniApertura.GiornoAperturaRepository;
import cartella.clinica.back_end_capstone.GiorniApertura.GiornoAperturaRequest;
import cartella.clinica.back_end_capstone.GiorniApertura.GiornoAperturaResponse;
import cartella.clinica.back_end_capstone.appuntamenti.Appuntamento;
import cartella.clinica.back_end_capstone.appuntamenti.AppuntamentoRepository;
import cartella.clinica.back_end_capstone.medici.Medico;
import cartella.clinica.back_end_capstone.pazienti.Paziente;
import cartella.clinica.back_end_capstone.pazienti.PazienteRepository;
import cartella.clinica.back_end_capstone.services.EmailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
public class StudioService {

    @Autowired
    private StudioRepository studioRepository;

    @Autowired
    private GiornoAperturaRepository giornoAperturaRepository;

    @Autowired
    private AppuntamentoRepository appuntamentoRepository;

    @Autowired
    private PazienteRepository pazienteRepository;

    @Autowired
    private EmailService emailService;

    private static final LocalTime DEFAULT_INIZIO_MATTINA = LocalTime.of(8, 0);
    private static final LocalTime DEFAULT_FINE_MATTINA = LocalTime.of(13, 0);
    private static final LocalTime DEFAULT_INIZIO_POMERIGGIO = LocalTime.of(14, 0);
    private static final LocalTime DEFAULT_FINE_POMERIGGIO = LocalTime.of(19, 0);

    public Studio getByMedico(Medico medico) {
        return studioRepository.findByMedico(medico).orElseGet(() -> {
            Studio studio = new Studio();
            studio.setMedico(medico);
            studio.setNome("Studio Medico");
            studio.setIndirizzo("Indirizzo non impostato");
            return studioRepository.save(studio);
        });
    }

    public List<GiornoAperturaResponse> getGiorniApertura(Medico medico) {
        Studio studio = getByMedico(medico);
        inizializzaGiorniSeVuoti(studio, medico);

        return giornoAperturaRepository.findByStudio_Medico(medico).stream()
                .sorted(Comparator.comparing(GiornoApertura::getGiorno))
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void aggiornaGiornoApertura(Medico medico, List<GiornoAperturaRequest> giorniRequest) {
        Studio studio = getByMedico(medico);

        for (GiornoAperturaRequest req : giorniRequest) {
            DayOfWeek giorno = DayOfWeek.valueOf(req.getGiorno().toUpperCase());
            GiornoApertura giornoEsistente = getOrCreateGiorno(medico, giorno, studio);

            boolean chiusoPrima = giornoEsistente.isChiuso();
            boolean chiusoDopo = req.isChiuso();

            giornoEsistente.setInizioMattina(req.getInizioMattina());
            giornoEsistente.setFineMattina(req.getFineMattina());
            giornoEsistente.setInizioPomeriggio(req.getInizioPomeriggio());
            giornoEsistente.setFinePomeriggio(req.getFinePomeriggio());
            giornoEsistente.setChiuso(chiusoDopo);

            giornoAperturaRepository.save(giornoEsistente);

            if (!chiusoPrima && chiusoDopo) {
                LocalDate dataChiusura = LocalDate.now().with(TemporalAdjusters.nextOrSame(giorno));
                cancellaAppuntamentiEAvvisaPazienti(medico, dataChiusura, studio);
            }
        }
    }

    private void cancellaAppuntamentiEAvvisaPazienti(Medico medico, LocalDate dataChiusura, Studio studio) {
        LocalDateTime inizioGiorno = dataChiusura.atStartOfDay();
        LocalDateTime fineGiorno = dataChiusura.atTime(LocalTime.MAX);

        List<Appuntamento> appuntamentiDaCancellare = appuntamentoRepository.findByDataOraAppuntamentoBetweenAndPaziente_Medico(
                inizioGiorno, fineGiorno, medico);

        appuntamentoRepository.deleteAll(appuntamentiDaCancellare);

        List<Paziente> pazientiDelMedico = pazienteRepository.findByMedico(medico);

        String dataFormattata = dataChiusura.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        for (Paziente paziente : pazientiDelMedico) {
            emailService.sendMailChiusuraStudio(
                    paziente.getUtente().getEmail(),
                    studio.getNome(),
                    dataFormattata,
                    medico.getUtente().getNome(),
                    medico.getUtente().getCognome()
            );
        }
    }

    public void updateFromRequest(Medico medico, StudioRequest req) {
        Studio studio = getByMedico(medico);
        studio.setNome(req.getNome());
        studio.setIndirizzo(req.getIndirizzo());
        studioRepository.save(studio);
    }

    private void inizializzaGiorniSeVuoti(Studio studio, Medico medico) {
        if (giornoAperturaRepository.findByStudio_Medico(medico).isEmpty()) {
            for (DayOfWeek giorno : DayOfWeek.values()) {
                GiornoApertura g = new GiornoApertura();
                g.setStudio(studio);
                g.setGiorno(giorno);
                g.setInizioMattina(DEFAULT_INIZIO_MATTINA);
                g.setFineMattina(DEFAULT_FINE_MATTINA);
                g.setInizioPomeriggio(DEFAULT_INIZIO_POMERIGGIO);
                g.setFinePomeriggio(DEFAULT_FINE_POMERIGGIO);
                g.setChiuso(giorno == DayOfWeek.SATURDAY || giorno == DayOfWeek.SUNDAY);
                giornoAperturaRepository.save(g);
            }
        }
    }

    private GiornoApertura getOrCreateGiorno(Medico medico, DayOfWeek giorno, Studio studio) {
        return giornoAperturaRepository.findByStudio_MedicoAndGiorno(medico, giorno)
                .orElseGet(() -> {
                    GiornoApertura nuovo = new GiornoApertura();
                    nuovo.setStudio(studio);
                    nuovo.setGiorno(giorno);
                    return nuovo;
                });
    }

    private GiornoAperturaResponse toResponse(GiornoApertura g) {
        return new GiornoAperturaResponse(
                g.getGiorno().name(),
                g.getGiorno().getDisplayName(java.time.format.TextStyle.FULL, Locale.ITALY),
                g.getInizioMattina(),
                g.getFineMattina(),
                g.getInizioPomeriggio(),
                g.getFinePomeriggio(),
                g.isChiuso()
        );
    }

    public StudioResponse getStudioPaziente(Paziente paziente) {
        Medico medico = paziente.getMedico();
        if (medico == null) {
            throw new RuntimeException("Medico associato al paziente non trovato");
        }
        Studio studio = studioRepository.findByMedico(medico)
                .orElseThrow(() -> new RuntimeException("Studio non trovato"));

        List<GiornoAperturaResponse> giorni = giornoAperturaRepository.findByStudio_Medico(medico).stream()
                .sorted(Comparator.comparing(GiornoApertura::getGiorno))
                .map(this::toResponse)
                .toList();

        return new StudioResponse(
                studio.getNome(),
                studio.getIndirizzo(),
                medico.getUtente().getTelefonoFisso(),
                medico.getUtente().getNome(),
                medico.getUtente().getCognome(),
                medico.getUtente().getEmail(),
                medico.getUtente().getTelefonoCellulare(),
                medico.getSpecializzazione(),
                giorni
        );
    }
}






