package cartella.clinica.back_end_capstone.anamnesi;

import cartella.clinica.back_end_capstone.enums.SiONO;
import lombok.Data;

import java.time.LocalDate;


@Data
public class AnamnesiResponse {

    private Long id;
    private LocalDate dataInserimentoAnamnesi;
    private LocalDate dataAnamnesi;
    private String descrizioneAnamnesi;
    private SiONO fumatore;
    private LocalDate dataInizioFumo;
    private SiONO usoDiAlcol;
    private LocalDate dataUltimaAssunzioneAlcol;
    private SiONO usoDiDroga;
    private LocalDate dataUltimaAssunzioneDroga;
    private Long pazienteId;
}
