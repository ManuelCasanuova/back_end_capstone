package cartella.clinica.back_end_capstone.prescrizioni;


import cartella.clinica.back_end_capstone.appuntamenti.Appuntamento;
import cartella.clinica.back_end_capstone.pazienti.Paziente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PrescrizioneRepository extends JpaRepository<Prescrizione, Long>, JpaSpecificationExecutor<Prescrizione> {

    Page<Prescrizione> findByPaziente(Paziente paziente, Pageable pageable);

}