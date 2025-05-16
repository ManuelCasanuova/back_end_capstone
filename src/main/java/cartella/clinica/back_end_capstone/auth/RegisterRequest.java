package cartella.clinica.back_end_capstone.auth;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String nome;
    private String cognome;
}
