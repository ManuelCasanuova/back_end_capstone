package cartella.clinica.back_end_capstone.auth;

import cartella.clinica.back_end_capstone.medici.Medico;
import cartella.clinica.back_end_capstone.medici.MedicoRepository;
import cartella.clinica.back_end_capstone.pazienti.Paziente;
import cartella.clinica.back_end_capstone.pazienti.PazienteRepository;
import cartella.clinica.back_end_capstone.pazienti.PazienteRequest;
import cartella.clinica.back_end_capstone.services.EmailService;
import cartella.clinica.back_end_capstone.utenti.Utente;
import cartella.clinica.back_end_capstone.utenti.UtenteRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PazienteRepository pazienteRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public AppUser registerUser(PazienteRequest pazienteRequest, String password) {
        String username = pazienteRequest.getEmail();

        if (appUserRepository.existsByUsername(username)) {
            throw new EntityExistsException("Username già in uso");
        }

        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(passwordEncoder.encode(password));
        appUser.setRoles(Set.of(Role.ROLE_PAZIENTE));
        appUser.setPasswordModificata(!"Password123!".equals(password));
        appUserRepository.save(appUser);

        Utente utente = new Utente();
        utente.setEmail(username);
        utente.setNome(pazienteRequest.getNome());
        utente.setCognome(pazienteRequest.getCognome());
        utente.setTelefonoCellulare(pazienteRequest.getTelefonoCellulare());
        utente.setTelefonoFisso(pazienteRequest.getTelefonoFisso());
        utente.setAppUser(appUser);
        appUser.setUtente(utente);
        utenteRepository.save(utente);

        Paziente paziente = new Paziente();
        paziente.setAppUser(appUser);
        paziente.setUtente(utente);
        paziente.setDataDiNascita(pazienteRequest.getDataDiNascita());
        paziente.setCodiceFiscale(pazienteRequest.getCodiceFiscale());
        paziente.setDomicilio(pazienteRequest.getDomicilio());
        paziente.setGruppoSanguigno(pazienteRequest.getGruppoSanguigno());
        paziente.setIndirizzoResidenza(pazienteRequest.getIndirizzoResidenza());
        paziente.setSesso(pazienteRequest.getSesso());
        paziente.setLuogoDiNascita(pazienteRequest.getLuogoDiNascita());
        paziente.setEsenzione(pazienteRequest.getEsenzione());


        AppUser admin = appUserRepository.findByUsername("dariolampa@gmail.com")
                .orElseThrow(() -> new EntityNotFoundException("Admin non trovato"));
        Medico medico = medicoRepository.findByAppUser(admin)
                .orElseThrow(() -> new EntityNotFoundException("Medico non trovato"));

        paziente.setMedico(medico);
        pazienteRepository.save(paziente);

        emailService.sendCredenzialiPaziente(username, username, password);

        return appUser;
    }


    public AppUser registerUser(String username, Set<Role> roles) {
        if (appUserRepository.existsByUsername(username)) {
            throw new EntityExistsException("Username già in uso");
        }

        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(passwordEncoder.encode("Admin123!"));
        appUser.setRoles(roles);
        appUser.setPasswordModificata(false);
        return appUserRepository.save(appUser);
    }

    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    public AuthResponse authenticateUser(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtTokenUtil.generateToken(userDetails);

            AppUser appUser = appUserRepository.findByUsername(username)
                    .orElseThrow(() -> new EntityNotFoundException("Utente non trovato con username: " + username));

            boolean changePasswordRequired =
                    appUser.getRoles().contains(Role.ROLE_PAZIENTE) && !appUser.isPasswordModificata();

            return new AuthResponse(token, changePasswordRequired);

        } catch (AuthenticationException e) {
            throw new SecurityException("Credenziali non valide", e);
        }
    }

    public void changePassword(ChangePasswordRequest req) {
        AppUser appUser = appUserRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));

        if (!passwordEncoder.matches(req.getOldPassword(), appUser.getPassword())) {
            throw new SecurityException("La vecchia password non è corretta");
        }

        appUser.setPassword(passwordEncoder.encode(req.getNewPassword()));
        appUser.setPasswordModificata(true);
        appUserRepository.save(appUser);
    }

    public AppUser getUtenteAutenticato() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            throw new RuntimeException("Nessun utente autenticato");
        }
        String username = authentication.getName();

        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + username));
    }

}


