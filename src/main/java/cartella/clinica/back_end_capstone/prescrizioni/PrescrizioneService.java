package cartella.clinica.back_end_capstone.prescrizioni;


import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.auth.Role;
import cartella.clinica.back_end_capstone.farmaci.FarmacoRepository;
import cartella.clinica.back_end_capstone.pazienti.Paziente;
import cartella.clinica.back_end_capstone.pazienti.PazienteRepository;
import cartella.clinica.back_end_capstone.pazienti.PazienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated

public class PrescrizioneService {

    @Autowired
    private PrescrizioneRepository prescrizioneRepository;

    @Autowired
    private PazienteRepository pazienteRepository;

    @Autowired
    private FarmacoRepository farmacoRepository;

    @Autowired
    private PazienteService pazienteService;


    private Prescrizione toEntity(PrescrizioneRequest prescrizioneRequest) {
        Prescrizione prescrizione = new Prescrizione();
        prescrizione.setDataOraPrescrizione(prescrizioneRequest.getDataOraPrescrizione());
        prescrizione.setFrequenza(prescrizioneRequest.getFrequenza());
        prescrizione.setDurata(prescrizioneRequest.getDurata());
        prescrizione.setNote(prescrizioneRequest.getNote());
        prescrizione.setPaziente(pazienteRepository.findById(prescrizioneRequest.getPazienteId()).orElseThrow());
        prescrizione.setFarmaco(farmacoRepository.findById(prescrizioneRequest.getFarmacoId()).orElseThrow());
        return prescrizione;
    }

    private PrescrizioneResponse toResponse(Prescrizione prescrizione) {
        return new PrescrizioneResponse(
                prescrizione.getId(),
                prescrizione.getDataOraPrescrizione(),
                prescrizione.getPaziente().getId(),
                prescrizione.getFarmaco().getId()
        );
    }

    public PrescrizioneResponse createPrescrizione(PrescrizioneRequest prescrizioneRequest) {
        Prescrizione prescrizione = toEntity(prescrizioneRequest);
        Prescrizione savedPrescrizione = prescrizioneRepository.save(prescrizione);
        return toResponse(savedPrescrizione);
    }

    public Page<PrescrizioneResponse> findAllPrescrizioni(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return prescrizioneRepository.findAll(pageable).map(this::toResponse);
    }

    public PrescrizioneResponse findPrescrizioneById(Long id) {
        Prescrizione prescrizione = prescrizioneRepository.findById(id).orElseThrow(() -> new RuntimeException("Prescrizione non trovata"));
        return toResponse(prescrizione);
    }

    public PrescrizioneResponse updatePrescrizione(Long id, PrescrizioneRequest prescrizioneRequest, AppUser adminLoggato) {
        Prescrizione prescrizione = prescrizioneRepository.findById(id).orElseThrow(() -> new RuntimeException("Prescrizione non trovata"));
        boolean isAdmin = adminLoggato.getRoles().contains(Role.ROLE_ADMIN);
        if(!isAdmin) {
            throw new RuntimeException("Non sei autorizzato a modificare la prescrizione");
        } else {
            prescrizione.setDataOraPrescrizione(prescrizioneRequest.getDataOraPrescrizione());
            prescrizione.setFrequenza(prescrizioneRequest.getFrequenza());
            prescrizione.setDurata(prescrizioneRequest.getDurata());
            prescrizione.setNote(prescrizioneRequest.getNote());
            prescrizione.setPaziente(pazienteRepository.findById(prescrizioneRequest.getPazienteId()).orElseThrow());
            prescrizione.setFarmaco(farmacoRepository.findById(prescrizioneRequest.getFarmacoId()).orElseThrow());
            Prescrizione savedPrescrizione = prescrizioneRepository.save(prescrizione);
            return toResponse(savedPrescrizione);
        }
    }

    public void deletePrescrizione(Long id, AppUser adminLoggato) {
        Prescrizione prescrizione = prescrizioneRepository.findById(id).orElseThrow(() -> new RuntimeException("Prescrizione non trovata"));
        boolean isAdmin = adminLoggato.getRoles().contains(Role.ROLE_ADMIN);
        if(!isAdmin) {
            throw new RuntimeException("Non sei autorizzato a eliminare la prescrizione");
        } else {
            prescrizioneRepository.delete(prescrizione);
        }
    }

    public Page<PrescrizioneResponse> findPrescrizioniByNomePaziente(String codiceFiscale, Pageable pageable) {
        Optional<Paziente> pazienteOptional = pazienteRepository.findByCodiceFiscale(codiceFiscale);
        Paziente paziente = pazienteOptional.orElseThrow(() -> new RuntimeException("Paziente non trovato con codice fiscale: " + codiceFiscale));
        Page<Prescrizione> prescrizioni = prescrizioneRepository.findByPaziente(paziente, pageable);
        return prescrizioni.map(this::toResponse);
    }

    public Page<PrescrizioneResponse> getPrescrizioniByPazienteCodiceFiscale(String codiceFiscale, Pageable pageable) {
        Optional<Paziente> pazienteOptional = pazienteRepository.findByCodiceFiscale(codiceFiscale);
        Paziente paziente = pazienteOptional.orElseThrow(() -> new RuntimeException("Paziente non trovato con codice fiscale: " + codiceFiscale));
        Page<Prescrizione> prescrizioniPage = prescrizioneRepository.findByPaziente(paziente, pageable);
        return prescrizioniPage.map(this::toResponse);
    }






}

