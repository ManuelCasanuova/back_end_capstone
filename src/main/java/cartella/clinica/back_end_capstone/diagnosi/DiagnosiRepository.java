
package cartella.clinica.back_end_capstone.diagnosi;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DiagnosiRepository extends JpaRepository<Diagnosi, Long>, JpaSpecificationExecutor<Diagnosi> {
    List<Diagnosi> findByPazienteId(Long pazienteId);
}