package cartella.clinica.back_end_capstone.appuntamenti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppuntamentoResponse {

    private Long id;
    private LocalDateTime dataOraAppuntamento;
    private String motivoRichiesta;
    private Long pazienteId;
    private String nome;
    private String cognome;
    private String avatar;
}
