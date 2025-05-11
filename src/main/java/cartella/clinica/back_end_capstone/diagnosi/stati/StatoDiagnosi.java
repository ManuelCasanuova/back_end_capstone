package cartella.clinica.back_end_capstone.diagnosi.stati;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stato_diagnosi")


public class StatoDiagnosi {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String nomeStatoDiagnosi;

    public StatoDiagnosi(String nomeStatoDiagnosi) {
        this.nomeStatoDiagnosi = nomeStatoDiagnosi;

}

}
