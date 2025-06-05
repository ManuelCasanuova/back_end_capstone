package cartella.clinica.back_end_capstone.anamnesi;
import cartella.clinica.back_end_capstone.anamnesi.fattoriDiRischio.FattoreDiRischioResponse;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AnamnesiResponse {

    private Long id;
    private LocalDate dataInserimentoAnamnesi;
    private String descrizioneAnamnesi;
    private FattoreDiRischioResponse fattoreDiRischio;
    private Long pazienteId;
}
