package cartella.clinica.back_end_capstone.diagnosi;

import cartella.clinica.back_end_capstone.diagnosi.stati.StatoDiagnosi;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor


public class DiagnosiResponse {

    Long id;
    String codiceCIM10;
    LocalDate dataInserimentoDiagnosi;
    String trattamentoRaccomandato;
    LocalDate dataDiagnosi;
    LocalDate dataFineDiagnosi;
    String descrizioneDiagnosi;
    Long pazienteId;
    StatoDiagnosi statoDiagnosi;

}
