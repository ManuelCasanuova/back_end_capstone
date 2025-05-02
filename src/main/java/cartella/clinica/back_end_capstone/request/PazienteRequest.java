package cartella.clinica.back_end_capstone.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PazienteRequest {

    @NotBlank(message = "Il campo 'nome' non può essere vuoto")
    private String nome;

    @NotBlank(message = "Il campo 'cognome' non può essere vuoto")
    private String cognome;

    @NotBlank(message = "Il campo 'dataDiNascita' non può essere vuoto")
    private String dataDiNascita;

    @NotBlank(message = "Il campo 'sesso' non può essere vuoto")
    private String sesso;

    @NotBlank(message = "Il campo 'codiceFiscale' non può essere vuoto")
    private String codiceFiscale;

    @NotBlank(message = "Il campo 'luogoDiNascita' non può essere vuoto")
    private String luogoDiNascita;

    @NotBlank(message = "Il campo 'indirizzo' non può essere vuoto")
    private String indirizzo;

    @NotBlank(message = "Il campo 'telefono' non può essere vuoto")
    private String telefono;

    @NotBlank(message = "Il campo 'email' non può essere vuoto")
    private String email;

}
