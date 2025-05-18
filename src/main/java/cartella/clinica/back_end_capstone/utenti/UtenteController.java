package cartella.clinica.back_end_capstone.utenti;

import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.auth.AppUserService;
import cartella.clinica.back_end_capstone.auth.Role;
import cartella.clinica.back_end_capstone.medici.Medico;
import cartella.clinica.back_end_capstone.medici.MedicoRepository;
import cartella.clinica.back_end_capstone.pazienti.Paziente;
import cartella.clinica.back_end_capstone.pazienti.PazienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utente")

public class UtenteController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private PazienteRepository pazienteRepo;

    @Autowired
    private MedicoRepository medicoRepo;

    @Autowired
    private UtenteService  utenteService;


    @GetMapping("/all")
    public Page<Utente> getUtenti(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return utenteService.getAllUtenti(pageable);
    }

    @GetMapping("/me")
    public ResponseEntity<UtenteResponse> getMe() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        AppUser user = appUserService.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));

        UtenteResponse response = new UtenteResponse();
        response.setUsername(user.getUsername());
        response.setRuolo(user.getRoles().iterator().next().name());

        if (user.getRoles().contains(Role.ROLE_PAZIENTE)) {
            Paziente paziente = user.getPaziente();
            response.setTipoProfilo("paziente");
            response.setNome(paziente.getCodiceFiscale());
            response.setCognome("-");

        } else if (user.getRoles().contains(Role.ROLE_ADMIN)) {
            Medico medico = medicoRepo.findByUtente_AppUser(user).orElse(null);
            Utente utente = medico != null ? medico.getUtente() : null;
            response.setTipoProfilo("medico");
            if (utente != null) {
                response.setNome(utente.getNome());
                response.setCognome(utente.getCognome());
                response.setEmail(utente.getEmail());
            }

        } else {
            response.setTipoProfilo("altro");
            response.setNome("Sconosciuto");
            response.setCognome("-");
            response.setEmail("-");

        }

        return ResponseEntity.ok(response);
    }
}

