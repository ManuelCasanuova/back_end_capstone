package cartella.clinica.back_end_capstone.pazienti;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PazienteResponse {

    private Long id;
    private String nome;
    private String cognome;
    private LocalDate dataDiNascita;
    private String gruppoSanguigno;
    private String codiceFiscale;
    private String luogoDiNascita;
    private String indirizzoResidenza;
    private String domicilio;
    private String telefonoCellulare;
    private String telefonoFisso;

    public static PazienteResponse from(Paziente p) {
        PazienteResponse res = new PazienteResponse();
        res.id = p.getId();
        res.nome = p.getUtente() != null ? p.getUtente().getNome() : null;
        res.cognome = p.getUtente() != null ? p.getUtente().getCognome() : null;
        res.dataDiNascita = p.getDataDiNascita();
        res.gruppoSanguigno = p.getGruppoSanguigno().name();
        res.codiceFiscale = p.getCodiceFiscale();
        res.luogoDiNascita = p.getLuogoDiNascita();
        res.indirizzoResidenza = p.getIndirizzoResidenza();
        res.domicilio = p.getDomicilio();
        res.telefonoCellulare = p.getTelefonoCellulare();
        res.telefonoFisso = p.getTelefonoFisso();
        return res;
    }

}
