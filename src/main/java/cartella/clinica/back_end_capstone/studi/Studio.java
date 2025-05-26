package cartella.clinica.back_end_capstone.studi;

import cartella.clinica.back_end_capstone.GiorniApertura.GiornoApertura;
import cartella.clinica.back_end_capstone.medici.Medico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "studi")

public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String indirizzo;
    private String telefono;

    @OneToOne
    @JoinColumn(name = "medico_id", unique = true)
    private Medico medico;

    @OneToMany(mappedBy = "studio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GiornoApertura> giorniApertura = new ArrayList<>();

}