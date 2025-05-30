package cartella.clinica.back_end_capstone.pazienti;

import cartella.clinica.back_end_capstone.enums.Genere;
import cartella.clinica.back_end_capstone.enums.GruppoSanguigno;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PazienteResponse {
    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private LocalDate dataDiNascita;
    private GruppoSanguigno gruppoSanguigno;
    private String codiceFiscale;
    private String luogoDiNascita;
    private String indirizzoResidenza;
    private String domicilio;
    private String telefonoCellulare;
    private String telefonoFisso;
    private Genere sesso;
    private String avatar;

    public static PazienteResponse from(Paziente p) {
        PazienteResponse res = new PazienteResponse();
        res.id = p.getId();

        if (p.getUtente() != null) {
            res.nome = p.getUtente().getNome();
            res.cognome = p.getUtente().getCognome();
            res.email = p.getUtente().getEmail();

            res.telefonoCellulare = p.getUtente().getTelefonoCellulare();
            res.telefonoFisso = p.getUtente().getTelefonoFisso();

            res.avatar = p.getUtente().getAvatar();
        } else {
            res.nome = null;
            res.cognome = null;
            res.email = null;
            res.telefonoCellulare = null;
            res.telefonoFisso = null;
            res.avatar = null;
        }

        res.dataDiNascita = p.getDataDiNascita();
        res.gruppoSanguigno = p.getGruppoSanguigno();
        res.codiceFiscale = p.getCodiceFiscale();
        res.luogoDiNascita = p.getLuogoDiNascita();
        res.indirizzoResidenza = p.getIndirizzoResidenza();
        res.domicilio = p.getDomicilio();
        res.sesso = p.getSesso();

        return res;
    }
}
