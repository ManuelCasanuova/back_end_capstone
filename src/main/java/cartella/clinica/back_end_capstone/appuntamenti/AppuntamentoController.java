package cartella.clinica.back_end_capstone.appuntamenti;


import cartella.clinica.back_end_capstone.auth.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appuntamenti")


public class AppuntamentoController {

    @Autowired
    private AppuntamentoService appuntamentoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppuntamentoResponse createAppuntamento(AppuntamentoRequest appuntamentoRequest) {
        return appuntamentoService.createAppuntamento(appuntamentoRequest);
    }


    public Page<AppuntamentoResponse> findAllAppuntamenti(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy){
        return appuntamentoService.findAllAppuntamenti(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public AppuntamentoResponse findAppuntamentoById(@PathVariable Long id) {
        return appuntamentoService.findAppuntamentoById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public AppuntamentoResponse updateAppuntamento(@PathVariable Long id, @RequestBody AppuntamentoResponse appuntamentoRequest, @AuthenticationPrincipal AppUser adminLoggato) {
        return appuntamentoService.updateAppuntamento(id, appuntamentoRequest, adminLoggato);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAppuntamento(@PathVariable Long id) {
        appuntamentoService.deleteAppuntamento(id);
    }

}
