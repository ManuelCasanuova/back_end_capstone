package cartella.clinica.back_end_capstone.notifiche;

import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.pazienti.Paziente;
import cartella.clinica.back_end_capstone.utenti.Utente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notifiche")

public class Notifica {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String messaggio;

    private boolean letta = false;

    private LocalDateTime dataCreazione = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "destinatario_id", nullable = false)
    private AppUser destinatario;

    @ManyToOne
    @JoinColumn(name = "paziente_id")
    private Paziente paziente;

}