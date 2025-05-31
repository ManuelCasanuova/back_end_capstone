package cartella.clinica.back_end_capstone.anamnesi;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AnamnesiFilter {
    private Long pazienteId;
    private LocalDate dataAnamnesi;
    private String nomePaziente;
    private String cognomePaziente;
    private String codiceFiscalePaziente;
    private String nominativoPaziente;
}
