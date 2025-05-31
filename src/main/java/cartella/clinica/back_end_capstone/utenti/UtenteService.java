package cartella.clinica.back_end_capstone.utenti;

import cartella.clinica.back_end_capstone.GiorniApertura.GiornoAperturaResponse;
import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.common.cloudinary.CloudinaryService;
import cartella.clinica.back_end_capstone.medici.Medico;
import cartella.clinica.back_end_capstone.pazienti.Paziente;
import cartella.clinica.back_end_capstone.pazienti.PazienteRepository;
import cartella.clinica.back_end_capstone.studi.Studio;
import cartella.clinica.back_end_capstone.studi.StudioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PazienteRepository pazienteRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    public Page<Utente> getAllUtenti(Pageable pageable) {
        return utenteRepository.findAll(pageable);
    }

    public Utente saveUtente(Utente utente) {
        return utenteRepository.save(utente);
    }

    public Utente getByUsername(String username) {
        Utente utente = utenteRepository.findByAppUserUsername(username);
        if (utente == null) {
            throw new RuntimeException("Utente non trovato");
        }
        return utente;
    }

    public Utente aggiornaAvatar(Long utenteId, MultipartFile file) {
        Utente utente = utenteRepository.findById(utenteId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        if (file != null && !file.isEmpty()) {
            String urlAvatar = cloudinaryService.uploadImage(file);
            utente.setAvatar(urlAvatar);
        }

        return utenteRepository.save(utente);
    }

    public UtenteResponse getUtenteResponse(AppUser appUser) {
        Utente utente = utenteRepository.findByAppUser(appUser)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        UtenteResponse response = new UtenteResponse();
        response.setId(utente.getId());
        response.setUsername(appUser.getUsername());
        response.setRoles(appUser.getRoles());
        response.setNome(utente.getNome());
        response.setCognome(utente.getCognome());
        response.setEmail(utente.getEmail());
        response.setAvatar(utente.getAvatar());
        response.setTelefonoCellulare(utente.getTelefonoCellulare());
        response.setTelefonoFisso(utente.getTelefonoFisso());
        response.setPasswordModificata(appUser.isPasswordModificata());

        pazienteRepository.findByAppUser(appUser).ifPresent(p -> {
            response.setPazienteId(p.getId());
            response.setDataDiNascita(p.getDataDiNascita());
            response.setCodiceFiscale(p.getCodiceFiscale());
            response.setSesso(p.getSesso() != null ? p.getSesso().name() : null);
            response.setGruppoSanguigno(p.getGruppoSanguigno() != null ? p.getGruppoSanguigno().name() : null);
            response.setIndirizzoResidenza(p.getIndirizzoResidenza());
            response.setDomicilio(p.getDomicilio());
            response.setEsenzione(p.getEsenzione());

            Medico medico = p.getMedico();
            if (medico != null) {
                response.setMedicoId(medico.getId());
                Studio studio = medico.getStudio();
                if (studio != null) {
                    StudioResponse studioResp = new StudioResponse();
                    studioResp.setNome(studio.getNome());
                    studioResp.setIndirizzo(studio.getIndirizzo());
                    studioResp.setTelefonoStudio(medico.getUtente().getTelefonoFisso());
                    studioResp.setNomeMedico(medico.getUtente().getNome());
                    studioResp.setCognomeMedico(medico.getUtente().getCognome());
                    studioResp.setEmailMedico(medico.getUtente().getEmail());
                    studioResp.setTelefonoCellulareMedico(medico.getUtente().getTelefonoCellulare());
                    studioResp.setSpecializzazioneMedico(medico.getSpecializzazione());
                    studioResp.setGiorniApertura(
                            studio.getGiorniApertura().stream()
                                    .map(g -> new GiornoAperturaResponse(
                                            g.getGiorno().name(),
                                            g.getGiorno().getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.ITALY),
                                            g.getInizioMattina(),
                                            g.getFineMattina(),
                                            g.getInizioPomeriggio(),
                                            g.getFinePomeriggio(),
                                            g.isChiuso()
                                    ))
                                    .toList()
                    );
                    response.setStudio(studioResp);
                }
            }
        });

        return response;
    }
}





