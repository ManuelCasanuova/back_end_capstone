package cartella.clinica.back_end_capstone.studi;

import cartella.clinica.back_end_capstone.medici.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class StudioService {

    @Autowired
    private StudioRepository repo;

    public Studio getByMedico(Medico medico) {
        return repo.findByMedico(medico).orElseGet(() -> {
            Studio studio = new Studio();
            studio.setInizioMattina(LocalTime.of(8, 0));
            studio.setFineMattina(LocalTime.of(13, 0));
            studio.setInizioPomeriggio(LocalTime.of(14, 0));
            studio.setFinePomeriggio(LocalTime.of(19, 0));
            studio.setGiornoDispariMattina(true);
            studio.setMedico(medico);
            return repo.save(studio);
        });
    }

    public Studio updateForMedico(Medico medico, Studio nuovo) {
        Studio esistente = getByMedico(medico);
        esistente.setInizioMattina(nuovo.getInizioMattina());
        esistente.setFineMattina(nuovo.getFineMattina());
        esistente.setInizioPomeriggio(nuovo.getInizioPomeriggio());
        esistente.setFinePomeriggio(nuovo.getFinePomeriggio());
        esistente.setGiornoDispariMattina(nuovo.isGiornoDispariMattina());
        return repo.save(esistente);
    }
}