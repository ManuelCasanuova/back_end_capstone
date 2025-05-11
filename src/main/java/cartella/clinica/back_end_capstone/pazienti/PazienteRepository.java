package cartella.clinica.back_end_capstone.pazienti;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PazienteRepository extends JpaRepository<Paziente, Long> {

    boolean existsByEmail(String email);

    boolean existsByTelefonoCellulare(String telefonoCellulare);

    boolean existsByCodiceFiscale(String codiceFiscale);

    Page<Paziente> findAll(Specification<Paziente> spec, Pageable pageable);

    Optional<Paziente> findByCodiceFiscale(String codiceFiscale);
}