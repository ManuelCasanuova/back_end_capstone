package cartella.clinica.back_end_capstone.auth;

import cartella.clinica.back_end_capstone.pazienti.PazienteRequest;
import lombok.Data;

@Data
public class RegisterRequest {
    private PazienteRequest paziente;
    private String password;
}
