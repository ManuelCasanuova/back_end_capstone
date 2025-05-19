package cartella.clinica.back_end_capstone.pazienti;


import cartella.clinica.back_end_capstone.enums.Genere;
import cartella.clinica.back_end_capstone.enums.GruppoSanguigno;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PazienteRequest {
    private Long id;

    private String nome;
    private String cognome;
    private String email;
    private String password;

    private LocalDate dataDiNascita;
    private GruppoSanguigno gruppoSanguigno;
    private Genere sesso;
    private String codiceFiscale;
    private String luogoDiNascita;
    private String indirizzoResidenza;
    private String domicilio;
    private String telefonoCellulare;
    private String telefonoFisso;
    private String esenzione;
}
