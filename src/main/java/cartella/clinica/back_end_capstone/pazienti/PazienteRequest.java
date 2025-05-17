package cartella.clinica.back_end_capstone.pazienti;


import cartella.clinica.back_end_capstone.enums.Genere;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PazienteRequest {

    private String nome;
    private String cognome;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDiNascita;
    private String gruppoSanguigno;
    private Genere sesso;
    private String codiceFiscale;
    private String luogoDiNascita;
    private String indirizzoResidenza;
    private String domicilio;
    private String telefonoCellulare;
    private String telefonoFisso;
    private String email;
    private String avatar;
    private String esenzione;
    private String password;
}
