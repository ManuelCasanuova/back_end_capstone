package cartella.clinica.back_end_capstone.esami;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class EsameRequest {
    private Long pazienteId;
    private MultipartFile file;
    private String note;
    private LocalDate dataEsame;
}