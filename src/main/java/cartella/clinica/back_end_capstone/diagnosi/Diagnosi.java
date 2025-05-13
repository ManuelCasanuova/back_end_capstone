package cartella.clinica.back_end_capstone.diagnosi;

import cartella.clinica.back_end_capstone.diagnosi.stati.StatoDiagnosi;
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
    private LocalDate dataInserimentoDiagnosi;

    private String trattamentoRaccomandato;

    @Column(nullable = false)
    private LocalDate dataDiagnosi;

    private LocalDate dataFineDiagnosi;

    @Column(length = 5000)
    private String descrizioneDiagnosi;

    @ManyToOne
    @JoinColumn(name = "paziente_id")
    private Paziente paziente;


    @ManyToOne
    @JoinColumn(name = "stato_diagnosi", nullable = false)
    private StatoDiagnosi statoDiagnosi;


}