package cartella.clinica.back_end_capstone.pazienti;

import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.enums.GruppoSanguigno;
import cartella.clinica.back_end_capstone.enums.Genere;
import cartella.clinica.back_end_capstone.utenti.Utente;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pazienti")
public class Paziente {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "dataDiNascita", nullable = false)
    private LocalDate dataDiNascita;


    @Enumerated(EnumType.STRING)
    private GruppoSanguigno gruppoSanguigno;


    @Enumerated(EnumType.STRING)
    private Genere sesso;


    private String dataDiRegistrazione = LocalDate.now().toString();

    @Column(name = "codiceFiscale", nullable = false, unique = true)
    private String codiceFiscale;


    @Column(name = "luogoDiNascita", nullable = false)
    private String luogoDiNascita;


    @Column(name = "residenza", nullable = false)
    private String indirizzoResidenza;

    private String domicilio;


    @Column(name = "telefonoCellulare", nullable = false)
    private String telefonoCellulare;


    @Column(name = "telefonoFisso")
    private String telefonoFisso;

    @Column(name = "esenzione")
    private String esenzione = " - ";

    @OneToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @OneToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;


}
