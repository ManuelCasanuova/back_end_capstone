package cartella.clinica.back_end_capstone.farmaci;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import java.util.List;


public interface FarmacoRepository extends JpaRepository<Farmaco, Long>, JpaSpecificationExecutor<Farmaco> {

    List<Farmaco> findByPazienteId(Long pazienteId);
}