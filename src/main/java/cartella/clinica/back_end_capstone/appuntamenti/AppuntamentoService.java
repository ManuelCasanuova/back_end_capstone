package cartella.clinica.back_end_capstone.appuntamenti;

import cartella.clinica.back_end_capstone.GiorniApertura.GiornoApertura;
import cartella.clinica.back_end_capstone.GiorniApertura.GiornoAperturaRepository;
import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.auth.AppUserRepository;
import cartella.clinica.back_end_capstone.auth.Role;
import cartella.clinica.back_end_capstone.pazienti.Paziente;
import cartella.clinica.back_end_capstone.pazienti.PazienteRepository;
import cartella.clinica.back_end_capstone.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Validated
public class AppuntamentoService {

    @Autowired
    private AppuntamentoRepository appuntamentoRepository;

    @Autowired
    private PazienteRepository pazienteRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private GiornoAperturaRepository giornoAperturaRepository;

    @Autowired
    private EmailService emailService;

    private Appuntamento toEntity(AppuntamentoRequest appuntamentoRequest) {
        Appuntamento appuntamento = new Appuntamento();
        appuntamento.setDataOraAppuntamento(appuntamentoRequest.getDataOraAppuntamento());
        appuntamento.setMotivoRichiesta(appuntamentoRequest.getMotivoRichiesta());
        appuntamento.setPaziente(pazienteRepository.findById(appuntamentoRequest.getPazienteId()).orElseThrow());
        return appuntamento;
    }

    private AppuntamentoResponse toResponse(Appuntamento appuntamento) {
        return new AppuntamentoResponse(
                appuntamento.getId(),
                appuntamento.getDataOraAppuntamento(),
                appuntamento.getMotivoRichiesta(),
                appuntamento.getPaziente().getId(),
                appuntamento.getPaziente().getUtente().getNome(),
                appuntamento.getPaziente().getUtente().getCognome(),
                appuntamento.getPaziente().getUtente().getAvatar()
        );
    }

    public AppuntamentoResponse createAppuntamento(AppuntamentoRequest appuntamentoRequest) {
        LocalDateTime dataOra = appuntamentoRequest.getDataOraAppuntamento();
        LocalDate giorno = dataOra.toLocalDate();
        long count = countAppuntamentiPerGiorno(giorno);

        if (count >= 8) {
            throw new IllegalStateException("Limite massimo di 8 appuntamenti per giorno raggiunto");
        }

        Paziente paziente = pazienteRepository.findById(appuntamentoRequest.getPazienteId())
                .orElseThrow(() -> new RuntimeException("Paziente non trovato"));

        if (!isOrarioDisponibilePerStudio(paziente, dataOra)) {
            throw new IllegalStateException("Orario non disponibile: lo studio è chiuso o l'orario è fuori fascia");
        }

        if (!isSlotDisponibile(dataOra)) {
            throw new IllegalStateException("Slot non disponibile");
        }

        Appuntamento appuntamento = toEntity(appuntamentoRequest);
        appuntamento = appuntamentoRepository.save(appuntamento);

        emailService.sendMailConfermaAppuntamento(
                paziente.getUtente().getEmail(),
                paziente.getUtente().getNome(),
                appuntamento.getDataOraAppuntamento(),
                paziente.getMedico().getUtente().getNome(),
                paziente.getMedico().getUtente().getCognome()
        );

        return toResponse(appuntamento);
    }

    public boolean isSlotDisponibile(LocalDateTime dataOra) {
        return appuntamentoRepository.findByDataOraAppuntamento(dataOra).isEmpty();
    }

    private boolean isOrarioDisponibilePerStudio(Paziente paziente, LocalDateTime dataOra) {
        if (paziente.getMedico() == null) return false;

        DayOfWeek giorno = dataOra.getDayOfWeek();
        LocalTime orario = dataOra.toLocalTime();

        GiornoApertura giornoApertura = giornoAperturaRepository
                .findByStudio_MedicoAndGiorno(paziente.getMedico(), giorno)
                .orElseThrow(() -> new RuntimeException("Giorno non configurato per lo studio"));

        if (giornoApertura.isChiuso()) return false;

        boolean inMattina = giornoApertura.getInizioMattina() != null &&
                giornoApertura.getFineMattina() != null &&
                !orario.isBefore(giornoApertura.getInizioMattina()) &&
                orario.isBefore(giornoApertura.getFineMattina());

        boolean inPomeriggio = giornoApertura.getInizioPomeriggio() != null &&
                giornoApertura.getFinePomeriggio() != null &&
                !orario.isBefore(giornoApertura.getInizioPomeriggio()) &&
                orario.isBefore(giornoApertura.getFinePomeriggio());

        return inMattina || inPomeriggio;
    }

    public List<AppuntamentoResponse> findAllAppuntamenti() {
        List<Appuntamento> appuntamenti = appuntamentoRepository.findAll(Sort.by("dataOraAppuntamento"));
        return appuntamenti.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public AppuntamentoResponse findAppuntamentoById(Long id) {
        Appuntamento appuntamento = appuntamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appuntamento non trovato"));
        return toResponse(appuntamento);
    }

    public List<AppuntamentoResponse> findByPazienteId(Long pazienteId) {
        List<Appuntamento> lista = appuntamentoRepository.findByPaziente_Id(pazienteId);

        return lista.stream().map(a -> {
            AppuntamentoResponse res = new AppuntamentoResponse();
            res.setId(a.getId());
            res.setDataOraAppuntamento(a.getDataOraAppuntamento());
            res.setMotivoRichiesta(a.getMotivoRichiesta());
            res.setPazienteId(a.getPaziente().getId());

            if (a.getPaziente().getUtente() != null) {
                res.setNome(a.getPaziente().getUtente().getNome());
                res.setCognome(a.getPaziente().getUtente().getCognome());
                res.setAvatar(a.getPaziente().getUtente().getAvatar());
            }

            return res;
        }).toList();
    }

    public AppuntamentoResponse updateAppuntamento(Long id, AppuntamentoResponse appuntamentoRequest, AppUser adminLoggato) {
        LocalDateTime dataOra = appuntamentoRequest.getDataOraAppuntamento();

        Appuntamento appuntamento = appuntamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appuntamento non trovato"));

        boolean isAdmin = adminLoggato.getRoles().contains(Role.ROLE_ADMIN);
        if (!isAdmin) {
            throw new RuntimeException("Non sei autorizzato a modificare l'appuntamento");
        }

        boolean slotOccupatoDaAltri = appuntamentoRepository.findByDataOraAppuntamento(dataOra).stream()
                .anyMatch(a -> !a.getId().equals(id));
        if (slotOccupatoDaAltri) {
            throw new IllegalStateException("Slot già occupato da un altro appuntamento");
        }

        if (!isOrarioDisponibilePerStudio(appuntamento.getPaziente(), dataOra)) {
            throw new IllegalStateException("Orario non disponibile per lo studio");
        }

        appuntamento.setDataOraAppuntamento(dataOra);
        appuntamento.setMotivoRichiesta(appuntamentoRequest.getMotivoRichiesta());
        appuntamento.setPaziente(pazienteRepository.findById(appuntamentoRequest.getPazienteId())
                .orElseThrow(() -> new RuntimeException("Paziente non trovato")));

        appuntamento = appuntamentoRepository.save(appuntamento);
        return toResponse(appuntamento);
    }

    public void deleteAppuntamento(Long id) {
        Appuntamento appuntamento = appuntamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appuntamento non trovato"));
        appuntamentoRepository.delete(appuntamento);
    }

    public Page<AppuntamentoResponse> filterAppuntamento(AppuntamentoFilter filter, Pageable pageable) {
        Specification<Appuntamento> spec = AppuntamentoSpecification.appuntamentoFilter(filter);
        Page<Appuntamento> page = appuntamentoRepository.findAll(spec, pageable);
        return page.map(this::toResponse);
    }

    public long countAppuntamentiPerGiorno(LocalDate giorno) {
        LocalDateTime inizioGiorno = giorno.atStartOfDay();
        LocalDateTime fineGiorno = giorno.atTime(LocalTime.MAX);
        return appuntamentoRepository.countByDataOraAppuntamentoBetween(inizioGiorno, fineGiorno);
    }
}

