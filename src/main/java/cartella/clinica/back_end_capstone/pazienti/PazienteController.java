package cartella.clinica.back_end_capstone.pazienti;


import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.auth.AppUserService;
import cartella.clinica.back_end_capstone.exceptions.BadRequestException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pazienti")

public class PazienteController {

    @Autowired
    private PazienteService pazienteService;

    @Autowired
    private AppUserService appUserService;


    @Autowired
    private PazienteRepository pazienteRepository;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PazienteResponse createPaziente(@RequestBody @Valid PazienteRequest pazienteRequest) {

        String passwordPredefinita = "Password123!";


        AppUser appUser = appUserService.registerUser(pazienteRequest, passwordPredefinita);


        Paziente pazienteCreato = pazienteRepository.findByAppUser(appUser)
                .orElseThrow(() -> new RuntimeException("Paziente non creato correttamente"));

        return PazienteResponse.from(pazienteCreato);
    }




    @GetMapping("/mio-profilo")
    public ResponseEntity<Paziente> getProfiloPaziente() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        AppUser user = appUserService.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));

        Paziente paziente = pazienteRepository.findByAppUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Paziente non trovato"));

        return ResponseEntity.ok(paziente);
    }

    @GetMapping("")

    public Page<PazienteResponse> filterPazienti(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cognome,
            @RequestParam(required = false) String codiceFiscale,
            @RequestParam(required = false) LocalDate dataNascita,
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
 /*   @PreAuthorize("hasRole('ROLE_ADMIN')")*/
    public void deletePazienteById(@PathVariable Long id, @AuthenticationPrincipal AppUser adminLoggato) {
        pazienteService.findPazienteByIdAndDelete(id);
    }

    @PutMapping("/{id}")
    public PazienteResponse updatePaziente(@PathVariable Long id, @RequestBody @Valid PazienteRequest pazienteRequest) {
        if (!id.equals(pazienteRequest.getId())) {
            throw new BadRequestException("ID percorso e body non coincidono");
        }
        Paziente aggiornato = pazienteService.findPazienteByIdAndUpdate(id, pazienteRequest);
        return pazienteService.toResponse(aggiornato);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PazienteResponse>> searchPazienti(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cognome) {

        PazienteFilter filter = new PazienteFilter();
        filter.setNomeParziale(nome);
        filter.setCognomeParziale(cognome);

        List<PazienteResponse> result = pazienteService.findPazientiByFilter(filter);
        return ResponseEntity.ok(result);
    }



}
