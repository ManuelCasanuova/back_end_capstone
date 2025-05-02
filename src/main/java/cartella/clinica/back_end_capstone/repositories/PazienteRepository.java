package cartella.clinica.back_end_capstone.repositories;


import cartella.clinica.back_end_capstone.entities.Paziente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PazienteRepository extends JpaRepository<Paziente, Long> {
    Optional<Paziente> findByCodiceFiscale(String codiceFiscale);
}