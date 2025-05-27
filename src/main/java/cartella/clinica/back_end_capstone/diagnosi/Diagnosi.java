package cartella.clinica.back_end_capstone.diagnosi;

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
@Table(name = "diagnosi")
public class Diagnosi {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String codiceCIM10;

    @Column(nullable = false)
    private LocalDate dataDiagnosi;

    @Column(nullable = false)
    private LocalDate dataInserimentoDiagnosi;

    private String trattamentoRaccomandato;

    @Column(length = 5000)
    private String descrizioneDiagnosi;

    @ManyToOne
    @JoinColumn(name = "paziente_id")
    private Paziente paziente;

    @PrePersist
    public void prePersist() {
        if (dataInserimentoDiagnosi == null) {
            dataInserimentoDiagnosi = LocalDate.now();
        }
    }
}
