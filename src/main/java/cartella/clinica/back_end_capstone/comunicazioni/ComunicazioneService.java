package cartella.clinica.back_end_capstone.comunicazioni;

import cartella.clinica.back_end_capstone.medici.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComunicazioneService {

    @Autowired
    private ComunicazioneRepository comunicazioneRepository;

    public List<ComunicazioneResponse> getComunicazioniGlobaliByMedico(Medico medico) {
        if (medico == null || medico.getId() == null) {
            throw new IllegalArgumentException("Medico non valido");
        }
        return comunicazioneRepository.comunicazioniGlobali(medico.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }


    public List<ComunicazioneResponse> getComunicazioniGlobaliPerPaziente(Long medicoId) {
        if (medicoId == null) {
            throw new IllegalArgumentException("ID del medico non valido");
        }
        return comunicazioneRepository.comunicazioniGlobali(medicoId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ComunicazioneResponse salvaComunicazioneGlobal(Comunicazione comunicazione) {
        Comunicazione salvata = comunicazioneRepository.save(comunicazione);
        return toResponse(salvata);
    }

    public ComunicazioneResponse aggiornaComunicazione(Long id, ComunicazioneRequest request, Medico medicoRichiedente) {
        Comunicazione comunicazione = comunicazioneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comunicazione non trovata"));

        if (!comunicazione.getMedico().getId().equals(medicoRichiedente.getId())) {
            throw new SecurityException("Non sei autorizzato a modificare questa comunicazione");
        }

        comunicazione.setOggetto(request.getOggetto());
        comunicazione.setTesto(request.getTesto());

        Comunicazione aggiornata = comunicazioneRepository.save(comunicazione);
        return toResponse(aggiornata);
    }

    public void eliminaComunicazione(Long id) {
        comunicazioneRepository.deleteById(id);
    }

    public ComunicazioneResponse toResponse(Comunicazione c) {
        ComunicazioneResponse r = new ComunicazioneResponse();
        r.setId(c.getId());
        r.setOggetto(c.getOggetto());
        r.setTesto(c.getTesto());
        r.setDataComunicazione(c.getDataComunicazione());
        r.setMedicoId(c.getMedico() != null ? c.getMedico().getId() : null);
        return r;
    }
}
