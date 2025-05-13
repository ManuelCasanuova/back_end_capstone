package cartella.clinica.back_end_capstone.prescrizioni;

import cartella.clinica.back_end_capstone.farmaci.Farmaco;
import cartella.clinica.back_end_capstone.pazienti.Paziente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "prescrizioni")

public class Prescrizione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "data_prescrizione", nullable = false, updatable = false)
    private LocalDateTime dataPrescrizione = LocalDateTime.now();



    @Column(name = "frequenza")
    private String frequenza;

    @Column(name = "durata")
    private String durata;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "paziente_id", nullable = false)
    private Paziente paziente;

    @ManyToOne
    @JoinColumn(name = "farmaco_id", nullable = false)
    private Farmaco farmaco;
}