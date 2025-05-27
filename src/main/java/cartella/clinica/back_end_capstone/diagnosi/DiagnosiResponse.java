package cartella.clinica.back_end_capstone.diagnosi;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DiagnosiResponse {

    private Long id;
    private String codiceCIM10;
    private LocalDate dataInserimentoDiagnosi;
    private String trattamentoRaccomandato;
    private LocalDate dataDiagnosi;
    private String descrizioneDiagnosi;
    private Long pazienteId;
}
