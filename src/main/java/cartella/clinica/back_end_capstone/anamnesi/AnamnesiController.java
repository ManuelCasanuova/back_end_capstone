package cartella.clinica.back_end_capstone.anamnesi;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/anamnesi")
public class AnamnesiController {

    @Autowired
    private AnamnesiService anamnesiService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public AnamnesiResponse createAnamnesi(@RequestBody @Valid AnamnesiRequest request) {
        return anamnesiService.createAnamnesi(request);
    }

    @GetMapping("/paziente/{pazienteId}")
    public List<AnamnesiResponse> getAnamnesiByPaziente(@PathVariable Long pazienteId) {
        return anamnesiService.findByPazienteId(pazienteId);
    }

    @GetMapping("/{id}")
    public AnamnesiResponse findById(@PathVariable Long id) {
        return anamnesiService.findAnamnesiById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public AnamnesiResponse updateAnamnesi(@PathVariable Long id, @RequestBody @Valid AnamnesiRequest request) {
        return anamnesiService.updateAnamnesi(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnamnesi(@PathVariable Long id) {
        anamnesiService.deleteAnamnesi(id);
    }
}


