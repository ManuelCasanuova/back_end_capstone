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

                String avatar = p.getUtente().getAvatar();
                if (avatar == null || avatar.isEmpty()) {
                    String inizialeNome = p.getUtente().getNome() != null && !p.getUtente().getNome().isEmpty()
                            ? p.getUtente().getNome().substring(0, 1).toUpperCase()
                            : "";
                    String inizialeCognome = p.getUtente().getCognome() != null && !p.getUtente().getCognome().isEmpty()
                            ? p.getUtente().getCognome().substring(0, 1).toUpperCase()
                            : "";
                    String initials = inizialeCognome + "+" + inizialeNome;
                    res.avatar = "https://ui-avatars.com/api/?name=" + initials + "&background=0D8ABC&color=fff&rounded=true&v=" + System.currentTimeMillis();
                } else {
                    res.avatar = avatar;
                }
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