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

    public static PazienteResponse from(Paziente p) {
        PazienteResponse res = new PazienteResponse();
        res.id = p.getId();
        res.nome = p.getUtente() != null ? p.getUtente().getNome() : null;
        res.cognome = p.getUtente() != null ? p.getUtente().getCognome() : null;
        res.email = p.getUtente() != null ? p.getUtente().getEmail() : null;
        res.dataDiNascita = p.getDataDiNascita();
        res.gruppoSanguigno = p.getGruppoSanguigno();
        res.codiceFiscale = p.getCodiceFiscale();
        res.luogoDiNascita = p.getLuogoDiNascita();
        res.indirizzoResidenza = p.getIndirizzoResidenza();
        res.domicilio = p.getDomicilio();
        res.telefonoCellulare = p.getTelefonoCellulare();
        res.telefonoFisso = p.getTelefonoFisso();
        res.sesso = p.getSesso();
        return res;
    }

}
