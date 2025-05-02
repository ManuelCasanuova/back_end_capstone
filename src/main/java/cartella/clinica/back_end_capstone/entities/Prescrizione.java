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
@Table(name = "prescrizioni")
public class Prescrizione {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    @Column(name = "data_prescrizione", nullable = false)
    private LocalDate dataPrescrizione;


    @Column(name = "farmaco")
    private String farmaco;


    @Column(name = "dosaggio")
    private String dosaggio;


    @Column(name = "modalita_assunzione")
    private String modalitaAssunzione;


    @Column(name = "note", length = 2000)
    private String note;


    @ManyToOne
    @JoinColumn(name = "paziente_id", nullable = false)
    private Paziente paziente;


    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;
}