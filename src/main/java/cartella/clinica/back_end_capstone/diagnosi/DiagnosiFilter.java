package cartella.clinica.back_end_capstone.diagnosi;


import lombok.Data;

@Data
public class DiagnosiFilter {

    private Long pazienteId;
    private String statoDiagnosi;
    private String codiceCIM10;
    private String dataDiagnosi;

}
