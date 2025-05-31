package cartella.clinica.back_end_capstone.anamnesi;

import cartella.clinica.back_end_capstone.enums.SiONO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;


@Data
public class AnamnesiRequest {

    @NotNull(message = "L'ID del paziente è obbligatorio")
    private Long pazienteId;

    private LocalDate dataAnamnesi;

    private String descrizioneAnamnesi;

    private SiONO fumatore;

    private LocalDate dataInizioFumo;

    private SiONO usoDiAlcol;

    private LocalDate dataUltimaAssunzioneAlcol;

    private SiONO usoDiDroga;

    private LocalDate dataUltimaAssunzioneDroga;
}