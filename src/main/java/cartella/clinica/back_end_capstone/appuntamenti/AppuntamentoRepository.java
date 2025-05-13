package cartella.clinica.back_end_capstone.appuntamenti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AppuntamentoRepository extends JpaRepository<Appuntamento, Long>, JpaSpecificationExecutor<Appuntamento> {
}
