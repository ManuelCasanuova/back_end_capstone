package cartella.clinica.back_end_capstone.utenti;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtenteAvatarResponse {
    private Long pazienteId;
    private String avatar;
}
