package cartella.clinica.back_end_capstone.anamnesi;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnamnesiRepository extends JpaRepository<Anamnesi, Long>, JpaSpecificationExecutor<Anamnesi> {
    List<Anamnesi> findByPazienteId(Long pazienteId);

    List<Anamnesi> findByPaziente_CodiceFiscale(String codiceFiscale);

    @Query("""
    SELECT a FROM Anamnesi a 
    WHERE LOWER(a.paziente.utente.nome) LIKE LOWER(CONCAT('%', :nome, '%')) 
    AND LOWER(a.paziente.utente.cognome) LIKE LOWER(CONCAT('%', :cognome, '%'))
""")
    List<Anamnesi> findByPazienteNomeCognome(String nome, String cognome);

}