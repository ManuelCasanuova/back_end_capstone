package cartella.clinica.back_end_capstone.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "diagnosi")
public class Diagnosi {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    @Column(name = "data_diagnosi", nullable = false)
    private LocalDate dataDiagnosi;


    @Column(name = "descrizione_diagnosi", nullable = false, length = 2000)
    private String descrizioneDiagnosi;


    @ManyToOne
    @JoinColumn(name = "paziente_id", nullable = false)
    private Paziente paziente;


    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;
}
