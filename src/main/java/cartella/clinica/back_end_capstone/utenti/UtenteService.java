package cartella.clinica.back_end_capstone.utenti;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    public Page<Utente> getAllUtenti(Pageable pageable) {
        return utenteRepository.findAll(pageable);
    }

    public Utente saveUtente(Utente utente) {
        return utenteRepository.save(utente);
    }
}
