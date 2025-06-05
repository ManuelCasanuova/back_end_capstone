package cartella.clinica.back_end_capstone.anamnesi;

import cartella.clinica.back_end_capstone.anamnesi.fattoriDiRischio.FattoreDiRischio;
import cartella.clinica.back_end_capstone.anamnesi.fattoriDiRischio.FattoreDiRischioRequest;
import cartella.clinica.back_end_capstone.anamnesi.fattoriDiRischio.FattoreDiRischioResponse;
import cartella.clinica.back_end_capstone.auth.Role;
import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.pazienti.Paziente;
import cartella.clinica.back_end_capstone.pazienti.PazienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnamnesiService {

    @Autowired
    private AnamnesiRepository anamnesiRepository;

    @Autowired
    private PazienteRepository pazienteRepository;

    private Anamnesi toEntity(AnamnesiRequest req) {
        Anamnesi anamnesi = new Anamnesi();
        anamnesi.setDescrizioneAnamnesi(req.getDescrizioneAnamnesi());

        Paziente paziente = pazienteRepository.findById(req.getPazienteId())
                .orElseThrow(() -> new RuntimeException("Paziente non trovato con id: " + req.getPazienteId()));
        anamnesi.setPaziente(paziente);

        if (anamnesi.getId() == null) {
            anamnesi.setDataInserimentoAnamnesi(LocalDate.now());
        }

        anamnesi.setFattoreDiRischio(toEntity(req.getFattoreDiRischio()));
        return anamnesi;
    }

    private Anamnesi updateEntity(Anamnesi anamnesi, AnamnesiRequest req) {
        anamnesi.setDescrizioneAnamnesi(req.getDescrizioneAnamnesi());

        Paziente paziente = pazienteRepository.findById(req.getPazienteId())
                .orElseThrow(() -> new RuntimeException("Paziente non trovato con id: " + req.getPazienteId()));
        anamnesi.setPaziente(paziente);

        anamnesi.setFattoreDiRischio(toEntity(req.getFattoreDiRischio()));
        return anamnesi;
    }

    private AnamnesiResponse toResponse(Anamnesi anamnesi) {
        AnamnesiResponse response = new AnamnesiResponse();
        response.setId(anamnesi.getId());
        response.setDataInserimentoAnamnesi(anamnesi.getDataInserimentoAnamnesi());
        response.setDescrizioneAnamnesi(anamnesi.getDescrizioneAnamnesi());
        response.setPazienteId(anamnesi.getPaziente().getId());
        response.setFattoreDiRischio(toResponse(anamnesi.getFattoreDiRischio()));
        return response;
    }

    private FattoreDiRischio toEntity(FattoreDiRischioRequest req) {
        if (req == null) return null;

        FattoreDiRischio fr = new FattoreDiRischio();
        fr.setFumatore(req.isFumatore());
        fr.setDataInizioFumo(req.isFumatore() ? req.getDataInizioFumo() : null);
        fr.setUsoAlcol(req.isUsoAlcol());
        fr.setDataUltimaAssunzioneAlcol(req.isUsoAlcol() ? req.getDataUltimaAssunzioneAlcol() : null);
        fr.setUsoStupefacente(req.isUsoStupefacente());
        fr.setDataUltimaAssunzioneStupefacente(req.isUsoStupefacente() ? req.getDataUltimaAssunzioneStupefacente() : null);
        fr.setNote(req.getNote());
        return fr;
    }

    private FattoreDiRischioResponse toResponse(FattoreDiRischio fr) {
        if (fr == null) return null;

        FattoreDiRischioResponse resp = new FattoreDiRischioResponse();
        resp.setFumatore(fr.isFumatore());
        resp.setDataInizioFumo(fr.getDataInizioFumo());
        resp.setUsoAlcol(fr.isUsoAlcol());
        resp.setDataUltimaAssunzioneAlcol(fr.getDataUltimaAssunzioneAlcol());
        resp.setUsoStupefacente(fr.isUsoStupefacente());
        resp.setDataUltimaAssunzioneStupefacente(fr.getDataUltimaAssunzioneStupefacente());
        resp.setNote(fr.getNote());
        return resp;
    }

    public AnamnesiResponse createAnamnesi(AnamnesiRequest request) {
        Anamnesi anamnesi = toEntity(request);
        anamnesi = anamnesiRepository.save(anamnesi);
        return toResponse(anamnesi);
    }

    public List<AnamnesiResponse> findByPazienteId(Long pazienteId) {
        List<Anamnesi> lista = anamnesiRepository.findByPazienteId(pazienteId);
        return lista.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public AnamnesiResponse findAnamnesiById(Long id) {
        Anamnesi anamnesi = anamnesiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anamnesi non trovata"));
        return toResponse(anamnesi);
    }

    public AnamnesiResponse updateAnamnesi(Long id, AnamnesiRequest request) {
        Anamnesi anamnesi = anamnesiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anamnesi non trovata"));

        updateEntity(anamnesi, request);
        anamnesi = anamnesiRepository.save(anamnesi);
        return toResponse(anamnesi);
    }

    public void deleteAnamnesi(Long id) {
        Anamnesi anamnesi = anamnesiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anamnesi non trovata"));
        anamnesiRepository.delete(anamnesi);
    }
}




