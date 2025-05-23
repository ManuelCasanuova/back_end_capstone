package cartella.clinica.back_end_capstone.appuntamenti;

import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.auth.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appuntamenti")
public class AppuntamentoController {

    @Autowired
    private AppuntamentoService appuntamentoService;

    @Autowired
    private AppUserRepository appUserRepository;

    @PostMapping
    public ResponseEntity createAppuntamento(@RequestBody AppuntamentoRequest request) {
        try {
            AppuntamentoResponse created = appuntamentoService.createAppuntamento(request);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<AppuntamentoResponse>> getAllAppuntamenti() {
        List<AppuntamentoResponse> appuntamenti = appuntamentoService.findAllAppuntamenti();
        return ResponseEntity.ok(appuntamenti);
    }

    @GetMapping("/{id}")
    public ResponseEntity getAppuntamentoById(@PathVariable Long id) {
        try {
            AppuntamentoResponse appuntamento = appuntamentoService.findAppuntamentoById(id);
            return ResponseEntity.ok(appuntamento);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/paziente/{id}")
    public ResponseEntity<List<AppuntamentoResponse>> getAppuntamentiByPazienteId(@PathVariable Long id) {
        List<AppuntamentoResponse> lista = appuntamentoService.findByPazienteId(id);
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateAppuntamento(
            @PathVariable Long id,
            @RequestBody AppuntamentoResponse appuntamentoRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        AppUser adminLoggato = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        try {
            AppuntamentoResponse aggiornato = appuntamentoService.updateAppuntamento(id, appuntamentoRequest, adminLoggato);
            return ResponseEntity.ok(aggiornato);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppuntamento(@PathVariable Long id) {
        try {
            appuntamentoService.deleteAppuntamento(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/statistiche")
    public ResponseEntity<Map<LocalDate, Long>> getStatisticheSettimanali() {
        Map<LocalDate, Long> statistiche = appuntamentoService.getStatisticheProssimi7Giorni();
        return ResponseEntity.ok(statistiche);
    }
}
