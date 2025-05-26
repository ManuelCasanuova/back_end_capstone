package cartella.clinica.back_end_capstone.utenti;


import cartella.clinica.back_end_capstone.auth.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtenteResponse {
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String avatar;
    private Set<Role> roles;
    private Long id;

}
