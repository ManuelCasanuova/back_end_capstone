package cartella.clinica.back_end_capstone.pazienti;

import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.auth.AppUserRepository;
import cartella.clinica.back_end_capstone.auth.Role;
import cartella.clinica.back_end_capstone.enums.GruppoSanguigno;
import cartella.clinica.back_end_capstone.exceptions.BadRequestException;
import cartella.clinica.back_end_capstone.exceptions.NotFoundException;
import cartella.clinica.back_end_capstone.utenti.Utente;
import cartella.clinica.back_end_capstone.utenti.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Set;

@Service
@Validated
public class PazienteService {

    @Autowired
    private PazienteRepository pazienteRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UtenteRepository utenteRepository;

    //Ricerca del paziente tramite ID

    public Paziente findPazienteById(Long id) {
        return pazienteRepository.findById(id).orElseThrow(()-> new NotFoundException("Paziente con id " + id + " non trovato"));
    }


    //RICERCA SINGOLO PAZIENTE PER CODICE FISCALE

    public Paziente findPazienteByCF(String codiceFiscale) {
        return pazienteRepository.findByCodiceFiscale(codiceFiscale).orElseThrow(()-> new NotFoundException("Paziente con codice fiscale " + codiceFiscale + " non trovato"));
    }

    //RICERCA SINGOLO PAZIENTE PER ID E LO ELIMINA

    public void findPazienteByIdAndDelete(Long id) {
        Paziente paziente = findPazienteById(id);
        pazienteRepository.delete(paziente);
    }

    //RICERCA SINGOLO PAZIENTE PER ID E LO AGGIORNA

    public Paziente findPazienteByIdAndUpdate(Long id, PazienteRequest pazienteRequest) {


        if(pazienteRepository.existsByTelefonoCellulare(pazienteRequest.getTelefonoCellulare()))
            throw new BadRequestException("Numero di telefono già in uso");

        if(pazienteRepository.existsByCodiceFiscale(pazienteRequest.getCodiceFiscale()))
            throw new BadRequestException("Codice fiscale già in uso");

        Paziente pazienteUpdate = findPazienteById(id);
        pazienteUpdate.setDataDiNascita(pazienteRequest.getDataDiNascita());
        pazienteUpdate.setGruppoSanguigno(pazienteRequest.getGruppoSanguigno());
        pazienteUpdate.setSesso(pazienteRequest.getSesso());
        pazienteUpdate.setCodiceFiscale(pazienteRequest.getCodiceFiscale());
        pazienteUpdate.setLuogoDiNascita(pazienteRequest.getLuogoDiNascita());
        pazienteUpdate.setIndirizzoResidenza(pazienteRequest.getIndirizzoResidenza());
        pazienteUpdate.setDomicilio(pazienteRequest.getDomicilio());
        pazienteUpdate.setTelefonoCellulare(pazienteRequest.getTelefonoCellulare());
        pazienteUpdate.setTelefonoFisso(pazienteRequest.getTelefonoFisso());

        pazienteUpdate.setEsenzione(pazienteRequest.getEsenzione());
        return pazienteRepository.save(pazienteUpdate);
    }

    public Paziente savePaziente(PazienteRequest pazienteRequest, AppUser appUser) {
        if (pazienteRepository.existsByTelefonoCellulare(pazienteRequest.getTelefonoCellulare()))
            throw new BadRequestException("Numero di telefono già esistente");

        if (pazienteRepository.existsByCodiceFiscale(pazienteRequest.getCodiceFiscale()))
            throw new BadRequestException("Codice fiscale già esistente");


        Utente utente = new Utente();
        utente.setNome(pazienteRequest.getNome());
        utente.setCognome(pazienteRequest.getCognome());
        utente.setEmail(pazienteRequest.getEmail());
        utenteRepository.save(utente);


        Paziente paziente = new Paziente();
        paziente.setDataDiNascita(pazienteRequest.getDataDiNascita());
        paziente.setGruppoSanguigno(pazienteRequest.getGruppoSanguigno());
        paziente.setSesso(pazienteRequest.getSesso());
        paziente.setCodiceFiscale(pazienteRequest.getCodiceFiscale());
        paziente.setLuogoDiNascita(pazienteRequest.getLuogoDiNascita());
        paziente.setIndirizzoResidenza(pazienteRequest.getIndirizzoResidenza());
        paziente.setDomicilio(pazienteRequest.getDomicilio());
        paziente.setTelefonoCellulare(pazienteRequest.getTelefonoCellulare());
        paziente.setTelefonoFisso(pazienteRequest.getTelefonoFisso());
        paziente.setEsenzione(pazienteRequest.getEsenzione());
        paziente.setAppUser(appUser);
        paziente.setUtente(utente);

        return pazienteRepository.save(paziente);
    }

    public Page<PazienteResponse> filterPazienti(PazienteFilter pazienteFilter, Pageable pageable) {
        Specification<Paziente> spec = PazienteSpecification.filterBy(pazienteFilter);
        Page<Paziente> pazienti = pazienteRepository.findAll(spec, pageable);
        return pazienti.map(this::toResponse);

    }

    public PazienteResponse toResponse(Paziente p) {
        PazienteResponse res = new PazienteResponse();
        res.setId(p.getId());
        res.setNome(p.getUtente() != null ? p.getUtente().getNome() : null);
        res.setCognome(p.getUtente() != null ? p.getUtente().getCognome() : null);
        res.setEmail(p.getUtente() != null ? p.getUtente().getEmail() : null);
        res.setDataDiNascita(p.getDataDiNascita());
        res.setCodiceFiscale(p.getCodiceFiscale());
        res.setGruppoSanguigno(p.getGruppoSanguigno());
        res.setLuogoDiNascita(p.getLuogoDiNascita());
        res.setIndirizzoResidenza(p.getIndirizzoResidenza());
        res.setDomicilio(p.getDomicilio());
        res.setTelefonoCellulare(p.getTelefonoCellulare());
        res.setTelefonoFisso(p.getTelefonoFisso());
        res.setSesso(p.getSesso());
        return res;
    }

    public Page<PazienteResponse> findAllPazienti(Pageable pageable) {
        return pazienteRepository.findAll(pageable).map(this::toResponse);
    }

}
