package cartella.clinica.back_end_capstone.esami;

import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.auth.AppUserService;
import cartella.clinica.back_end_capstone.notifiche.NotificaService;
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

    @Autowired
    private NotificaService notificaService;

    @Autowired
    private AppUserService appUserService;

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

        Esame salvato = esameRepository.save(esame);

        AppUser utenteCorrente = appUserService.getUtenteAutenticato();

        System.out.println("Utente corrente id: " + utenteCorrente.getId());
        System.out.println("Paziente id esame: " + paziente.getAppUser().getId());

        if (utenteCorrente.getId().equals(paziente.getAppUser().getId())) {

            if (paziente.getMedico() != null && paziente.getMedico().getAppUser() != null) {
                String messaggio = "Il paziente " + paziente.getUtente().getNome() + " " + paziente.getUtente().getCognome() + " ha caricato un nuovo esame.";
                notificaService.inviaNotifica(paziente.getMedico().getAppUser(), messaggio);
                System.out.println("Notifica inviata al medico: " + paziente.getMedico().getUtente().getNome());
            }
        } else {

            if (paziente.getAppUser() != null) {
                String messaggio = "Il Dottor " + paziente.getMedico().getUtente().getNome() + " " + paziente.getMedico().getUtente().getCognome() + " ha caricato un nuovo esame nel tuo profilo.";
                notificaService.inviaNotifica(paziente.getAppUser(), messaggio);
                System.out.println("Notifica inviata al paziente: " + paziente.getUtente().getNome());
            }
        }

        return salvato;
    }



    public Esame getEsame(Long id) {
        return esameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Esame non trovato"));
    }

    @Transactional
    public List<EsameResponse> getEsamiDtoByPaziente(Long pazienteId) {
        System.out.println("Recupero esami per paziente id: " + pazienteId);
        List<Esame> esami = esameRepository.findByPazienteId(pazienteId);
        System.out.println("Esami trovati nel repo: " + esami.size());
        List<EsameResponse> responses = esami.stream()
                .map(EsameMapper::toResponse)
                .toList();
        System.out.println("Esami convertiti a DTO: " + responses.size());
        return responses;
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

