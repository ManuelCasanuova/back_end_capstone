package cartella.clinica.back_end_capstone.auth;


import cartella.clinica.back_end_capstone.pazienti.Paziente;
import cartella.clinica.back_end_capstone.pazienti.PazienteRepository;
import cartella.clinica.back_end_capstone.pazienti.PazienteRequest;
import cartella.clinica.back_end_capstone.utenti.Utente;
import cartella.clinica.back_end_capstone.utenti.UtenteRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
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
        appUserRepository.save(appUser);

        Utente utente = new Utente();
        utente.setEmail(pazienteRequest.getEmail());
        utente.setNome(pazienteRequest.getNome());
        utente.setCognome(pazienteRequest.getCognome());
        utente.setAppUser(appUser);
        utenteRepository.save(utente);

        Paziente paziente = new Paziente();
        paziente.setAppUser(appUser);
        paziente.setUtente(utente);
        paziente.setDataDiNascita(pazienteRequest.getDataDiNascita());
        paziente.setCodiceFiscale(pazienteRequest.getCodiceFiscale());
        paziente.setDomicilio(pazienteRequest.getDomicilio());
        paziente.setGruppoSanguigno(pazienteRequest.getGruppoSanguigno());
        paziente.setIndirizzoResidenza(pazienteRequest.getIndirizzoResidenza());
        paziente.setTelefonoCellulare(pazienteRequest.getTelefonoCellulare());
        paziente.setTelefonoFisso(pazienteRequest.getTelefonoFisso());
        paziente.setSesso(pazienteRequest.getSesso());
        paziente.setLuogoDiNascita(pazienteRequest.getLuogoDiNascita());
        paziente.setEsenzione(pazienteRequest.getEsenzione());
        pazienteRepository.save(paziente);

        return appUser;
    }



    public AppUser registerUser(String username, String password, Set<Role> roles) {
        if (appUserRepository.existsByUsername(username)) {
            throw new EntityExistsException("Username già in uso");
        }

        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(passwordEncoder.encode(password));
        appUser.setRoles(roles);
        return appUserRepository.save(appUser);
    }

    public Optional<AppUser> findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    public String authenticateUser(String username, String password)  {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            throw new SecurityException("Credenziali non valide", e);
        }
    }


    public AppUser loadUserByUsername(String username)  {
        AppUser appUser = appUserRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("Utente non trovato con username: " + username));


        return appUser;
    }
}
