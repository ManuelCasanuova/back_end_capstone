package cartella.clinica.back_end_capstone.diagnosi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosiRequest {

    @NotNull(message = "La data della diagnosi è obbligatoria")
    private LocalDate dataDiagnosi;

    private String codiceCIM10;

    @NotNull(message = "Specificare il paziente")
    private Long pazienteId;

    private String trattamentoRaccomandato;

    @NotNull(message = "La descrizione della diagnosi è obbligatoria")
    private String descrizioneDiagnosi;
}


