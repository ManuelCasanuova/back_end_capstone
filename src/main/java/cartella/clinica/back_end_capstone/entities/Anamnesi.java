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
@Table(name = "anamnesi")
public class Anamnesi {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    @Column(name = "data_evento", nullable = false)
    private LocalDate dataEvento;


    @Column(name = "descrizione_evento", nullable = false, length = 2000)
    private String descrizioneEvento;


    @ManyToOne
    @JoinColumn(name = "paziente_id", nullable = false)
    private Paziente paziente;


    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;
}