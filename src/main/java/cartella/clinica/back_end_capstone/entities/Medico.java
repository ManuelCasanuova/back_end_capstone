package cartella.clinica.back_end_capstone.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "medici")

public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cognome", nullable = false)
    private String cognome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "indirizzo", nullable = false)
    private String indirizzo;

    @Column(name = "specializzazione", nullable = false)
    private String specializzazione;

    @OneToMany(mappedBy = "medico")
    private List<Paziente> pazienti = new ArrayList<>();

}