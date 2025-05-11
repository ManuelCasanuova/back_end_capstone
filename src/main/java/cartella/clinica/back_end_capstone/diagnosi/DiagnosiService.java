package cartella.clinica.back_end_capstone.diagnosi;


import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.auth.Role;
import cartella.clinica.back_end_capstone.diagnosi.stati.StatoDiagnosiService;
import cartella.clinica.back_end_capstone.pazienti.PazienteRepository;
import cartella.clinica.back_end_capstone.pazienti.PazienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated

public class DiagnosiService {

    @Autowired
    private DiagnosiRepository diagnosiRepository;

    @Autowired
    private PazienteRepository pazienteRepository;

    @Autowired
    private PazienteService pazienteService;

    @Autowired
    private StatoDiagnosiService statoDiagnosiService;

    private Diagnosi toEntity(DiagnosiRequest diagnosiRequest) {
        Diagnosi diagnosi = new Diagnosi();
        diagnosi.setDataDiagnosi(diagnosiRequest.getDataDiagnosi());
        diagnosi.setCodiceCIM10(diagnosiRequest.getCodiceCIM10());
        diagnosi.setPaziente(pazienteRepository.findById(diagnosiRequest.getPazienteId()).get());

        if(diagnosiRequest.getStatoDiagnosi() != null){
            diagnosi.setStatoDiagnosi(statoDiagnosiService.findStatoDiagnosiByNome("Attiva"));
        } else{
            diagnosi.setStatoDiagnosi(statoDiagnosiService.findStatoDiagnosiByNome(diagnosiRequest.getStatoDiagnosi()));
        }
        return diagnosi;
    }


   private DiagnosiResponse toResponse(Diagnosi diagnosi) {
       return new DiagnosiResponse(
               diagnosi.getId(),
               diagnosi.getCodiceCIM10(),
               diagnosi.getDataInserimentoDiagnosi(),
               diagnosi.getTrattamentoRaccomandato(),
               diagnosi.getDataDiagnosi(),
               diagnosi.getDataFineDiagnosi(),
               diagnosi.getDescrizioneDiagnosi(),
               diagnosi.getPaziente().getPazienteId(),
               diagnosi.getStatoDiagnosi()
       );
   }

   public DiagnosiResponse createDiagnosi(DiagnosiRequest diagnosiRequest, AppUser adminLoggato) {
       boolean isAdmin = adminLoggato.getRoles().contains(Role.ROLE_ADMIN);
       if(!isAdmin) {
           throw new RuntimeException("Non sei autorizzato a creare una diagnosi");
       } else {
           Diagnosi diagnosi = toEntity(diagnosiRequest);
           diagnosi = diagnosiRepository.save(diagnosi);
           return toResponse(diagnosi);
       }
   }


   public Page<DiagnosiResponse> FindAllDiagnosi(int page, int size, String sortBy){
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
    return diagnosiRepository.findAll(pageable).map(this::toResponse);
    }

    public DiagnosiResponse findById(Long id) {
        Diagnosi diagnosi = diagnosiRepository.findById(id).orElseThrow(() -> new RuntimeException("Diagnosi non trovata"));
        return toResponse(diagnosi);
    }

    public DiagnosiResponse updateDiagnosi(Long id, DiagnosiRequest diagnosiRequest, AppUser adminLoggato) {
        Diagnosi diagnosi = diagnosiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diagnosi non trovata"));

        boolean isAdmin = adminLoggato.getRoles().contains(Role.ROLE_ADMIN);
        if(!isAdmin) {
            throw new RuntimeException("Non sei autorizzato a modificare la diagnosi");
        } else {
            diagnosi.setDataDiagnosi(diagnosiRequest.getDataDiagnosi());
            diagnosi.setCodiceCIM10(diagnosiRequest.getCodiceCIM10());
            diagnosi.setPaziente(pazienteRepository.findById(diagnosiRequest.getPazienteId()).get());
            diagnosi.setStatoDiagnosi(statoDiagnosiService.findStatoDiagnosiByNome(diagnosiRequest.getStatoDiagnosi()));
            diagnosi = diagnosiRepository.save(diagnosi);
            return toResponse(diagnosi);
        }
    }

    public void deleteDiagnosi(Long id, AppUser adminLoggato) {
        Diagnosi diagnosi = diagnosiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diagnosi non trovata"));

        boolean isAdmin = adminLoggato.getRoles().contains(Role.ROLE_ADMIN);
        if(!isAdmin) {
            throw new RuntimeException("Non sei autorizzato a eliminare la diagnosi");
        } else {
            diagnosiRepository.delete(diagnosi);
        }
    }

    public Page<DiagnosiResponse> filterDiagnosi(DiagnosiFilter diagnosiFilter, Pageable pageable) {
        Specification<Diagnosi> spec = DiagnosiSpecification.filterBy(diagnosiFilter);
        Page<Diagnosi> page = diagnosiRepository.findAll(spec, pageable);
        return page.map(this::toResponse);
    }




}
