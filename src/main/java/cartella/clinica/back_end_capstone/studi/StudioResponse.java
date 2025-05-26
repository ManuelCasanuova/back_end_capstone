package cartella.clinica.back_end_capstone.studi;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudioResponse {

    private String nomeStudio;
    private String indirizzo;
    private String telefono;
    private String inizioMattina;
    private String fineMattina;
    private String inizioPomeriggio;
    private String finePomeriggio;
    private String nomeMedico;
    private String cognomeMedico;
    private String emailMedico;
    private String specializzazione;
}
