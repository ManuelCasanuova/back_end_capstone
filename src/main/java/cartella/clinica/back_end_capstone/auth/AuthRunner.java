package cartella.clinica.back_end_capstone.auth;

import cartella.clinica.back_end_capstone.utenti.Utente;
import cartella.clinica.back_end_capstone.utenti.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class AuthRunner implements ApplicationRunner {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private UtenteService utenteService;

    @Override
    public void run(ApplicationArguments args) throws Exception {


        String emailAdmin = "dariolampa@gmail.com";

        Optional<AppUser> admin = appUserService.findByUsername(emailAdmin);
        if (admin.isEmpty()) {
            AppUser appUser = new AppUser();
            appUser.setUsername(emailAdmin);
            appUser.setPassword(passwordEncoder.encode("password"));
            appUser.setRoles(Set.of(Role.ROLE_ADMIN));
            appUser.setPasswordModificata(true);
            appUserRepository.save(appUser);

            Utente utente = new Utente();
            utente.setAppUser(appUser);
            utente.setAvatar("https://ui-avatars.com/api/?name=Admin");
            utente.setNome("Dario");
            utente.setCognome("Lampa");
            utente.setEmail(emailAdmin);
            utenteService.saveUtente(utente);
        }


        Optional<AppUser> normalUser = appUserService.findByUsername("user");
        if (normalUser.isEmpty()) {
            appUserService.registerUser("user", Set.of(Role.ROLE_PAZIENTE));
        }
    }

}
