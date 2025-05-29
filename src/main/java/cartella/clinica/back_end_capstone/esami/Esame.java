package cartella.clinica.back_end_capstone.esami;

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
@Table(name = "esami")

public class Esame {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String nomeFile;

    @Column(nullable = false)
    private LocalDate dataCaricamento = LocalDate.now();


    private LocalDate dataEsame;

    @Column(nullable = false)
    private String tipoFile;

    @Lob
    private byte[] file;

    @Column(length = 5000)
    private String note;

    @ManyToOne
    @JoinColumn(name = "paziente_id")
    private Paziente paziente;


}