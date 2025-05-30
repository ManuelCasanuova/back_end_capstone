package cartella.clinica.back_end_capstone.notifiche;


import cartella.clinica.back_end_capstone.auth.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificaService {

    @Autowired
    private NotificaRepository notificaRepository;

    public void inviaNotifica(AppUser destinatario, String messaggio) {
        Notifica notifica = new Notifica();
        notifica.setDestinatario(destinatario);
        notifica.setMessaggio(messaggio);
        notifica.setLetta(false);
        notifica.setDataCreazione(LocalDateTime.now());
        notificaRepository.save(notifica);
    }

    public List<Notifica> getNotificheNonLette(AppUser utente) {
        return notificaRepository.findByDestinatarioAndLettaFalse(utente);
    }

    public void segnaComeLetta(Long notificaId) {
        Notifica notifica = notificaRepository.findById(notificaId)
                .orElseThrow(() -> new RuntimeException("Notifica non trovata"));
        notifica.setLetta(true);
        notificaRepository.save(notifica);
    }

    public List<Notifica> getNotifichePerUtente(AppUser utente) {
        return notificaRepository.findByDestinatarioOrderByDataCreazioneDesc(utente);
    }
}

