package cartella.clinica.back_end_capstone.farmaci;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FarmacoRequest {

    @NotBlank(message = "Il nome commerciale è obbligatorio")
    private String nomeCommerciale;

    @NotBlank(message = "Il codice ATC è obbligatorio")
    private String codiceATC;

    private String formaFarmaceutica;

    private Long pazienteId;

    private String dosaggio;

    @Size(max = 1000, message = "Le note non possono superare i 1000 caratteri")
    private String note;
}