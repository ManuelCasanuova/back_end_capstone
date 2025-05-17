package cartella.clinica.back_end_capstone.pazienti;

import cartella.clinica.back_end_capstone.enums.GruppoSanguigno;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PazienteFilter {

    private String nomeParziale;
    private String cognomeParziale;
    private String codiceFiscale;
    private String telefonoCellulare;
    private String dataDiRegistrazione;
    private LocalDate dataDiNascita;
    private GruppoSanguigno gruppoSanguigno;
}
