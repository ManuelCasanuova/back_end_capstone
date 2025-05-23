package cartella.clinica.back_end_capstone.appuntamenti;




import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.auth.AppUserRepository;
import cartella.clinica.back_end_capstone.auth.Role;
import cartella.clinica.back_end_capstone.pazienti.PazienteRepository;
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
        LocalDate giorno = appuntamentoRequest.getDataOraAppuntamento().toLocalDate();
        LocalDateTime dataOra = appuntamentoRequest.getDataOraAppuntamento();
        long count = countAppuntamentiPerGiorno(giorno);

        if (count >= 8) {
            throw new IllegalStateException("Limite massimo di 8 appuntamenti per giorno raggiunto");
        }

        if (!isSlotValido(dataOra)) {
            throw new IllegalStateException("Orario non valido per la prenotazione");
        }

        if (!isSlotDisponibile(appuntamentoRequest.getDataOraAppuntamento())) {
            throw new IllegalStateException("Slot non disponibile");
        }

        Appuntamento appuntamento = toEntity(appuntamentoRequest);
        appuntamento = appuntamentoRepository.save(appuntamento);
        return toResponse(appuntamento);
    }

    public boolean isSlotDisponibile(LocalDateTime dataOra) {
        return appuntamentoRepository.findByDataOraAppuntamento(dataOra).isEmpty();
    }

    public List<AppuntamentoResponse> findAllAppuntamenti() {
        List<Appuntamento> appuntamenti = appuntamentoRepository.findAll(Sort.by("dataOraAppuntamento"));
        return appuntamenti.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public AppuntamentoResponse findAppuntamentoById(Long id) {
        Appuntamento appuntamento = appuntamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Appuntamento non trovato"));
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

        if (!isSlotValido(dataOra)) {
            throw new IllegalStateException("Orario non valido per la prenotazione");
        }

        boolean slotOccupatoDaAltri = appuntamentoRepository.findByDataOraAppuntamento(dataOra).stream()
                .anyMatch(a -> !a.getId().equals(id));
        if (slotOccupatoDaAltri) {
            throw new IllegalStateException("Slot già occupato da un altro appuntamento");
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

    public boolean isSlotValido(LocalDateTime dataOra) {
        DayOfWeek giornoSettimana = dataOra.getDayOfWeek();
        int giornoMese = dataOra.getDayOfMonth();
        LocalTime ora = dataOra.toLocalTime();

        if (giornoSettimana == DayOfWeek.SATURDAY || giornoSettimana == DayOfWeek.SUNDAY) {
            return false;
        }

        if (giornoMese % 2 == 1) {
            return !ora.isBefore(LocalTime.of(8, 0)) && ora.isBefore(LocalTime.of(13, 0));
        } else {
            return !ora.isBefore(LocalTime.of(14, 0)) && ora.isBefore(LocalTime.of(19, 0));
        }
    }


    public Map<LocalDate, Long> getStatisticheProssimi7Giorni() {
        LocalDate oggi = LocalDate.now();
        LocalDate tra7Giorni = oggi.plusDays(7);

        LocalDateTime start = oggi.atStartOfDay();
        LocalDateTime end = tra7Giorni.atTime(LocalTime.MAX);

        List<Appuntamento> appuntamenti = appuntamentoRepository.findByDataOraAppuntamentoBetween(start, end);

        Map<LocalDate, Long> statistiche = new TreeMap<>();

        // Inizializza la mappa con tutti i giorni = 0
        for (int i = 0; i <= 7; i++) {
            statistiche.put(oggi.plusDays(i), 0L);
        }

        // Conta gli appuntamenti per giorno
        for (Appuntamento app : appuntamenti) {
            LocalDate data = app.getDataOraAppuntamento().toLocalDate();
            statistiche.put(data, statistiche.getOrDefault(data, 0L) + 1);
        }

        return statistiche;
    }
}

