package cartella.clinica.back_end_capstone.studi;

public class StudioResponseBase {
    private String nome;
    private String indirizzo;
    private String telefono;

    public StudioResponseBase(String nome, String indirizzo, String telefono) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
    }

    public String getNome() {
        return nome;
    }
    public String getIndirizzo() {
        return indirizzo;
    }
    public String getTelefono() {
        return telefono;
    }
}
