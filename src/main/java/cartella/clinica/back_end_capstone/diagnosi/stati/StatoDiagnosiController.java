package cartella.clinica.back_end_capstone.diagnosi.stati;


import cartella.clinica.back_end_capstone.auth.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stato-diagnosi")

public class StatoDiagnosiController {

    @Autowired
    StatoDiagnosiService statoDiagnosiService;

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public StatoDiagnosi updateStatoDiagnosi(@PathVariable Long id, @RequestBody StatoDiagnosi statoDiagnosi, @AuthenticationPrincipal AppUser adminLoggato) {
        return statoDiagnosiService.updateStatoDiagnosi(id, statoDiagnosi, adminLoggato);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStatoDiagnosi(@PathVariable Long id, @AuthenticationPrincipal AppUser adminLoggato) {
        statoDiagnosiService.deleteStatoDiagnosi(id, adminLoggato);
    }


}
