package cartella.clinica.back_end_capstone.farmaci;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FarmacoResponse {

    private Long id;
    private String nomeCommerciale;
    private String codiceATC;
    private String formaFarmaceutica;
    private String dosaggio;
    private String note;
    private LocalDate dataInserimento;
    private Long pazienteId;
}