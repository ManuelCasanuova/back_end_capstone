package cartella.clinica.back_end_capstone.common;


import cartella.clinica.back_end_capstone.anamnesi.AnamnesiRepository;
import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.auth.AppUserRepository;
import cartella.clinica.back_end_capstone.auth.AppUserService;
import cartella.clinica.back_end_capstone.auth.Role;
import cartella.clinica.back_end_capstone.diagnosi.DiagnosiRepository;
import cartella.clinica.back_end_capstone.enums.Genere;
import cartella.clinica.back_end_capstone.enums.GruppoSanguigno;
import cartella.clinica.back_end_capstone.medici.Medico;
import cartella.clinica.back_end_capstone.medici.MedicoRepository;
import cartella.clinica.back_end_capstone.pazienti.Paziente;
import cartella.clinica.back_end_capstone.pazienti.PazienteRepository;
import cartella.clinica.back_end_capstone.utenti.Utente;
import cartella.clinica.back_end_capstone.utenti.UtenteRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Set;

@Component
public class CommonRunner implements CommandLineRunner {

    @Autowired
    Faker faker;

    @Autowired
    private PazienteRepository pazienteRepository;

    @Autowired
    private AnamnesiRepository anamnesiRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private DiagnosiRepository diagnosiRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private MedicoRepository medicoRepository;


    @Autowired
    private AppUserService appUserService;

    @Override
    public void run(String... args) throws Exception {




        for(int i = 0; i < 12; i++) {

            String email = faker.internet().emailAddress();
            String password = "password123"; // Puoi randomizzare o criptare
            String nome = faker.name().firstName();
            String cognome = faker.name().lastName();


            appUserService.registerUser(email, password, Set.of(Role.ROLE_PAZIENTE));

            AppUser appUser = appUserService.findByUsername(email).orElseThrow( () -> new RuntimeException("Utente non trovato"));


            Utente utente = new Utente();
            utente.setEmail(email);
            utente.setNome(nome);
            utente.setCognome(cognome);
            utente.setAppUser(appUser);
            utenteRepository.save(utente);

            Paziente paziente = new Paziente();

            paziente.setTelefonoCellulare(faker.phoneNumber().cellPhone());
            paziente.setTelefonoFisso(faker.phoneNumber().phoneNumber());
            paziente.setCodiceFiscale(faker.idNumber().valid());
            paziente.setLuogoDiNascita(faker.address().city());
            paziente.setIndirizzoResidenza(faker.address().streetAddress());
            paziente.setDomicilio(faker.address().streetAddress());
            paziente.setDataDiNascita(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            paziente.setGruppoSanguigno(GruppoSanguigno.valueOf(faker.options().option("A_POSITIVO",
                            "A_NEGATIVO",
                            "B_POSITIVO",
                            "B_NEGATIVO",
                            "AB_POSITIVO",
                            "AB_NEGATIVO",
                            "ZERO_POSITIVO",
                            "ZERO_NEGATIVO")));
            paziente.setSesso(Genere.valueOf(faker.options().option("MASCHILE", "FEMMINILE")));
            paziente.setUtente(utente);
            pazienteRepository.save(paziente);

            Medico medico = new Medico();
            medico.setSpecializzazione(faker.medical().diseaseName());
            medico.setTelefonoStudio(faker.phoneNumber().cellPhone());
            medico.setUtente(utente);

            medicoRepository.save(medico);
        }



    }
}
