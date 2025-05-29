package cartella.clinica.back_end_capstone.auth;

import cartella.clinica.back_end_capstone.GiorniApertura.GiornoApertura;
import cartella.clinica.back_end_capstone.GiorniApertura.GiornoAperturaRepository;
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

import java.time.DayOfWeek;
import java.time.LocalTime;
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

    @Autowired
    private GiornoAperturaRepository giornoAperturaRepository;

    @Override
    public void run(ApplicationArguments args) {

        String emailAdmin = "dariolampa@gmail.com";

        Optional<AppUser> admin = appUserService.findByUsername(emailAdmin);
        if (admin.isEmpty()) {

            // 1. AppUser
            AppUser appUser = new AppUser();
            appUser.setUsername(emailAdmin);
            appUser.setPassword(passwordEncoder.encode("password"));
            appUser.setRoles(Set.of(Role.ROLE_ADMIN));
            appUser.setPasswordModificata(true);
            appUserRepository.save(appUser);

            // 2. Utente
            Utente utente = new Utente();
            utente.setAppUser(appUser);
            utente.setAvatar("https://ui-avatars.com/api/?name=Admin");
            utente.setNome("Dario");
            utente.setCognome("Lampa");
            utente.setEmail(emailAdmin);
            utente.setTelefonoCellulare("0123 456789");
            utente.setTelefonoFisso("0123 456789");
            utenteService.saveUtente(utente);

            // 3. Medico
            Medico medico = new Medico();
            medico.setAppUser(appUser);
            medico.setUtente(utente);
            medico.setSpecializzazione("Medicina Generale");
            medico = medicoRepository.save(medico);

            // 4. Studio
            Studio studio = new Studio();
            studio.setMedico(medico);
            studio.setNome("Studio Dott. Lampa");
            studio.setIndirizzo("Via della Salute 123, Milano");
            studio = studioRepository.save(studio);

            // 5. Giorni di apertura dello studio (LUN–VEN aperto, SAB–DOM chiuso)
            for (DayOfWeek giorno : DayOfWeek.values()) {
                GiornoApertura g = new GiornoApertura();
                g.setStudio(studio);
                g.setGiorno(giorno);
                g.setInizioMattina(LocalTime.of(8, 0));
                g.setFineMattina(LocalTime.of(13, 0));
                g.setInizioPomeriggio(LocalTime.of(14, 0));
                g.setFinePomeriggio(LocalTime.of(19, 0));
                g.setChiuso(giorno == DayOfWeek.SATURDAY || giorno == DayOfWeek.SUNDAY);
                giornoAperturaRepository.save(g);
            }
        }

        Optional<AppUser> normalUser = appUserService.findByUsername("user");
        if (normalUser.isEmpty()) {
            appUserService.registerUser("user", Set.of(Role.ROLE_PAZIENTE));
        }
    }
}



