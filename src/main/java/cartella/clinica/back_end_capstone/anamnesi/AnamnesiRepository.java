package cartella.clinica.back_end_capstone.anamnesi;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import java.util.List;

public interface AnamnesiRepository extends JpaRepository<Anamnesi, Long>, JpaSpecificationExecutor<Anamnesi> {
    List<Anamnesi> findByPazienteId(Long pazienteId);


}