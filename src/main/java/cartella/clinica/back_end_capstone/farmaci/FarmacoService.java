package cartella.clinica.back_end_capstone.farmaci;


import cartella.clinica.back_end_capstone.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class FarmacoService {

    @Autowired
    public FarmacoRepository farmacoRepository;

    public FarmacoResponse createFarmaco(FarmacoRequest request) {
        Farmaco farmaco = new Farmaco();
        farmaco.setNomeCommerciale(request.getNomeCommerciale());
        farmaco.setCodiceATC(request.getCodiceATC());
        farmaco.setFormaFarmaceutica(request.getFormaFarmaceutica());
        farmaco.setDosaggio(request.getDosaggio());
        farmaco.setNote(request.getNote());
        Farmaco savedFarmaco = farmacoRepository.save(farmaco);
        return toResponse(savedFarmaco);
    }

    public FarmacoResponse getFarmacoById(Long id) {
        Farmaco farmaco = farmacoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Farmaco non trovato con id: " + id));
        return toResponse(farmaco);
    }

    public Page<FarmacoResponse> findAll(Pageable pageable) {
        Page<Farmaco> farmaciPage = farmacoRepository.findAll(pageable);
        return farmaciPage.map(this::toResponse);
    }

    public void deleteFarmaco(Long id) {
        if (!farmacoRepository.existsById(id)) {
            throw new NotFoundException("Farmaco non trovato con id: " + id);
        }
        farmacoRepository.deleteById(id);
    }

    public FarmacoResponse updateFarmaco(Long id, FarmacoRequest request) {
        Farmaco farmaco = farmacoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Farmaco non trovato con id: " + id));
        farmaco.setNomeCommerciale(request.getNomeCommerciale());
        farmaco.setCodiceATC(request.getCodiceATC());
        farmaco.setFormaFarmaceutica(request.getFormaFarmaceutica());
        farmaco.setDosaggio(request.getDosaggio());
        farmaco.setNote(request.getNote());
        Farmaco updatedFarmaco = farmacoRepository.save(farmaco);
        return toResponse(updatedFarmaco);
    }

    public FarmacoResponse toResponse(Farmaco farmaco) {
        FarmacoResponse response = new FarmacoResponse();
        response.setId(farmaco.getId());
        response.setNomeCommerciale(farmaco.getNomeCommerciale());
        response.setCodiceATC(farmaco.getCodiceATC());
        response.setFormaFarmaceutica(farmaco.getFormaFarmaceutica());
        response.setDosaggio(farmaco.getDosaggio());
        response.setNote(farmaco.getNote());
        return response;
    }

    public Page<FarmacoResponse> filterFarmaci (FarmacoFilter farmacoFilter, Pageable pageable){
        Specification<Farmaco> spec = FarmacoSpecidication.filterBy(farmacoFilter);
        Page<Farmaco> page = farmacoRepository.findAll(spec, pageable);
        return page.map(this::toResponse);
    }
}
