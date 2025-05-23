package cartella.clinica.back_end_capstone.utenti;

import cartella.clinica.back_end_capstone.auth.AppUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "utenti")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    private String nome;
    private String cognome;
    private String avatar;

    @OneToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;


    @JsonProperty("avatar")
    public String getAvatar() {
        if (avatar == null || avatar.isEmpty()) {
            String nomeIniziale = nome != null && !nome.isEmpty() ? nome.substring(0,1).toUpperCase() : "";
            String cognomeIniziale = cognome != null && !cognome.isEmpty() ? cognome.substring(0,1).toUpperCase() : "";
            String initials = nomeIniziale + "+" + cognomeIniziale;
            return "https://ui-avatars.com/api/?name=" + initials + "&background=0D8ABC&color=fff&rounded=true";
        }
        return avatar;
    }
}