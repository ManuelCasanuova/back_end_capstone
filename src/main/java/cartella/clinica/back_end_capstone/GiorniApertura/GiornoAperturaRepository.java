package cartella.clinica.back_end_capstone.GiorniApertura;
import cartella.clinica.back_end_capstone.medici.Medico;
import cartella.clinica.back_end_capstone.studi.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface GiornoAperturaRepository extends JpaRepository<GiornoApertura, Long> {

    List<GiornoApertura> findByStudio_Medico(Medico medico);
    Optional<GiornoApertura> findByStudio_MedicoAndGiorno(Medico medico, DayOfWeek giorno);
}
