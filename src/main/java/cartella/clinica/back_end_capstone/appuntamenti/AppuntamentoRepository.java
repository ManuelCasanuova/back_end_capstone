package cartella.clinica.back_end_capstone.appuntamenti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface AppuntamentoRepository extends JpaRepository<Appuntamento, Long>, JpaSpecificationExecutor<Appuntamento> {

    long countByDataOraAppuntamentoBetween(LocalDateTime start, LocalDateTime end);

    List<Appuntamento> findByDataOraAppuntamentoBetween(LocalDateTime start, LocalDateTime end);

    List<Appuntamento> findByDataOraAppuntamento(LocalDateTime dataOra);


}
