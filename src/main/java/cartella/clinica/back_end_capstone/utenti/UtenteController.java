package cartella.clinica.back_end_capstone.utenti;

import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.auth.AppUserService;
import cartella.clinica.back_end_capstone.auth.Role;

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

    @Autowired
    private UtenteRepository utenteRepository;


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
        response.setRoles(user.getRoles());

        Utente utente = utenteRepository.findByAppUser(user).orElse(null);
        if (utente != null) {
            response.setNome(utente.getNome());
            response.setCognome(utente.getCognome());
            response.setEmail(utente.getEmail());
            response.setAvatar(utente.getAvatar());
        } else {
            response.setNome("Sconosciuto");
            response.setCognome("-");
            response.setEmail("-");
        }


        if (user.getRoles().contains(Role.ROLE_PAZIENTE) && user.getPaziente() != null) {
            response.setId(user.getPaziente().getId());
        }


        if (user.getRoles().contains(Role.ROLE_ADMIN) && user.getMedico() != null) {
            response.setId(user.getMedico().getId());
        }

        return ResponseEntity.ok(response);
    }



}

