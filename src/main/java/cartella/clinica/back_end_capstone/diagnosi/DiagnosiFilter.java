package cartella.clinica.back_end_capstone.diagnosi;


import lombok.Data;

import java.time.LocalDate;

@Data
public class DiagnosiFilter {

    private Long pazienteId;
    private String codiceCIM10;
    private LocalDate dataDiagnosi;

}
