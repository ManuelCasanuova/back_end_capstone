package cartella.clinica.back_end_capstone.prescrizioni;


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
@RequestMapping("/prescrizioni")

public class PrescrizioneController {

    @Autowired
    private PrescrizioneService prescrizioneService;



    //Filtra le prescrizioni per codice fiscale
    @GetMapping(" ")
    public Page<PrescrizioneResponse> findPrescrizioniByPazienteCodiceFiscale(

            @PathVariable String codiceFiscale,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataPrescrizione,desc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return prescrizioneService.getPrescrizioniByPazienteCodiceFiscale(codiceFiscale, pageable);
    }


    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public PrescrizioneResponse createPrescrizione(@RequestBody PrescrizioneRequest prescrizioneRequest) {
        return prescrizioneService.createPrescrizione(prescrizioneRequest);
    }

    @GetMapping("/{id}")
    public PrescrizioneResponse findPrescrizioneById(@PathVariable Long id) {
        return prescrizioneService.findPrescrizioneById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PrescrizioneResponse updatePrescrizione(@PathVariable Long id, @RequestBody @Valid PrescrizioneRequest prescrizioneRequest, @AuthenticationPrincipal AppUser adminLoggato) {
        return prescrizioneService.updatePrescrizione(id, prescrizioneRequest, adminLoggato);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePrescrizione(@PathVariable Long id, AppUser adminLoggato) {
        prescrizioneService.deletePrescrizione(id, adminLoggato);
    }

    public Page<PrescrizioneResponse> finAllPrescrizioni(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy){
        return prescrizioneService.findAllPrescrizioni(page, size, sortBy);
    }
}
