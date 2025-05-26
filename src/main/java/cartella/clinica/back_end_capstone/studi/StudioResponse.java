package cartella.clinica.back_end_capstone.studi;

import cartella.clinica.back_end_capstone.GiorniApertura.GiornoAperturaResponse;

import java.util.List;

public class StudioResponse {
    private String nome;
    private String indirizzo;
    private String telefono;
    private String nomeMedico;
    private String cognomeMedico;
    private String emailMedico;
    private String specializzazioneMedico;
    private List<GiornoAperturaResponse> giorniApertura;

    public StudioResponse(String nome, String indirizzo, String telefono,
                          String nomeMedico, String cognomeMedico, String emailMedico,
                          String specializzazioneMedico, List<GiornoAperturaResponse> giorniApertura) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.nomeMedico = nomeMedico;
        this.cognomeMedico = cognomeMedico;
        this.emailMedico = emailMedico;
        this.specializzazioneMedico = specializzazioneMedico;
        this.giorniApertura = giorniApertura;
    }

    public String getNome() {
        return nome;
    }
    public String getIndirizzo() {
        return indirizzo;
    }
    public String getTelefono() {
        return telefono;
    }
    public String getNomeMedico() {
        return nomeMedico;
    }
    public String getCognomeMedico() {
        return cognomeMedico;
    }
    public String getEmailMedico() {
        return emailMedico;
    }
    public String getSpecializzazioneMedico() {
        return specializzazioneMedico;
    }
    public List<GiornoAperturaResponse> getGiorniApertura() {
        return giorniApertura;
    }
}

