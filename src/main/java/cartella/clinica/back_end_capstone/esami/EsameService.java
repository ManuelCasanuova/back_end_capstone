package cartella.clinica.back_end_capstone.esami;

import cartella.clinica.back_end_capstone.pazienti.Paziente;
import cartella.clinica.back_end_capstone.pazienti.PazienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class EsameService {

    @Autowired
    private EsameRepository esameRepository;

    @Autowired
    private PazienteRepository pazienteRepository;

    public Esame salvaEsame(Long pazienteId, MultipartFile file, String note, LocalDate dataEsame) throws IOException {
        Paziente paziente = pazienteRepository.findById(pazienteId)
                .orElseThrow(() -> new RuntimeException("Paziente non trovato"));

        Esame esame = new Esame();
        esame.setNomeFile(file.getOriginalFilename());
        esame.setTipoFile(file.getContentType());
        esame.setFile(file.getBytes());
        esame.setNote(note);
        esame.setPaziente(paziente);
        esame.setDataEsame(dataEsame);
        esame.setDataCaricamento(LocalDate.now());

        return esameRepository.save(esame);
    }

    public Esame getEsame(Long id) {
        return esameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Esame non trovato"));
    }

    public List<EsameResponse> getEsamiDtoByPaziente(Long pazienteId) {
        return esameRepository.findByPazienteId(pazienteId)
                .stream()
                .map(EsameMapper::toResponse)
                .toList();
    }

    @Transactional
    public Esame updateEsame(Long esameId, MultipartFile file, String note, LocalDate dataEsame) throws IOException {
        Esame esame = esameRepository.findById(esameId)
                .orElseThrow(() -> new RuntimeException("Esame non trovato"));

        if (file != null && !file.isEmpty()) {
            if (!file.getContentType().equals("application/pdf")) {
                throw new RuntimeException("Solo file PDF sono ammessi");
            }
            esame.setNomeFile(file.getOriginalFilename());
            esame.setTipoFile(file.getContentType());
            esame.setFile(file.getBytes());
            esame.setDataCaricamento(LocalDate.now());
        }

        if (note != null) {
            esame.setNote(note);
        }

        if (dataEsame != null) {
            esame.setDataEsame(dataEsame);
        }

        return esameRepository.save(esame);
    }

    public void deleteEsame(Long esameId) {
        Esame esame = esameRepository.findById(esameId)
                .orElseThrow(() -> new RuntimeException("Esame non trovato"));
        esameRepository.delete(esame);
    }
}

