package cartella.clinica.back_end_capstone.GiorniApertura;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiornoAperturaResponse {
    private String giorno;
    private String nomeGiorno;
    private LocalTime inizioMattina;
    private LocalTime fineMattina;
    private LocalTime inizioPomeriggio;
    private LocalTime finePomeriggio;
    private boolean chiuso;
}

