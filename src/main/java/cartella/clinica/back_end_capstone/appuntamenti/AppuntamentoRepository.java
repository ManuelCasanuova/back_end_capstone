package cartella.clinica.back_end_capstone.appuntamenti;

import cartella.clinica.back_end_capstone.medici.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppuntamentoRepository extends JpaRepository<Appuntamento, Long>, JpaSpecificationExecutor<Appuntamento> {

    long countByDataOraAppuntamentoBetween(LocalDateTime start, LocalDateTime end);

    List<Appuntamento> findByDataOraAppuntamentoBetween(LocalDateTime start, LocalDateTime end);

    List<Appuntamento> findByDataOraAppuntamento(LocalDateTime dataOra);

    List<Appuntamento> findByPaziente_Id(Long pazienteId);

    @Query("""
        SELECT FUNCTION('DATE', a.dataOraAppuntamento) as data, COUNT(a) as totale
        FROM Appuntamento a
        WHERE a.dataOraAppuntamento BETWEEN :oggi AND :setteGiorniDopo
        GROUP BY FUNCTION('DATE', a.dataOraAppuntamento)
        ORDER BY data
    """)
    List<Object[]> countAppuntamentiPerGiorno(
            @Param("oggi") LocalDateTime oggi,
            @Param("setteGiorniDopo") LocalDateTime setteGiorniDopo
    );

    @Query("""
        SELECT a FROM Appuntamento a
        WHERE a.dataOraAppuntamento BETWEEN :start AND :end
          AND a.paziente.medico = :medico
    """)
    List<Appuntamento> findByDataOraAppuntamentoBetweenAndPaziente_Medico(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("medico") Medico medico
    );

}




