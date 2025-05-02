package cartella.clinica.back_end_capstone.entities;

import cartella.clinica.back_end_capstone.enums.Sesso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pazienti")
public class Paziente {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    @Column(name = "nome", nullable = false)
    private String nome;


    @Column(name = "cognome", nullable = false)
    private String cognome;


    private LocalDate dataDiNascita;


    @Enumerated(EnumType.STRING)
    private Sesso sesso;


    @Column(name = "codiceFiscale", nullable = false, unique = true)
    private String codiceFiscale;


    @Column(name = "luogoDiNascita", nullable = false)
    private String luogoDiNascita;


    @Column(name = "residenza", nullable = false)
    private String indirizzoResidenza;


    @Column(name = "telefono", nullable = false)
    private String telefonoCellulare;


    @Column(name = "telefonoFisso")
    private String telefonoFisso;


    @Column(name = "email", nullable = false, unique = true)
    private String email;


    private String password;


    private String Avatar;


    @Column(name = "esenzione")
    private String esenzione = " - ";


    @OneToMany(mappedBy = "paziente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Anamnesi> anamnesiList = new ArrayList<>();


    @OneToMany(mappedBy = "paziente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diagnosi> diagnosiList = new ArrayList<>();


    @OneToMany(mappedBy = "paziente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Referto> refertoList = new ArrayList<>();
}