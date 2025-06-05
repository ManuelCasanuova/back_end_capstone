package cartella.clinica.back_end_capstone.farmaci;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/farmaci")
public class FarmacoController {

    @Autowired
    private FarmacoService farmacoService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public FarmacoResponse createFarmaco(@RequestBody @Valid FarmacoRequest request) {
        return farmacoService.createFarmaco(request);
    }

    @GetMapping("/paziente/{pazienteId}")
    public List<FarmacoResponse> getFarmaciByPaziente(@PathVariable Long pazienteId) {
        return farmacoService.getFarmaciByPaziente(pazienteId);
    }

    @GetMapping("/{id}")
    public FarmacoResponse getFarmacoById(@PathVariable Long id) {
        return farmacoService.getFarmacoById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public FarmacoResponse updateFarmaco(@PathVariable Long id, @RequestBody @Valid FarmacoRequest request) {
        return farmacoService.updateFarmaco(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFarmaco(@PathVariable Long id) {
        farmacoService.deleteFarmaco(id);
    }
}







