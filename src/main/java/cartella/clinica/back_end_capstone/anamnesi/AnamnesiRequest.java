package cartella.clinica.back_end_capstone.anamnesi;


import cartella.clinica.back_end_capstone.anamnesi.fattoriDiRischio.FattoreDiRischioRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;



@Data
public class AnamnesiRequest {

    @NotNull(message = "L'ID del paziente è obbligatorio")
    private Long pazienteId;

    private String descrizioneAnamnesi;

    private Integer anno;


    @Size(message = "Il titolo può contenere al massimo 60 caratteri")
    private String titolo;

    private FattoreDiRischioRequest fattoreDiRischio;
}