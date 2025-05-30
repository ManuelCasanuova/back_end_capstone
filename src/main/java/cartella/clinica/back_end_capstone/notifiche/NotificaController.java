package cartella.clinica.back_end_capstone.notifiche;

import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.auth.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifiche")
public class NotificaController {

    @Autowired
    private NotificaService notificaService;

    @Autowired
    private AppUserService appUserService;


    @GetMapping
    public List<Notifica> getNotificheUtente() {
        AppUser utenteLoggato = appUserService.getUtenteAutenticato();
        return notificaService.getNotificheNonLette(utenteLoggato);
    }


    @PostMapping("/{id}/letta")
    public void segnaComeLetta(@PathVariable Long id) {
        notificaService.segnaComeLetta(id);
    }
}
