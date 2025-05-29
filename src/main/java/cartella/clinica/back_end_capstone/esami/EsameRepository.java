package cartella.clinica.back_end_capstone.esami;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EsameRepository extends JpaRepository<Esame, Long> {
    List<Esame> findByPazienteId(Long pazienteId);
}