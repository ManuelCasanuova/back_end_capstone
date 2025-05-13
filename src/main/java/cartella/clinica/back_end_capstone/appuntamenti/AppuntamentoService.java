package cartella.clinica.back_end_capstone.appuntamenti;


import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.auth.Role;
import cartella.clinica.back_end_capstone.pazienti.PazienteRepository;
import cartella.clinica.back_end_capstone.pazienti.PazienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated

public class AppuntamentoService {

    @Autowired
    private AppuntamentoRepository appuntamentoRepository;

    @Autowired
    private PazienteRepository pazienteRepository;

    @Autowired
    private PazienteService pazienteService;


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
                appuntamento.getPaziente().getId()
        );
    }

    public AppuntamentoResponse createAppuntamento(AppuntamentoRequest appuntamentoRequest) {
        Appuntamento appuntamento = toEntity(appuntamentoRequest);
        appuntamento = appuntamentoRepository.save(appuntamento);
        return toResponse(appuntamento);
    }

    public Page<AppuntamentoResponse> findAllAppuntamenti(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return appuntamentoRepository.findAll(pageable).map(this::toResponse);
    }

    public AppuntamentoResponse findAppuntamentoById(Long id) {
        Appuntamento appuntamento = appuntamentoRepository.findById(id).orElseThrow(()-> new RuntimeException("Appuntamento non trovato"));
        return toResponse(appuntamento);
    }

    public AppuntamentoResponse updateAppuntamento (Long id, AppuntamentoResponse appuntamentoRequest, AppUser adminLoggato) {
        Appuntamento appuntamento = appuntamentoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Appuntamento non trovato"));

        boolean isAdmin = adminLoggato.getRoles().contains(Role.ROLE_ADMIN);
        if (!isAdmin) {
            throw new RuntimeException("Non sei autorizzato a modificare l'appuntamento");
        } else {
            appuntamento.setDataOraAppuntamento(appuntamentoRequest.getDataOraAppuntamento());
            appuntamento.setMotivoRichiesta(appuntamentoRequest.getMotivoRichiesta());
            appuntamento.setPaziente(pazienteRepository.findById(appuntamentoRequest.getPazienteId()).orElseThrow());
            appuntamento = appuntamentoRepository.save(appuntamento);
            return toResponse(appuntamento);
        }

    }

    public void deleteAppuntamento(Long id) {
        Appuntamento appuntamento = appuntamentoRepository.findById(id).orElseThrow(()-> new RuntimeException("Appuntamento non trovato"));
        appuntamentoRepository.delete(appuntamento);
    }

    public Page<AppuntamentoResponse> filterAppuntamento(AppuntamentoFilter appuntamentoFilter, Pageable pageable) {
        Specification<Appuntamento> spec = AppuntamentoSpecification.appuntamentoFilter(appuntamentoFilter);
        Page<Appuntamento> page = appuntamentoRepository.findAll(spec, pageable);
        return page.map(this::toResponse);
    }
}
