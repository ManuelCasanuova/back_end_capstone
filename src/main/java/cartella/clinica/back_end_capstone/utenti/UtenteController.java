package cartella.clinica.back_end_capstone.utenti;


import cartella.clinica.back_end_capstone.GiorniApertura.GiornoAperturaRepository;
import cartella.clinica.back_end_capstone.GiorniApertura.GiornoAperturaResponse;
import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.auth.AppUserService;
import cartella.clinica.back_end_capstone.auth.Role;
import cartella.clinica.back_end_capstone.medici.Medico;
import cartella.clinica.back_end_capstone.medici.MedicoRepository;
import cartella.clinica.back_end_capstone.pazienti.Paziente;
import cartella.clinica.back_end_capstone.pazienti.PazienteRepository;
import cartella.clinica.back_end_capstone.studi.Studio;
import cartella.clinica.back_end_capstone.studi.StudioResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/utente")
public class UtenteController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private PazienteRepository pazienteRepo;

    @Autowired
    private MedicoRepository medicoRepo;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private GiornoAperturaRepository giornoAperturaRepository;

    @GetMapping("/all")
    public Page<Utente> getUtenti(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return utenteService.getAllUtenti(pageable);
    }

    @GetMapping("/me")
    public ResponseEntity<UtenteResponse> getMe() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        AppUser user = appUserService.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));

        UtenteResponse response = new UtenteResponse();
        response.setUsername(user.getUsername());
        response.setRoles(user.getRoles());

        Utente utente = utenteRepository.findByAppUser(user).orElse(null);
        if (utente != null) {
            response.setNome(utente.getNome());
            response.setCognome(utente.getCognome());
            response.setEmail(utente.getEmail());
            response.setAvatar(utente.getAvatar());
            response.setTelefonoCellulare(utente.getTelefonoCellulare());
            response.setTelefonoFisso(utente.getTelefonoFisso());
        } else {
            response.setNome("Sconosciuto");
            response.setCognome("-");
            response.setEmail("-");
        }

        if (user.getRoles().contains(Role.ROLE_PAZIENTE) && user.getPaziente() != null) {
            Paziente paziente = user.getPaziente();
            response.setId(paziente.getId());
            response.setPazienteId(paziente.getId());
            response.setDataDiNascita(paziente.getDataDiNascita());
            response.setCodiceFiscale(paziente.getCodiceFiscale());
            response.setSesso(paziente.getSesso() != null ? paziente.getSesso().name() : null);
            response.setGruppoSanguigno(paziente.getGruppoSanguigno() != null ? paziente.getGruppoSanguigno().name() : null);
            response.setIndirizzoResidenza(paziente.getIndirizzoResidenza());
            response.setDomicilio(paziente.getDomicilio());
            response.setEsenzione(paziente.getEsenzione());
        }

        if (user.getRoles().contains(Role.ROLE_ADMIN) && user.getMedico() != null) {
            Medico medico = user.getMedico();
            response.setId(medico.getId());
            response.setMedicoId(medico.getId());

            Studio medicoStudio = medico.getStudio();
            if (medicoStudio != null) {
                StudioResponse studioResponse = new StudioResponse();
                studioResponse.setNome(medicoStudio.getNome());
                studioResponse.setIndirizzo(medicoStudio.getIndirizzo());
                studioResponse.setTelefonoStudio(medico.getUtente().getTelefonoFisso());
                studioResponse.setNomeMedico(medico.getUtente().getNome());
                studioResponse.setCognomeMedico(medico.getUtente().getCognome());
                studioResponse.setEmailMedico(medico.getUtente().getEmail());
                studioResponse.setTelefonoCellulareMedico(medico.getUtente().getTelefonoCellulare());
                studioResponse.setSpecializzazioneMedico(medico.getSpecializzazione());

                List<GiornoAperturaResponse> giorni = giornoAperturaRepository.findByStudio_Medico(medico).stream()
                        .map(g -> new GiornoAperturaResponse(
                                g.getGiorno().name(),
                                g.getGiorno().getDisplayName(TextStyle.FULL, Locale.ITALY),
                                g.getInizioMattina(),
                                g.getFineMattina(),
                                g.getInizioPomeriggio(),
                                g.getFinePomeriggio(),
                                g.isChiuso()
                        ))
                        .toList();

                studioResponse.setGiorniApertura(giorni);
                response.setStudio(studioResponse);
            }
        }

        return ResponseEntity.ok(response);
    }
}










