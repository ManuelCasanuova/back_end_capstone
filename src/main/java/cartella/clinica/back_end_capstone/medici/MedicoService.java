package cartella.clinica.back_end_capstone.medici;

import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.auth.AppUserService;
import cartella.clinica.back_end_capstone.auth.Role;
import cartella.clinica.back_end_capstone.studi.Studio;
import cartella.clinica.back_end_capstone.studi.StudioRepository;
import cartella.clinica.back_end_capstone.utenti.Utente;
import cartella.clinica.back_end_capstone.utenti.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        studio.setInizioMattina(java.time.LocalTime.of(8, 0));
        studio.setFineMattina(java.time.LocalTime.of(13, 0));
        studio.setInizioPomeriggio(java.time.LocalTime.of(14, 0));
        studio.setFinePomeriggio(java.time.LocalTime.of(19, 0));
        studio.setGiornoDispariMattina(true);
        studio.setMedico(medico);

        studioRepository.save(studio);

        medico.setStudio(studio);
        return medico;
    }
}
