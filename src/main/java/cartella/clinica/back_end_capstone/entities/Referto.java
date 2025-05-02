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
@Table(name = "referti")
public class Referto {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    @Column(name = "data_referto", nullable = false)
    private LocalDate dataReferto;


    @Column(name = "tipo_esame", nullable = false)
    private String tipoEsame;


    @Lob
    @Column(name = "descrizione", nullable = false, length = 10000)
    private String descrizione;


    @Column(name = "percorso_file")
    private String percorsoFile;


    @ManyToOne
    @JoinColumn(name = "paziente_id", nullable = false)
    private Paziente paziente;


    @ManyToOne
    @JoinColumn(name = "medico_richiedente_id", nullable = false)
    private Medico medicoRichiedente;


    @ManyToOne
    @JoinColumn(name = "medico_refertante_id")
    private Medico medicoRefertante;
}