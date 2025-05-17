package cartella.clinica.back_end_capstone.pazienti;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PazienteResponse {

    private Long id;
    private String nome;
    private String cognome;
    private LocalDate dataDiNascita;
    private String gruppoSanguigno;
    private String codiceFiscale;
    private String luogoDiNascita;
    private String indirizzoResidenza;
    private String domicilio;
    private String telefonoCellulare;
    private String telefonoFisso;
    private String email;
}
