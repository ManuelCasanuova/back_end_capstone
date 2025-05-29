package cartella.clinica.back_end_capstone.comunicazioni;



import lombok.Data;

import java.time.LocalDate;

@Data
public class ComunicazioneResponse {
    private Long id;
    private LocalDate dataComunicazione;
    private String oggetto;
    private String testo;
    private Long medicoId;
}
