package cartella.clinica.back_end_capstone.auth;

import cartella.clinica.back_end_capstone.pazienti.Paziente;
import cartella.clinica.back_end_capstone.pazienti.PazienteRepository;

import cartella.clinica.back_end_capstone.pazienti.PazienteRequest;
import cartella.clinica.back_end_capstone.pazienti.PazienteResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;



@RestController

public class AuthController {

    @Autowired
    private  AppUserService appUserService;

    @Autowired

    private PazienteRepository pazienteRepository;

    @PostMapping("/register")
    public ResponseEntity<PazienteResponse> register(@RequestBody PazienteRequest pazienteRequest) {
        AppUser appUser = appUserService.registerUser(pazienteRequest, "Password123!");

        Paziente paziente = pazienteRepository.findByAppUser(appUser)
                .orElseThrow(() -> new RuntimeException("Paziente non trovato dopo la creazione"));

        PazienteResponse response = PazienteResponse.from(paziente);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = appUserService.authenticateUser(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
