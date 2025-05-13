package cartella.clinica.back_end_capstone.diagnosi.stati;


import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.auth.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated

public class StatoDiagnosiService {

    @Autowired
    private StatoDiagnosiRepository statoDiagnosiRepository;

    public StatoDiagnosi createStatoDiagnosi(StatoDiagnosiRequest statoDiagnosiRequest, AppUser adminLoggato) {
       boolean isAdmin = adminLoggato.getRoles().contains(Role.ROLE_ADMIN);
       if(!isAdmin) {
           throw new RuntimeException("Non sei autorizzato a creare lo stato della diagnosi");
       } else {
           return statoDiagnosiRepository.save(new StatoDiagnosi(statoDiagnosiRequest.getNomeStatoDiagnosi()));
       }
    }

    public StatoDiagnosi findStatoById(Long id) {
        return statoDiagnosiRepository.findById(id).orElseThrow(() -> new RuntimeException("Stato della diagnosi non trovato"));
    }


    public List<StatoDiagnosi> findAllStati() {
        return statoDiagnosiRepository.findAll();
    }

    public StatoDiagnosi updateStatoDiagnosi(Long id, StatoDiagnosi stato, AppUser adminLoggato) {
        StatoDiagnosi statoDiagnosi = statoDiagnosiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stato della diagnosi non trovato"));

        boolean isAdmin = adminLoggato.getRoles().contains(Role.ROLE_ADMIN);
        if(!isAdmin) {
            throw new RuntimeException("Non sei autorizzato a modificare lo stato della diagnosi");
        } else {
           statoDiagnosi.setNomeStatoDiagnosi(stato.getNomeStatoDiagnosi());
        }
        return statoDiagnosiRepository.save(statoDiagnosi);
    }



    public void deleteStatoDiagnosi(Long id, AppUser adminLoggato) {
        StatoDiagnosi statoDiagnosi = statoDiagnosiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stato della diagnosi non trovato"));

        boolean isAdmin = adminLoggato.getRoles().contains(Role.ROLE_ADMIN);
        if(!isAdmin) {
            throw new RuntimeException("Non sei autorizzato a eliminare lo stato della diagnosi");
        } else {
            statoDiagnosiRepository.delete(statoDiagnosi);
        }
    }

    public StatoDiagnosi findStatoDiagnosiByNome(String nomeStatoDiagnosi) {
       return statoDiagnosiRepository.findByNomeStatoDiagnosi(nomeStatoDiagnosi).orElseThrow(() -> new RuntimeException("Stato della diagnosi non trovato"));
    }
}
