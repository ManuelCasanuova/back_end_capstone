package cartella.clinica.back_end_capstone.diagnosi;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DiagnosiRequest {


    @NotNull(message = "La data della diagnosi è obbligatoria")
    private LocalDate dataDiagnosi;

    @NotNull(message = "Il codice CIM10 della diagnosi é obbligatorio")
    private String codiceCIM10;

    @NotNull(message = "Specificare il paziente")
    private Long pazienteId;

    @NotNull(message = "Lo stato della diagnosi è obbligatorio")
    private String statoDiagnosi;
}
