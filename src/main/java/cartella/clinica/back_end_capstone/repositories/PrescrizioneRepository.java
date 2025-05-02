package cartella.clinica.back_end_capstone.repositories;

import cartella.clinica.back_end_capstone.entities.Prescrizione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescrizioneRepository extends JpaRepository<Prescrizione, Long> {

}