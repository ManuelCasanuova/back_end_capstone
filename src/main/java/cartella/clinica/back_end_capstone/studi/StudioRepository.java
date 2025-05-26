package cartella.clinica.back_end_capstone.studi;


import cartella.clinica.back_end_capstone.medici.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StudioRepository extends JpaRepository<Studio, Long> {
    Optional<Studio> findByMedico(Medico medico);
}