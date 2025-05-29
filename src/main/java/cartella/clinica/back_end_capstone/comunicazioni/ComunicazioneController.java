package cartella.clinica.back_end_capstone.comunicazioni;

import cartella.clinica.back_end_capstone.medici.Medico;
import cartella.clinica.back_end_capstone.medici.MedicoService;
import cartella.clinica.back_end_capstone.utenti.Utente;
import cartella.clinica.back_end_capstone.utenti.UtenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comunicazioni")

public class ComunicazioneController {

  @Autowired
  private  ComunicazioneService comunicazioneService;
  @Autowired  private  MedicoService medicoService;
  @Autowired  private  UtenteService utenteService;

    private Medico getMedicoLoggato() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Utente utente = utenteService.getByUsername(username);
        return medicoService.getByUtente(utente);
    }

    @GetMapping
    public ResponseEntity<List<ComunicazioneResponse>> getComunicazioniDelMedico() {
        Medico medico = getMedicoLoggato();
        return ResponseEntity.ok(comunicazioneService.getComunicazioniGlobaliByMedico(medico));
    }

    @PostMapping
    public ResponseEntity<ComunicazioneResponse> creaComunicazione(@RequestBody @Valid ComunicazioneRequest request) {
        Medico medico = getMedicoLoggato();
        Comunicazione comunicazione = new Comunicazione();
        comunicazione.setOggetto(request.getOggetto());
        comunicazione.setTesto(request.getTesto());
        comunicazione.setMedico(medico);
        return ResponseEntity.ok(comunicazioneService.salvaComunicazioneGlobal(comunicazione));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComunicazioneResponse> aggiornaComunicazione(
            @PathVariable Long id,
            @RequestBody @Valid ComunicazioneRequest request
    ) {
        Medico medico = getMedicoLoggato();
        return ResponseEntity.ok(comunicazioneService.aggiornaComunicazione(id, request, medico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> elimina(@PathVariable Long id) {
        comunicazioneService.eliminaComunicazione(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/globali")
    public ResponseEntity<List<ComunicazioneResponse>> getComunicazioniGlobaliPerPaziente() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Utente utente = utenteService.getByUsername(username);

        if (utente == null || utente.getAppUser() == null || utente.getAppUser().getPaziente() == null) {
            return ResponseEntity.badRequest().build();
        }

        Long medicoId = utente.getAppUser().getPaziente().getMedico().getId();

        List<ComunicazioneResponse> comunicazioni = comunicazioneService.getComunicazioniGlobaliPerPaziente(medicoId);
        return ResponseEntity.ok(comunicazioni);
    }
}

