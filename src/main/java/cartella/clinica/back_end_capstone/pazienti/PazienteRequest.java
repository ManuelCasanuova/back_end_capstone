package cartella.clinica.back_end_capstone.pazienti;


import cartella.clinica.back_end_capstone.enums.Sesso;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PazienteRequest {

    private String nome;
    private String cognome;
    private String dataDiNascita;
    private String gruppoSanguigno;
    private Sesso sesso;
    private String codiceFiscale;
    private String luogoDiNascita;
    private String indirizzoResidenza;
    private String domicilio;
    private String telefonoCellulare;
    private String telefonoFisso;
    private String email;
}
