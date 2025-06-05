package cartella.clinica.back_end_capstone.diagnosi;




import cartella.clinica.back_end_capstone.pazienti.PazienteRepository;
import cartella.clinica.back_end_capstone.pazienti.PazienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;



import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class DiagnosiService {

    @Autowired
    private DiagnosiRepository diagnosiRepository;

    @Autowired
    private PazienteRepository pazienteRepository;

    @Autowired
    private PazienteService pazienteService;

    private Diagnosi toEntity(DiagnosiRequest diagnosiRequest) {
        Diagnosi diagnosi = new Diagnosi();
        diagnosi.setDataDiagnosi(diagnosiRequest.getDataDiagnosi());
        diagnosi.setCodiceCIM10(diagnosiRequest.getCodiceCIM10());
        diagnosi.setTrattamentoRaccomandato(diagnosiRequest.getTrattamentoRaccomandato());
        diagnosi.setDescrizioneDiagnosi(diagnosiRequest.getDescrizioneDiagnosi());
        diagnosi.setPaziente(pazienteRepository.findById(diagnosiRequest.getPazienteId())
                .orElseThrow(() -> new RuntimeException("Paziente non trovato")));
        return diagnosi;
    }

    private DiagnosiResponse toResponse(Diagnosi diagnosi) {
        return new DiagnosiResponse(
                diagnosi.getId(),
                diagnosi.getCodiceCIM10(),
                diagnosi.getDataInserimentoDiagnosi(),
                diagnosi.getTrattamentoRaccomandato(),
                diagnosi.getDataDiagnosi(),
                diagnosi.getDescrizioneDiagnosi(),
                diagnosi.getPaziente().getId()
        );
    }

    public DiagnosiResponse createDiagnosi(DiagnosiRequest diagnosiRequest) {
        Diagnosi diagnosi = toEntity(diagnosiRequest);
        diagnosi = diagnosiRepository.save(diagnosi);
        return toResponse(diagnosi);
    }

    public List<DiagnosiResponse> findDiagnosiByPazienteId(Long pazienteId) {
        List<Diagnosi> lista = diagnosiRepository.findByPazienteId(pazienteId);
        return lista.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public DiagnosiResponse findById(Long id) {
        Diagnosi diagnosi = diagnosiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diagnosi non trovata"));
        return toResponse(diagnosi);
    }

    public DiagnosiResponse updateDiagnosi(Long id, DiagnosiRequest diagnosiRequest) {
        Diagnosi diagnosi = diagnosiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diagnosi non trovata"));

        diagnosi.setDataDiagnosi(diagnosiRequest.getDataDiagnosi());
        diagnosi.setCodiceCIM10(diagnosiRequest.getCodiceCIM10());
        diagnosi.setTrattamentoRaccomandato(diagnosiRequest.getTrattamentoRaccomandato());
        diagnosi.setDescrizioneDiagnosi(diagnosiRequest.getDescrizioneDiagnosi());
        diagnosi.setPaziente(pazienteRepository.findById(diagnosiRequest.getPazienteId())
                .orElseThrow(() -> new RuntimeException("Paziente non trovato")));

        diagnosi = diagnosiRepository.save(diagnosi);
        return toResponse(diagnosi);
    }


    public void deleteDiagnosi(Long id) {
        Diagnosi diagnosi = diagnosiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diagnosi non trovata"));

        diagnosiRepository.delete(diagnosi);
    }

    public Page<DiagnosiResponse> filterDiagnosi(DiagnosiFilter diagnosiFilter, Pageable pageable) {
        Specification<Diagnosi> spec = DiagnosiSpecification.filterBy(diagnosiFilter);
        Page<Diagnosi> page = diagnosiRepository.findAll(spec, pageable);
        return page.map(this::toResponse);
    }
}


