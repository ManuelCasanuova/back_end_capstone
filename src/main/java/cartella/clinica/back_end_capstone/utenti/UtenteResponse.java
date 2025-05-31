package cartella.clinica.back_end_capstone.utenti;

import cartella.clinica.back_end_capstone.auth.Role;
import cartella.clinica.back_end_capstone.studi.StudioResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtenteResponse {
    private Long id;
    private String username;
    private Set<Role> roles;

    // Dati base utente
    private String nome;
    private String cognome;
    private String email;
    private String avatar;
    private String telefonoCellulare;
    private String telefonoFisso;

    // Dati paziente
    private LocalDate dataDiNascita;
    private String codiceFiscale;
    private String sesso;
    private String gruppoSanguigno;;
    private String indirizzoResidenza;
    private String domicilio;
    private String esenzione;
    private Long pazienteId;

    // Dati medico/studio
    private StudioResponse studio;
    private Long medicoId;

    private boolean passwordModificata;
}

