package cartella.clinica.back_end_capstone.diagnosi;

import cartella.clinica.back_end_capstone.auth.AppUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/diagnosi")
public class DiagnosiController {

    @Autowired
    private DiagnosiService diagnosiService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public DiagnosiResponse createDiagnosi(@RequestBody @Valid DiagnosiRequest diagnosiRequest) {
        return diagnosiService.createDiagnosi(diagnosiRequest);
    }

    @GetMapping("/paziente/{pazienteId}")
    public List<DiagnosiResponse> getDiagnosiByPaziente(@PathVariable Long pazienteId) {
        return diagnosiService.findDiagnosiByPazienteId(pazienteId);
    }

    @GetMapping("")
    /*@PreAuthorize("hasRole('ROLE_ADMIN')")*/
    public Page<DiagnosiResponse> filterDiagnosi(
            @RequestParam(required = false) Long pazienteId,
            @RequestParam(required = false) String codiceCIM10,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataDiagnosi,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        DiagnosiFilter diagnosiFilter = new DiagnosiFilter();
        diagnosiFilter.setPazienteId(pazienteId);
        diagnosiFilter.setCodiceCIM10(codiceCIM10);
        diagnosiFilter.setDataDiagnosi(dataDiagnosi);

        Sort.Order order = new Sort.Order(Sort.Direction.fromString(sort[1]), sort[0]);
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));

        return diagnosiService.filterDiagnosi(diagnosiFilter, pageable);
    }

    @GetMapping("/{id}")
    public DiagnosiResponse findById(@PathVariable Long id) {
        return diagnosiService.findById(id);
    }

    @PutMapping("/{id}")
// @PreAuthorize("hasRole('ROLE_ADMIN')")
    public DiagnosiResponse updateDiagnosi(@PathVariable Long id, @RequestBody DiagnosiRequest diagnosiRequest) {
        return diagnosiService.updateDiagnosi(id, diagnosiRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDiagnosi(@PathVariable Long id) {
        System.out.println("Entrato nel controller per DELETE diagnosi id = " + id);
        diagnosiService.deleteDiagnosi(id);
    }
}


