package cartella.clinica.back_end_capstone.auth;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
