package cartella.clinica.back_end_capstone.anamnesi;


import cartella.clinica.back_end_capstone.anamnesi.fattoriDiRischio.FattoreDiRischioRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AnamnesiRequest {

    @NotNull(message = "L'ID del paziente è obbligatorio")
    private Long pazienteId;

    private String descrizioneAnamnesi;

    private FattoreDiRischioRequest fattoreDiRischio;
}