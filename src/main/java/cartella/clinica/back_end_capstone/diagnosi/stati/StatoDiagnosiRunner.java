package cartella.clinica.back_end_capstone.diagnosi.stati;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Order
public class StatoDiagnosiRunner implements CommandLineRunner {

    @Autowired
    StatoDiagnosiRepository statoDiagnosiRepository;

    @Override
    public void run(String... args) throws Exception {

        StatoDiagnosi diagnosiAttiva = new StatoDiagnosi();
        diagnosiAttiva.setNomeStatoDiagnosi("ATTIVA");

        StatoDiagnosi diagnosiRemissione = new StatoDiagnosi();
        diagnosiRemissione.setNomeStatoDiagnosi("REMISSIONE");

        StatoDiagnosi diagnosiRisolta = new StatoDiagnosi();
        diagnosiRisolta.setNomeStatoDiagnosi("RISOLTA");

        statoDiagnosiRepository.save(diagnosiAttiva);
        statoDiagnosiRepository.save(diagnosiRemissione);
        statoDiagnosiRepository.save(diagnosiRisolta);

    }
}
