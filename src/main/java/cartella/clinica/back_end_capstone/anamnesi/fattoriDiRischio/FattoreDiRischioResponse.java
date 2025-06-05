package cartella.clinica.back_end_capstone.anamnesi.fattoriDiRischio;

import lombok.Data;
import java.time.LocalDate;

@Data
public class FattoreDiRischioResponse {
    private boolean fumatore;
    private LocalDate dataInizioFumo;
    private boolean usoAlcol;
    private LocalDate dataUltimaAssunzioneAlcol;
    private boolean usoStupefacente;
    private LocalDate dataUltimaAssunzioneStupefacente;
    private String note;
}
