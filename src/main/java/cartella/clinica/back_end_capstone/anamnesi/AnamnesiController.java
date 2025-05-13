package cartella.clinica.back_end_capstone.anamnesi;


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

@RestController
@RequestMapping("/anamnesi")

public class AnamnesiController {

    @Autowired
    private AnamnesiService anamnesiService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnamnesiResponse createAnamnesi(@RequestBody @Valid AnamnesiRequest anamnesiRequest) {
        return anamnesiService.createAnamnesi(anamnesiRequest);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<AnamnesiResponse> findAll (@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "id") String sortBy) {
        return anamnesiService.findAll(page, size, sortBy);
    }


    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<AnamnesiResponse> filterAnamnesi(
            @RequestParam(required = false) Long pazienteId,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cognome,
            @RequestParam(required = false) String codiceFiscale,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate dataAnamnesi,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String [] sort) {

        AnamnesiFilter anamnesiFilter = new AnamnesiFilter();
        anamnesiFilter.setPazienteId(pazienteId);
        anamnesiFilter.setNomePaziente(nome);
        anamnesiFilter.setCognomePaziente(cognome);
        anamnesiFilter.setCodiceFiscalePaziente(codiceFiscale);
        anamnesiFilter.setDataAnamnesi(dataAnamnesi);

        Sort.Order order = new Sort.Order(Sort.Direction.fromString(sort[1]), sort[0]);
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));
        return anamnesiService.filterAnamnesi(anamnesiFilter, pageable);

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public AnamnesiResponse updateAnamnesi(@PathVariable Long id, @RequestBody @Valid AnamnesiRequest anamnesiRequest, @AuthenticationPrincipal AppUser adminLoggato) {
        return anamnesiService.updateAnamnesi(id, anamnesiRequest, adminLoggato);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnamnesi(@PathVariable Long id, @AuthenticationPrincipal AppUser adminLoggato) {
        anamnesiService.deleteAnamnesi(id, adminLoggato);
    }

}
