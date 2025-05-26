package cartella.clinica.back_end_capstone.medici;

import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.auth.AppUserService;
import cartella.clinica.back_end_capstone.auth.Role;
import cartella.clinica.back_end_capstone.studi.Studio;
import cartella.clinica.back_end_capstone.studi.StudioRepository;
import cartella.clinica.back_end_capstone.GiorniApertura.GiornoApertura;
import cartella.clinica.back_end_capstone.GiorniApertura.GiornoAperturaRepository;
import cartella.clinica.back_end_capstone.utenti.Utente;
import cartella.clinica.back_end_capstone.utenti.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Service
public class MedicoService {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private StudioRepository studioRepository;

    @Autowired
    private GiornoAperturaRepository giornoAperturaRepository;

    public Medico saveMedico(MedicoRequest request) {

        AppUser appUser = appUserService.registerUser(
                request.getEmail(),
                Set.of(Role.ROLE_ADMIN)
        );

        Utente utente = new Utente();
        utente.setEmail(request.getEmail());
        utente.setNome(request.getNome());
        utente.setCognome(request.getCognome());
        utente.setAvatar(request.getAvatar());
        utente.setAppUser(appUser);
        utenteRepository.save(utente);

        Medico medico = new Medico();
        medico.setSpecializzazione(request.getSpecializzazione());
        medico.setUtente(utente);
        medico.setAppUser(appUser);
        medico = medicoRepository.save(medico);

        Studio studio = new Studio();
        studio.setNome(request.getNomeStudio());
        studio.setIndirizzo(request.getIndirizzoStudio());
        studio.setTelefono(request.getTelefonoStudio());
        studio.setMedico(medico);
        studio = studioRepository.save(studio); // salva per assegnare ID

        // Inizializzazione default dei GiorniApertura (LUN–VEN aperto, SAB–DOM chiuso)
        for (DayOfWeek giorno : DayOfWeek.values()) {
            GiornoApertura g = new GiornoApertura();
            g.setStudio(studio);
            g.setGiorno(giorno);
            g.setInizioMattina(LocalTime.of(8, 0));
            g.setFineMattina(LocalTime.of(13, 0));
            g.setInizioPomeriggio(LocalTime.of(14, 0));
            g.setFinePomeriggio(LocalTime.of(19, 0));
            g.setChiuso(giorno == DayOfWeek.SATURDAY || giorno == DayOfWeek.SUNDAY);
            giornoAperturaRepository.save(g);
        }

        return medico;
    }
}

