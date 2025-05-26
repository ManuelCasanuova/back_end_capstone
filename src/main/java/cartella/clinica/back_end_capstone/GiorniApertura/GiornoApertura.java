package cartella.clinica.back_end_capstone.GiorniApertura;

import cartella.clinica.back_end_capstone.studi.Studio;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiornoApertura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek giorno;

    private LocalTime inizioMattina;
    private LocalTime fineMattina;

    private LocalTime inizioPomeriggio;
    private LocalTime finePomeriggio;

    private boolean chiuso;

    @ManyToOne
    @JoinColumn(name = "studio_id")
    private Studio studio;
}
