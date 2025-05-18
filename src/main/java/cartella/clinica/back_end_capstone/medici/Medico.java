package cartella.clinica.back_end_capstone.medici;

import cartella.clinica.back_end_capstone.utenti.Utente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medici")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String specializzazione;
    private String telefonoStudio;

    @OneToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;
}