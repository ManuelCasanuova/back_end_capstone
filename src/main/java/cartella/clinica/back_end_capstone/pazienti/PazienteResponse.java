package cartella.clinica.back_end_capstone.pazienti;

import lombok.Data;

@Data
public class PazienteResponse {

    private Long id;
    private String nome;
    private String cognome;
    private String dataDiNascita;
    private String gruppoSanguigno;
    private String codiceFiscale;
    private String luogoDiNascita;
    private String indirizzoResidenza;
    private String domicilio;
    private String telefonoCellulare;
    private String telefonoFisso;
    private String email;
}
