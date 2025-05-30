package cartella.clinica.back_end_capstone.notifiche;

import cartella.clinica.back_end_capstone.auth.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    // Nuovo metodo per convertire da Notifica entity a NotificaResponse DTO
    public NotificaResponse toResponse(Notifica notifica) {
        AppUser destinatario = notifica.getDestinatario();

        NotificaResponse.Destinatario destinatarioDto = new NotificaResponse.Destinatario(
                destinatario.getId(),
                destinatario.getUsername(),
                destinatario.getUtente() != null ? destinatario.getUtente().getNome() : null,
                destinatario.getUtente() != null ? destinatario.getUtente().getCognome() : null
        );

        return new NotificaResponse(
                notifica.getId(),
                notifica.getMessaggio(),
                notifica.isLetta(),
                notifica.getDataCreazione(),
                notifica.
                destinatarioDto
        );
    }

    // Metodo per ottenere le notifiche non lette già convertite in DTO
    public List<NotificaResponse> getNotificheNonLetteResponse(AppUser utente) {
        List<Notifica> notifiche = getNotificheNonLette(utente);
        return notifiche.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Metodo per ottenere tutte le notifiche per utente in formato DTO
    public List<NotificaResponse> getNotifichePerUtenteResponse(AppUser utente) {
        List<Notifica> notifiche = getNotifichePerUtente(utente);
        return notifiche.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}

