package cartella.clinica.back_end_capstone.esami;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class EsameResponse {
    private Long id;
    private String nomeFile;
    private String tipoFile;
    private LocalDate dataCaricamento;
    private LocalDate dataEsame;
    private String note;
    private Long pazienteId;
}