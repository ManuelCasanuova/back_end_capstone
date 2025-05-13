package cartella.clinica.back_end_capstone.appuntamenti;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppuntamentoRequest {

    @NotNull(message="La data e l'ora dell'appuntamento sono obbligatorie")
    private LocalDateTime dataOraAppuntamento;

    @NotNull(message="Il motivo della richiesta è obbligatorio")
    private String motivoRichiesta;

    @NotNull(message="L'id del paziente è obbligatorio")
    private Long pazienteId;
}
