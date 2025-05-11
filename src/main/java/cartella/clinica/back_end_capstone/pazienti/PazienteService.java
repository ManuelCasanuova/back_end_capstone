package cartella.clinica.back_end_capstone.pazienti;

import cartella.clinica.back_end_capstone.enums.GruppoSanguigno;
import cartella.clinica.back_end_capstone.exceptions.BadRequestException;
import cartella.clinica.back_end_capstone.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Service
@Validated
public class PazienteService {

    @Autowired
    private PazienteRepository pazienteRepository;

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

        if(pazienteRepository.existsByEmail(pazienteRequest.getEmail()))
            throw new BadRequestException("Email già in uso");

        if(pazienteRepository.existsByTelefonoCellulare(pazienteRequest.getTelefonoCellulare()))
            throw new BadRequestException("Numero di telefono già in uso");

        if(pazienteRepository.existsByCodiceFiscale(pazienteRequest.getCodiceFiscale()))
            throw new BadRequestException("Codice fiscale già in uso");

        Paziente pazienteUpdate = findPazienteById(id);
        pazienteUpdate.setNome(pazienteRequest.getNome());
        pazienteUpdate.setCognome(pazienteRequest.getCognome());
        pazienteUpdate.setDataDiNascita(LocalDate.parse(pazienteRequest.getDataDiNascita()));
        pazienteUpdate.setGruppoSanguigno(GruppoSanguigno.valueOf(pazienteRequest.getGruppoSanguigno()));
        pazienteUpdate.setSesso(pazienteRequest.getSesso());
        pazienteUpdate.setCodiceFiscale(pazienteRequest.getCodiceFiscale());
        pazienteUpdate.setLuogoDiNascita(pazienteRequest.getLuogoDiNascita());
        pazienteUpdate.setIndirizzoResidenza(pazienteRequest.getIndirizzoResidenza());
        pazienteUpdate.setDomicilio(pazienteRequest.getDomicilio());
        pazienteUpdate.setTelefonoCellulare(pazienteRequest.getTelefonoCellulare());
        pazienteUpdate.setTelefonoFisso(pazienteRequest.getTelefonoFisso());
        pazienteUpdate.setEmail(pazienteRequest.getEmail());
        pazienteUpdate.setPassword(pazienteRequest.getCodiceFiscale());
        pazienteUpdate.setAvatar(pazienteRequest.getCodiceFiscale());
        pazienteUpdate.setEsenzione(pazienteRequest.getCodiceFiscale());
        return pazienteRepository.save(pazienteUpdate);
    }

    public Paziente savePaziente(PazienteRequest pazienteRequest) {

        if(pazienteRepository.existsByEmail(pazienteRequest.getEmail()))
            throw new BadRequestException("Email già esistente");

        if(pazienteRepository.existsByTelefonoCellulare(pazienteRequest.getTelefonoCellulare()))
            throw new BadRequestException("Numero di telefono già esistente");

        if(pazienteRepository.existsByCodiceFiscale(pazienteRequest.getCodiceFiscale()))
            throw new BadRequestException("Codice fiscale già esistente");

        Paziente paziente = new Paziente();
        paziente.setNome(pazienteRequest.getNome());
        paziente.setCognome(pazienteRequest.getCognome());
        paziente.setDataDiNascita(LocalDate.parse(pazienteRequest.getDataDiNascita()));
        paziente.setGruppoSanguigno(GruppoSanguigno.valueOf(pazienteRequest.getGruppoSanguigno()));
        paziente.setSesso(pazienteRequest.getSesso());
        paziente.setCodiceFiscale(pazienteRequest.getCodiceFiscale());
        paziente.setLuogoDiNascita(pazienteRequest.getLuogoDiNascita());
        paziente.setIndirizzoResidenza(pazienteRequest.getIndirizzoResidenza());
        paziente.setDomicilio(pazienteRequest.getDomicilio());
        paziente.setTelefonoCellulare(pazienteRequest.getTelefonoCellulare());
        paziente.setTelefonoFisso(pazienteRequest.getTelefonoFisso());
        paziente.setEmail(pazienteRequest.getEmail());
        paziente.setPassword(pazienteRequest.getCodiceFiscale());
        paziente.setAvatar(pazienteRequest.getCodiceFiscale());
        paziente.setEsenzione(pazienteRequest.getCodiceFiscale());
        return pazienteRepository.save(paziente);
    }

    public Page<PazienteResponse> filterPazienti(PazienteFilter pazienteFilter, Pageable pageable) {
        Specification<Paziente> spec = PazienteSpecification.filterBy(pazienteFilter);
        Page<Paziente> pazienti = pazienteRepository.findAll(spec, pageable);
        return pazienti.map(this::toResponse);

    }

    public PazienteResponse toResponse(Paziente paziente) {
        PazienteResponse pazienteResponse = new PazienteResponse();

        pazienteResponse.setNome(paziente.getNome());
        pazienteResponse.setCognome(paziente.getCognome());
        pazienteResponse.setDataDiNascita(paziente.getDataDiNascita().toString());
        pazienteResponse.setGruppoSanguigno(paziente.getGruppoSanguigno().toString());

        pazienteResponse.setCodiceFiscale(paziente.getCodiceFiscale());
        pazienteResponse.setLuogoDiNascita(paziente.getLuogoDiNascita());
        pazienteResponse.setIndirizzoResidenza(paziente.getIndirizzoResidenza());
        pazienteResponse.setDomicilio(paziente.getDomicilio());
        pazienteResponse.setTelefonoCellulare(paziente.getTelefonoCellulare());
        pazienteResponse.setTelefonoFisso(paziente.getTelefonoFisso());
        pazienteResponse.setEmail(paziente.getEmail());

        return pazienteResponse;
    }

    public Page<PazienteResponse> findAllPazienti(Pageable pageable) {
        return pazienteRepository.findAll(pageable).map(this::toResponse);
    }

}
