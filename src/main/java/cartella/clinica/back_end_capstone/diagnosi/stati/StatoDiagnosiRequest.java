package cartella.clinica.back_end_capstone.diagnosi.stati;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatoDiagnosiRequest {

    @NotBlank(message = "Il nome dello stato della diagnosi non può essere vuoto")
    private String nomeStatoDiagnosi;



}
