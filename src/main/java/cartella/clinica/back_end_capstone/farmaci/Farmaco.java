package cartella.clinica.back_end_capstone.farmaci;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "farmaci")

public class Farmaco {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "nome_commerciale", nullable = false)
    private String nomeCommerciale;

    @Column(name = "codice_atc", nullable = false)
    private String codiceATC;

    @Column(name = "forma_farmaceutica")
    private String formaFarmaceutica;

    @Column(name = "dosaggio")
    private String dosaggio;

    @Column(name = "note", length = 1000)
    private String note;


}