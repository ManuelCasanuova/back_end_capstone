package cartella.clinica.back_end_capstone.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicoRequest {

    @NotBlank(message = "Il campo 'nome' non può essere vuoto")
    private String nome;

    @NotBlank(message = "Il campo 'cognome' non può essere vuoto")
    private String cognome;


}
