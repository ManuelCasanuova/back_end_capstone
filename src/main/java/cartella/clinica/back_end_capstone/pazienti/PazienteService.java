package cartella.clinica.back_end_capstone.pazienti;

import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.auth.AppUserRepository;
import cartella.clinica.back_end_capstone.auth.AppUserService;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    private AppUserService appUserService;

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

        // Verifiche univocità (escludendo il paziente stesso)
        Paziente pazienteEsistente = findPazienteById(id);

        if(pazienteRepository.existsByTelefonoCellulare(pazienteRequest.getTelefonoCellulare())
                && !pazienteEsistente.getTelefonoCellulare().equals(pazienteRequest.getTelefonoCellulare())) {
            throw new BadRequestException("Numero di telefono già in uso");
        }
        if(pazienteRepository.existsByCodiceFiscale(pazienteRequest.getCodiceFiscale())
                && !pazienteEsistente.getCodiceFiscale().equals(pazienteRequest.getCodiceFiscale())) {
            throw new BadRequestException("Codice fiscale già in uso");
        }

        Paziente pazienteUpdate = pazienteEsistente;

        // Aggiorna i campi Utente
        Utente utente = pazienteUpdate.getUtente();
        if (utente == null) {
            utente = new Utente();
            pazienteUpdate.setUtente(utente);
        }
        utente.setNome(pazienteRequest.getNome());
        utente.setCognome(pazienteRequest.getCognome());
        utente.setEmail(pazienteRequest.getEmail());
        utenteRepository.save(utente);



        // Aggiorna i campi Paziente
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

    public Page<PazienteResponse> filterPazienti(PazienteFilter pazienteFilter, Pageable pageable) {
        Specification<Paziente> spec = PazienteSpecification.filterBy(pazienteFilter);
        Page<Paziente> pazienti = pazienteRepository.findAll(spec, pageable);
        return pazienti.map(this::toResponse);

    }

    public PazienteResponse toResponse(Paziente p) {
        return PazienteResponse.from(p);
    }

    public Page<PazienteResponse> findAllPazienti(Pageable pageable) {
        return pazienteRepository.findAll(pageable).map(this::toResponse);
    }

    public List<PazienteResponse> findPazientiByFilter(PazienteFilter filter) {
        Specification<Paziente> spec = PazienteSpecification.filterBy(filter);
        List<Paziente> lista = pazienteRepository.findAll(spec);
        return lista.stream()
                .map(PazienteResponse::from)
                .collect(Collectors.toList());
    }


}
