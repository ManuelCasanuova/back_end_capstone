package cartella.clinica.back_end_capstone.medici;

import lombok.Data;

@Data
public class MedicoRequest {
    private String email;
    private String nome;
    private String cognome;
    private String specializzazione;
    private String avatar;
    private String nomeStudio;
    private String indirizzoStudio;
    private String telefonoStudio;
    private String telefonoCellulare;
}