package cartella.clinica.back_end_capstone.anamnesi;

import cartella.clinica.back_end_capstone.anamnesi.fattoriDiRischio.FattoreDiRischio;
import cartella.clinica.back_end_capstone.enums.SiONO;
import cartella.clinica.back_end_capstone.pazienti.Paziente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "anamnesi")

public class Anamnesi {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataInserimentoAnamnesi = LocalDate.now();

    @Column
    private String descrizioneAnamnesi;

    @Column(length = 9)
    private Integer anno;

    @Column(length = 60)
    private String titolo;

    @Embedded
    private FattoreDiRischio  fattoreDiRischio;

    @ManyToOne
    @JoinColumn(name = "paziente_id", nullable = false)
    private Paziente paziente;

}