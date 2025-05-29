package cartella.clinica.back_end_capstone.comunicazioni;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ComunicazioneRequest {
    @NotBlank(message = "Oggetto è obbligatorio")
    private String oggetto;

    @NotBlank(message = "Testo è obbligatorio")
    private String testo;


}
