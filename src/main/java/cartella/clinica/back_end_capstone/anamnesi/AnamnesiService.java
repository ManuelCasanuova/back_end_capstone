package cartella.clinica.back_end_capstone.anamnesi;


import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.auth.Role;
import cartella.clinica.back_end_capstone.exceptions.NotFoundException;
import cartella.clinica.back_end_capstone.pazienti.Paziente;
import cartella.clinica.back_end_capstone.pazienti.PazienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnamnesiService {

    @Autowired
    private AnamnesiRepository anamnesiRepository;

    @Autowired
    private PazienteRepository pazienteRepository;


        public AnamnesiResponse createAnamnesi(AnamnesiRequest request) {
            Paziente paziente = pazienteRepository.findById(request.getPazienteId())
                    .orElseThrow(() -> new NotFoundException("Paziente non trovato con id: " + request.getPazienteId()));

            Anamnesi anamnesi = new Anamnesi();
            anamnesi.setPaziente(paziente);
            anamnesi.setDataAnamnesi(request.getDataAnamnesi());
            anamnesi.setDescrizioneAnamnesi(request.getDescrizioneAnamnesi());
            anamnesi.setFumatore(request.getFumatore());
            anamnesi.setDataInizioFumo(request.getDataInizioFumo());
            anamnesi.setUsoDiAlcol(request.getUsoDiAlcol());
            anamnesi.setDataUltimaAssunzioneAlcol(request.getDataUltimaAssunzioneAlcol());
            anamnesi.setUsoDiDroga(request.getUsoDiDroga());
            anamnesi.setDataUltimaAssunzioneDroga(request.getDataUltimaAssunzioneDroga());

            Anamnesi savedAnamnesi = anamnesiRepository.save(anamnesi);
            return toResponse(savedAnamnesi);
        }

        public AnamnesiResponse findAnamnesiById(Long id) {
            Anamnesi anamnesi = anamnesiRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Anamnesi non trovata con id: " + id));
            return toResponse(anamnesi);
        }

        public Page<AnamnesiResponse> findAll(int page, int size, String sortBy) {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            return anamnesiRepository.findAll(pageable).map(this::toResponse);
        }

        public List<AnamnesiResponse> findAnamnesiByPazienteId(Long pazienteId) {
            return anamnesiRepository.findByPazienteId(pazienteId)
                    .stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        }

    public List<AnamnesiResponse> findAnamnesiByPazienteNomeCognome(String nome, String cognome) {
        List<Anamnesi> anamnesiList = anamnesiRepository.findByPazienteNomeCognome(nome, cognome);
        return anamnesiList.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

        private AnamnesiResponse toResponse(Anamnesi anamnesi) {
            AnamnesiResponse response = new AnamnesiResponse();
            response.setId(anamnesi.getId());
            response.setDataInserimentoAnamnesi(anamnesi.getDataInserimentoAnamnesi());
            response.setDataAnamnesi(anamnesi.getDataAnamnesi());
            response.setDescrizioneAnamnesi(anamnesi.getDescrizioneAnamnesi());
            response.setFumatore(anamnesi.getFumatore());
            response.setPazienteId(anamnesi.getPaziente().getPazienteId()); // Ottieni l'ID dal paziente
            return response;
        }

    public Page<AnamnesiResponse> filterAnamnesi (AnamnesiFilter anamnesiFilter, Pageable pageable) {
        Specification<Anamnesi> spec = AnamnesiSpecification.filterBy(anamnesiFilter);
        Page<Anamnesi> page = anamnesiRepository.findAll(spec, pageable);
        return page.map(this::toResponse);
    }

    public AnamnesiResponse updateAnamnesi(Long id, AnamnesiRequest anamnesiRequest, AppUser adminLoggato) {
        Anamnesi anamnesi = anamnesiRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Anamnesi non trovata con id: " + id));

        boolean isAdmin = adminLoggato.getRoles().contains(Role.ROLE_ADMIN);
        if(!isAdmin) {
            throw new RuntimeException("Non sei autorizzato a modificare l'anamnesi");
        } else {
            anamnesi.setDataAnamnesi(anamnesiRequest.getDataAnamnesi());
            anamnesi.setDescrizioneAnamnesi(anamnesiRequest.getDescrizioneAnamnesi());
            anamnesi.setFumatore(anamnesiRequest.getFumatore());
            anamnesi.setDataInizioFumo(anamnesiRequest.getDataInizioFumo());
            anamnesi.setUsoDiAlcol(anamnesiRequest.getUsoDiAlcol());
            anamnesi.setDataUltimaAssunzioneAlcol(anamnesiRequest.getDataUltimaAssunzioneAlcol());
            anamnesi.setUsoDiDroga(anamnesiRequest.getUsoDiDroga());
            anamnesi.setDataUltimaAssunzioneDroga(anamnesiRequest.getDataUltimaAssunzioneDroga());

            Anamnesi updatedAnamnesi = anamnesiRepository.save(anamnesi);
            return toResponse(updatedAnamnesi);
        }
    }

    public void deleteAnamnesi(Long id, AppUser adminLoggato) {
        Anamnesi anamnesi = anamnesiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anamnesi non trovata"));

        boolean isAdmin = adminLoggato.getRoles().contains(Role.ROLE_ADMIN);
        if(!isAdmin) {
            throw new RuntimeException("Non sei autorizzato a eliminare l'anamnesi");
        } else {
            anamnesiRepository.delete(anamnesi);
        }
    }
}