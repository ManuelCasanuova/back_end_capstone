package cartella.clinica.back_end_capstone.farmaci;

import cartella.clinica.back_end_capstone.exceptions.NotFoundException;
import cartella.clinica.back_end_capstone.pazienti.Paziente;
import cartella.clinica.back_end_capstone.pazienti.PazienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FarmacoService {

    @Autowired
    private FarmacoRepository farmacoRepository;

    @Autowired
    private PazienteRepository pazienteRepository;

    public FarmacoResponse createFarmaco(FarmacoRequest request) {
        if (request.getPazienteId() == null) {
            throw new IllegalArgumentException("Il campo pazienteId è obbligatorio");
        }

        Paziente paziente = pazienteRepository.findById(request.getPazienteId())
                .orElseThrow(() -> new NotFoundException("Paziente non trovato con id: " + request.getPazienteId()));

        Farmaco farmaco = new Farmaco();
        farmaco.setNomeCommerciale(request.getNomeCommerciale());
        farmaco.setCodiceATC(request.getCodiceATC());
        farmaco.setFormaFarmaceutica(request.getFormaFarmaceutica());
        farmaco.setDosaggio(request.getDosaggio());
        farmaco.setNote(request.getNote());
        farmaco.setDataInserimento(LocalDate.now());
        farmaco.setPaziente(paziente);

        Farmaco saved = farmacoRepository.save(farmaco);
        return toResponse(saved);
    }

    public List<FarmacoResponse> getFarmaciByPaziente(Long pazienteId) {
        List<Farmaco> farmaci = farmacoRepository.findByPazienteId(pazienteId);
        return farmaci.stream().map(this::toResponse).toList();
    }

    public FarmacoResponse getFarmacoById(Long id) {
        Farmaco farmaco = farmacoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Farmaco non trovato con id: " + id));
        return toResponse(farmaco);
    }

    public FarmacoResponse updateFarmaco(Long id, FarmacoRequest request) {
        Farmaco farmaco = farmacoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Farmaco non trovato con id: " + id));

        farmaco.setNomeCommerciale(request.getNomeCommerciale());
        farmaco.setCodiceATC(request.getCodiceATC());
        farmaco.setFormaFarmaceutica(request.getFormaFarmaceutica());
        farmaco.setDosaggio(request.getDosaggio());
        farmaco.setNote(request.getNote());

        if (request.getPazienteId() != null) {
            Paziente paziente = pazienteRepository.findById(request.getPazienteId())
                    .orElseThrow(() -> new NotFoundException("Paziente non trovato con id: " + request.getPazienteId()));
            farmaco.setPaziente(paziente);
        }

        Farmaco updated = farmacoRepository.save(farmaco);
        return toResponse(updated);
    }

    public void deleteFarmaco(Long id) {
        if (!farmacoRepository.existsById(id)) {
            throw new NotFoundException("Farmaco non trovato con id: " + id);
        }
        farmacoRepository.deleteById(id);
    }

    private FarmacoResponse toResponse(Farmaco farmaco) {
        FarmacoResponse response = new FarmacoResponse();
        response.setId(farmaco.getId());
        response.setNomeCommerciale(farmaco.getNomeCommerciale());
        response.setCodiceATC(farmaco.getCodiceATC());
        response.setFormaFarmaceutica(farmaco.getFormaFarmaceutica());
        response.setDosaggio(farmaco.getDosaggio());
        response.setNote(farmaco.getNote());
        response.setDataInserimento(farmaco.getDataInserimento());

        if (farmaco.getPaziente() != null) {
            response.setPazienteId(farmaco.getPaziente().getId());
        }
        return response;
    }
}

