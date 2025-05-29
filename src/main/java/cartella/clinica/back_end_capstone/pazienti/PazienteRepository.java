package cartella.clinica.back_end_capstone.pazienti;


import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.medici.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PazienteRepository extends JpaRepository<Paziente, Long>, JpaSpecificationExecutor<Paziente> {

    boolean existsByCodiceFiscale(String codiceFiscale);

    Page<Paziente> findAll(Specification<Paziente> spec, Pageable pageable);

    Optional<Paziente> findByCodiceFiscale(String codiceFiscale);

    Optional<Paziente> findByAppUser(AppUser appUser);

    List<Paziente> findByMedico(Medico medico);
}