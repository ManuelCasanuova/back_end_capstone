package cartella.clinica.back_end_capstone.notifiche;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificaResponse {

    private Long id;
    private String messaggio;
    private boolean letta;
    private LocalDateTime dataCreazione;
    private Destinatario destinatario;
    private Long pazienteId;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Destinatario {
        private Long id;
        private String username;
        private String nome;
        private String cognome;
    }
}
