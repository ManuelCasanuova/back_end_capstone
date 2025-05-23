package cartella.clinica.back_end_capstone.medici;

import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.auth.AppUserService;
import cartella.clinica.back_end_capstone.auth.Role;
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

    public Medico saveMedico(MedicoRequest request) {

        AppUser appUser = appUserService.registerUser(
                request.getEmail(),
                Set.of(Role.ROLE_ADMIN)
        );


        Utente utente = new Utente();
        utente.setEmail(request.getEmail());
        utente.setNome(request.getNome());
        utente.setCognome(request.getCognome());
        utente.setAppUser(appUser);
        utenteRepository.save(utente);


        Medico medico = new Medico();
        medico.setSpecializzazione(request.getSpecializzazione());
        medico.setNomeStudioMedico(request.getNomeStudioMedico());
        medico.setIndirizzoStudio(request.getIndirizzoStudio());
        medico.setTelefonoStudio(request.getTelefonoStudio());
        medico.setUtente(utente);
        medico.setAppUser(appUser);

        return medicoRepository.save(medico);
    }
}
