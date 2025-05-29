package cartella.clinica.back_end_capstone.comunicazioni;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComunicazioneRepository extends JpaRepository<Comunicazione, Long> {

    @Query("""
SELECT c FROM Comunicazione c 
WHERE c.medico.id = :medicoId
AND c.paziente IS NULL
ORDER BY c.dataComunicazione DESC
""")
    List<Comunicazione> comunicazioniGlobali(Long medicoId);
}