package cartella.clinica.back_end_capstone.auth;

import cartella.clinica.back_end_capstone.pazienti.Paziente;
import cartella.clinica.back_end_capstone.pazienti.PazienteRepository;

import cartella.clinica.back_end_capstone.pazienti.PazienteRequest;
import cartella.clinica.back_end_capstone.pazienti.PazienteResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController

public class AuthController {

    @Autowired
    private  AppUserService appUserService;

    @Autowired
    private PazienteRepository pazienteRepository;

    @PostMapping("/register")
    public ResponseEntity<PazienteResponse> register(@RequestBody RegisterRequest request) {
        AppUser appUser = appUserService.registerUser(request.getPazienteRequest(), request.getPassword());

        Paziente paziente = pazienteRepository.findByAppUser(appUser)
                .orElseThrow(() -> new RuntimeException("Paziente non trovato dopo la creazione"));

        PazienteResponse response = PazienteResponse.from(paziente);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = appUserService.authenticateUser(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(response);
    }





        @PutMapping("/change-password")
        public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
            appUserService.changePassword(request);
            return ResponseEntity.ok("Password aggiornata correttamente");
        }

}
