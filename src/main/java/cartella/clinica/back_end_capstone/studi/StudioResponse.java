package cartella.clinica.back_end_capstone.studi;

import java.util.List;

import cartella.clinica.back_end_capstone.GiorniApertura.GiornoAperturaResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudioResponse {
    private String nome;
    private String indirizzo;
    private String telefonoStudio;
    private String nomeMedico;
    private String cognomeMedico;
    private String emailMedico;
    private String telefonoCellulareMedico;
    private String specializzazioneMedico;
    private List<GiornoAperturaResponse> giorniApertura;

    public StudioResponse(String nome, String indirizzo, String telefonoStudio,
                          String nomeMedico, String cognomeMedico, String emailMedico, String telefonoCellulareMedico,
                          String specializzazioneMedico, List<GiornoAperturaResponse> giorniApertura) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.telefonoStudio = telefonoStudio;
        this.nomeMedico = nomeMedico;
        this.cognomeMedico = cognomeMedico;
        this.emailMedico = emailMedico;
        this.specializzazioneMedico = specializzazioneMedico;
        this.telefonoCellulareMedico = telefonoCellulareMedico;
        this.giorniApertura = giorniApertura;
    }
}

