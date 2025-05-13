package cartella.clinica.back_end_capstone.prescrizioni;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescrizioneRequest {

    @NotNull(message= "Il campo 'dataPrescrizione' non può essere vuoto")
    private LocalDateTime dataOraPrescrizione = LocalDateTime.now();

    @NotNull(message= "Il campo 'frequenza' non può essere vuoto")
    private String frequenza;

    @NotNull(message= "Il campo 'descrizione' non può essere vuoto")
    private String descrizione;

    @NotNull(message= "Il campo 'durata' non può essere vuoto")
    private String durata;

    @NotNull(message= "Il campo 'note' non può essere vuoto")
    private String note;

    @NotNull(message= "Specificare il paziente")
    private Long pazienteId;

    @NotNull(message= "Specificare il farmaco")
    private Long farmacoId;

}
