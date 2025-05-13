package cartella.clinica.back_end_capstone.appuntamenti;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppuntamentoFilter {
    private Long pazienteId;
    private String motivoRichiesta;
    private LocalDateTime dataOraAppuntamento;
}
