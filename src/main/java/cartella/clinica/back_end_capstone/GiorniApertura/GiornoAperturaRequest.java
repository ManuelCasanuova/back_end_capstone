package cartella.clinica.back_end_capstone.GiorniApertura;

import java.time.LocalTime;

public class GiornoAperturaRequest {
    private String giorno;
    private LocalTime inizioMattina;
    private LocalTime fineMattina;
    private LocalTime inizioPomeriggio;
    private LocalTime finePomeriggio;
    private boolean chiuso;

    public GiornoAperturaRequest() {}

    public String getGiorno() {
        return giorno;
    }
    public void setGiorno(String giorno) {
        this.giorno = giorno;
    }

    public LocalTime getInizioMattina() {
        return inizioMattina;
    }
    public void setInizioMattina(LocalTime inizioMattina) {
        this.inizioMattina = inizioMattina;
    }

    public LocalTime getFineMattina() {
        return fineMattina;
    }
    public void setFineMattina(LocalTime fineMattina) {
        this.fineMattina = fineMattina;
    }

    public LocalTime getInizioPomeriggio() {
        return inizioPomeriggio;
    }
    public void setInizioPomeriggio(LocalTime inizioPomeriggio) {
        this.inizioPomeriggio = inizioPomeriggio;
    }

    public LocalTime getFinePomeriggio() {
        return finePomeriggio;
    }
    public void setFinePomeriggio(LocalTime finePomeriggio) {
        this.finePomeriggio = finePomeriggio;
    }

    public boolean isChiuso() {
        return chiuso;
    }
    public void setChiuso(boolean chiuso) {
        this.chiuso = chiuso;
    }
}

