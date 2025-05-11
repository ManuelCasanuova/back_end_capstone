package cartella.clinica.back_end_capstone.pazienti;

import cartella.clinica.back_end_capstone.enums.GruppoSanguigno;
import lombok.Data;

@Data
public class PazienteFilter {

    private String nomeParziale;
    private String cognomeParziale;
    private String codiceFiscale;
    private String email;
    private String telefonoCellulare;
    private String dataDiRegistrazione;
    private String dataDiNascita;
    private GruppoSanguigno gruppoSanguigno;
}
