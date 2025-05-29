package cartella.clinica.back_end_capstone.esami;


public class EsameMapper {

    public static EsameResponse toResponse(Esame esame) {
        return new EsameResponse(
                esame.getId(),
                esame.getNomeFile(),
                esame.getTipoFile(),
                esame.getDataCaricamento(),
                esame.getDataEsame(),
                esame.getNote(),
                esame.getPaziente().getId()
        );
    }
}

