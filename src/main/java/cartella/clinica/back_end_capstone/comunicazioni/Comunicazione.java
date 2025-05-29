package cartella.clinica.back_end_capstone.comunicazioni;

import cartella.clinica.back_end_capstone.medici.Medico;
import cartella.clinica.back_end_capstone.pazienti.Paziente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Comunicazione")

public class Comunicazione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDate dataComunicazione = LocalDate.now();

    private String oggetto;

    private String testo;

    @ManyToOne
    @JoinColumn(name = "paziente_id", nullable = true)
    private Paziente paziente;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;


}