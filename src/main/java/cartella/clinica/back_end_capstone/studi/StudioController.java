package cartella.clinica.back_end_capstone.studi;

import cartella.clinica.back_end_capstone.GiorniApertura.GiornoAperturaRequest;
import cartella.clinica.back_end_capstone.GiorniApertura.GiornoAperturaResponse;
import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.auth.AppUserService;
import cartella.clinica.back_end_capstone.medici.Medico;
import cartella.clinica.back_end_capstone.pazienti.Paziente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/studio")
public class StudioController {

    @Autowired
    private StudioService studioService;

    @Autowired
    private AppUserService appUserService;

    @GetMapping
    public ResponseEntity<StudioResponse> getStudio() {
        AppUser user = appUserService.getUtenteAutenticato();

        // Se utente medico (o admin), usa il medico direttamente
        Medico medico = user.getMedico();

        // Se utente paziente, recupera il medico associato al paziente
        if (medico == null) {
            Paziente paziente = user.getPaziente();
            if (paziente == null || paziente.getMedico() == null) {
                return ResponseEntity.notFound().build();
            }
            medico = paziente.getMedico();
        }

        Studio studio = studioService.getByMedico(medico);
        if (studio == null) {
            return ResponseEntity.notFound().build();
        }

        StudioResponse response = new StudioResponse(
                studio.getNome(),
                studio.getIndirizzo(),
                medico.getUtente().getTelefonoFisso(),
                medico.getUtente().getNome(),
                medico.getUtente().getCognome(),
                medico.getUtente().getEmail(),
                medico.getSpecializzazione(),
                medico.getUtente().getTelefonoCellulare(),
                studioService.getGiorniApertura(medico)
        );

        return ResponseEntity.ok(response);
    }


    @PutMapping
    public ResponseEntity<Void> updateStudio(@RequestBody StudioRequest request) {
        AppUser user = appUserService.getUtenteAutenticato();
        Medico medico = user.getMedico();

        if (medico == null) {
            return ResponseEntity.badRequest().build();
        }

        studioService.updateFromRequest(medico, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/orari")
    public ResponseEntity<List<GiornoAperturaResponse>> getOrariStudio() {
        AppUser user = appUserService.getUtenteAutenticato();
        Medico medico = user.getMedico();

        if (medico == null) {
            Paziente paziente = user.getPaziente();
            if (paziente == null || paziente.getMedico() == null) {
                return ResponseEntity.notFound().build();
            }
            medico = paziente.getMedico();
        }

        List<GiornoAperturaResponse> giorni = studioService.getGiorniApertura(medico);
        return ResponseEntity.ok(giorni);
    }


    @PutMapping("/orari")
    public ResponseEntity<Void> aggiornaOrariStudio(
            @RequestBody List<GiornoAperturaRequest> giorniRequest,
            @RequestParam(required = false) String dataChiusura) {

        AppUser user = appUserService.getUtenteAutenticato();
        Medico medico = user.getMedico();

        if (medico == null) {
            return ResponseEntity.badRequest().build();
        }

        LocalDate data = null;
        if (dataChiusura != null) {
            data = LocalDate.parse(dataChiusura);
        }

        if (data != null) {
            studioService.aggiornaGiornoApertura(medico, giorniRequest);

        } else {
            studioService.aggiornaGiornoApertura(medico, giorniRequest);
        }

        return ResponseEntity.noContent().build();
    }

}




