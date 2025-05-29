package cartella.clinica.back_end_capstone.common;

import cartella.clinica.back_end_capstone.appuntamenti.Appuntamento;
import cartella.clinica.back_end_capstone.appuntamenti.AppuntamentoRepository;
import cartella.clinica.back_end_capstone.auth.AppUserService;
import cartella.clinica.back_end_capstone.enums.Genere;
import cartella.clinica.back_end_capstone.enums.GruppoSanguigno;
import cartella.clinica.back_end_capstone.pazienti.Paziente;
import cartella.clinica.back_end_capstone.pazienti.PazienteRepository;
import cartella.clinica.back_end_capstone.pazienti.PazienteRequest;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
@Order(2)
public class CommonRunner implements CommandLineRunner {

    @Autowired
    private Faker faker;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private PazienteRepository pazienteRepository;

    @Autowired
    private AppuntamentoRepository appuntamentoRepository;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 12; i++) {
            String email = faker.internet().emailAddress();
            String nome = faker.name().firstName();
            String cognome = faker.name().lastName();

            PazienteRequest req = new PazienteRequest();
            req.setEmail(email);
            req.setNome(nome);
            req.setCognome(cognome);
            req.setCodiceFiscale(faker.idNumber().valid());
            req.setLuogoDiNascita(faker.address().city());
            req.setIndirizzoResidenza(faker.address().streetAddress());
            req.setTelefonoCellulare(faker.phoneNumber().cellPhone());
            req.setTelefonoFisso(faker.phoneNumber().phoneNumber());
            req.setDomicilio(faker.address().streetAddress());
            req.setDataDiNascita(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            req.setGruppoSanguigno(GruppoSanguigno.valueOf(faker.options().option(
                    "A_POSITIVO", "A_NEGATIVO", "B_POSITIVO", "B_NEGATIVO",
                    "AB_POSITIVO", "AB_NEGATIVO", "ZERO_POSITIVO", "ZERO_NEGATIVO")));
            req.setSesso(Genere.valueOf(faker.options().option("MASCHILE", "FEMMINILE")));
            req.setEsenzione("000");

            appUserService.registerUser(req, "Password123!");

            Paziente paziente = pazienteRepository.findByCodiceFiscale(req.getCodiceFiscale())
                    .orElseThrow(() -> new RuntimeException("Paziente non trovato"));

            // Aggiunta appuntamento
            Date startDate = new Date();
            Date endDate = Date.from(LocalDate.now().plusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant());

            Appuntamento appuntamento = new Appuntamento();
            appuntamento.setDataOraAppuntamento(
                    faker.date().between(startDate, endDate)
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime()
            );
            appuntamento.setMotivoRichiesta(faker.lorem().sentence());
            appuntamento.setPaziente(paziente);

            appuntamentoRepository.save(appuntamento);
        }
    }
}

