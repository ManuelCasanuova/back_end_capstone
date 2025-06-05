package cartella.clinica.back_end_capstone.anamnesi.fattoriDiRischio;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDate;

@Data
@Embeddable
public class FattoreDiRischio {

    @Column(nullable = false)
    private boolean fumatore;

    private LocalDate dataInizioFumo;

    @Column(nullable = false)
    private boolean usoAlcol;

    private LocalDate dataUltimaAssunzioneAlcol;

    @Column(nullable = false)
    private boolean usoStupefacente;

    private LocalDate dataUltimaAssunzioneStupefacente;

    @Column(length = 1000)
    private String note;
}
