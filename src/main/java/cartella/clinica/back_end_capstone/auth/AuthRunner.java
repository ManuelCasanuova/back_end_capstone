package cartella.clinica.back_end_capstone.auth;

import cartella.clinica.back_end_capstone.medici.Medico;
import cartella.clinica.back_end_capstone.medici.MedicoRepository;
import cartella.clinica.back_end_capstone.studi.Studio;
import cartella.clinica.back_end_capstone.studi.StudioRepository;
import cartella.clinica.back_end_capstone.utenti.Utente;
import cartella.clinica.back_end_capstone.utenti.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@Order(1)
public class AuthRunner implements ApplicationRunner {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private StudioRepository studioRepository;

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


            Medico medico = new Medico();
            medico.setAppUser(appUser);
            medico.setUtente(utente);
            medico.setSpecializzazione("Medicina Generale");
            medicoRepository.save(medico);


            Studio studio = new Studio();
            studio.setMedico(medico);
            studio.setNome("Studio Dott. Lampa");
            studio.setIndirizzo("Via della Salute 123, Milano");
            studio.setTelefono("0123 456789");
            studio.setInizioMattina(java.time.LocalTime.of(8, 0));
            studio.setFineMattina(java.time.LocalTime.of(13, 0));
            studio.setInizioPomeriggio(java.time.LocalTime.of(14, 0));
            studio.setFinePomeriggio(java.time.LocalTime.of(19, 0));
            studio.setGiornoDispariMattina(true);
            studioRepository.save(studio);


            medico.setStudio(studio);
            medicoRepository.save(medico);
        }

        Optional<AppUser> normalUser = appUserService.findByUsername("user");
        if (normalUser.isEmpty()) {
            appUserService.registerUser("user", Set.of(Role.ROLE_PAZIENTE));
        }
    }
    }


