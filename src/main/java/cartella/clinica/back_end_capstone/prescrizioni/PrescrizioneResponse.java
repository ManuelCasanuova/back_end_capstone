package cartella.clinica.back_end_capstone.prescrizioni;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class PrescrizioneResponse {
    Long id;
    LocalDateTime dataOraPrescrizione;
    Long pazienteId;
    Long farmacoId;
}
