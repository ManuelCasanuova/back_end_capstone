package cartella.clinica.back_end_capstone.studi;

import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.auth.AppUserService;
import cartella.clinica.back_end_capstone.medici.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studio")
public class StudioController {

    @Autowired
    private StudioService service;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private StudioRepository studioRepository;

    @GetMapping
    public ResponseEntity<Studio> get() {
        AppUser user = appUserService.getUtenteAutenticato();
        Medico medico = user.getMedico();
        return ResponseEntity.ok(service.getByMedico(medico));
    }

    @PutMapping
    public ResponseEntity<Studio> update(@RequestBody Studio nuovo) {
        AppUser user = appUserService.getUtenteAutenticato();
        Medico medico = user.getMedico();
        return ResponseEntity.ok(service.updateForMedico(medico, nuovo));
    }

    @GetMapping("/mio-studio")
    public ResponseEntity<StudioResponse> getMioStudio() {
        AppUser user = appUserService.getUtenteAutenticato();

        if (user.getPaziente() == null || user.getPaziente().getMedico() == null) {
            return ResponseEntity.notFound().build();
        }

        Medico medico = user.getPaziente().getMedico();

        Studio studio = studioRepository.findByMedico(medico)
                .orElseThrow(() -> new RuntimeException("Studio non trovato"));

        StudioResponse response = new StudioResponse(
                studio.getNome(),
                studio.getIndirizzo(),
                studio.getTelefono(),
                studio.getInizioMattina().toString(),
                studio.getFineMattina().toString(),
                studio.getInizioPomeriggio().toString(),
                studio.getFinePomeriggio().toString(),
                medico.getUtente().getNome(),
                medico.getUtente().getCognome(),
                medico.getUtente().getEmail(),
                medico.getSpecializzazione()
        );

        return ResponseEntity.ok(response);
    }
}