package cartella.clinica.back_end_capstone.pazienti;


import cartella.clinica.back_end_capstone.auth.AppUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pazienti")

public class PazienteController {

    @Autowired
    private PazienteService pazienteService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Paziente createPaziente(@RequestBody @Valid PazienteRequest pazienteRequest) {
        return pazienteService.savePaziente(pazienteRequest);
    }

    @GetMapping(" ")

    public Page<PazienteResponse> filterPazienti(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cognome,
            @RequestParam(required = false) String codiceFiscale,
            @RequestParam(required = false) String dataNascita,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        PazienteFilter pazienteFilter = new PazienteFilter();
        pazienteFilter.setNomeParziale(nome);
        pazienteFilter.setCognomeParziale(cognome);
        pazienteFilter.setCodiceFiscale(codiceFiscale);
        pazienteFilter.setDataDiNascita(dataNascita);

        if(sort[0].equals("prov")){
            if(sort[1].equals("asc")){
                Pageable pageable = PageRequest.of(page,size, Sort.by("cognome").ascending());
                return pazienteService.filterPazienti(pazienteFilter, pageable);
            }else {
                Pageable pageable = PageRequest.of(page, size, Sort.by("cognome").descending());
                return pazienteService.filterPazienti(pazienteFilter, pageable);
            }
        }

        else {
            Sort.Order order = new Sort.Order(Sort.Direction.fromString(sort[1]), sort[0]);
            Pageable pageable = PageRequest.of(page, size, Sort.by(order));
            return pazienteService.filterPazienti(pazienteFilter, pageable);
        }

    }

    @GetMapping("/{id}")
    public PazienteResponse findPazienteById(@PathVariable Long id) {
        return pazienteService.toResponse(pazienteService.findPazienteById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deletePazienteById(@PathVariable Long id, @AuthenticationPrincipal AppUser adminLoggato) {
        pazienteService.findPazienteByIdAndDelete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PazienteResponse updatePaziente(@PathVariable Long id, @RequestBody @Valid PazienteRequest pazienteRequest, @AuthenticationPrincipal AppUser adminLoggato) {
        return pazienteService.toResponse(pazienteService.findPazienteByIdAndUpdate(id, pazienteRequest));
    }


}
