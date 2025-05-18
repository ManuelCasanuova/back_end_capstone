package cartella.clinica.back_end_capstone.utenti;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtenteResponse {
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String ruolo;
    private String tipoProfilo;
}
