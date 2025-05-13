package cartella.clinica.back_end_capstone.anamnesi;

import cartella.clinica.back_end_capstone.enums.SiONO;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AnamnesiResponse {

    private Long id;

    private LocalDate dataInserimentoAnamnesi;

    private LocalDateTime dataAnamnesi;

    private String descrizioneAnamnesi;

    private SiONO fumatore;

    private Long pazienteId;
}