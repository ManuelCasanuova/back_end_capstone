package cartella.clinica.back_end_capstone.GiorniApertura;

import java.time.LocalTime;

public class GiornoAperturaResponse {
    private String giorno;
    private String nomeGiorno;
    private LocalTime inizioMattina;
    private LocalTime fineMattina;
    private LocalTime inizioPomeriggio;
    private LocalTime finePomeriggio;
    private boolean chiuso;

    public GiornoAperturaResponse(String giorno, String nomeGiorno,
                                  LocalTime inizioMattina, LocalTime fineMattina,
                                  LocalTime inizioPomeriggio, LocalTime finePomeriggio,
                                  boolean chiuso) {
        this.giorno = giorno;
        this.nomeGiorno = nomeGiorno;
        this.inizioMattina = inizioMattina;
        this.fineMattina = fineMattina;
        this.inizioPomeriggio = inizioPomeriggio;
        this.finePomeriggio = finePomeriggio;
        this.chiuso = chiuso;
    }

    public String getGiorno() {
        return giorno;
    }
    public String getNomeGiorno() {
        return nomeGiorno;
    }
    public LocalTime getInizioMattina() {
        return inizioMattina;
    }
    public LocalTime getFineMattina() {
        return fineMattina;
    }
    public LocalTime getInizioPomeriggio() {
        return inizioPomeriggio;
    }
    public LocalTime getFinePomeriggio() {
        return finePomeriggio;
    }
    public boolean isChiuso() {
        return chiuso;
    }
}

