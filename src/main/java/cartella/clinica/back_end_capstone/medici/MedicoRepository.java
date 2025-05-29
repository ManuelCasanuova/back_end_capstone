package cartella.clinica.back_end_capstone.medici;


import cartella.clinica.back_end_capstone.auth.AppUser;
import cartella.clinica.back_end_capstone.utenti.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByAppUser(AppUser appUser);
    Optional<Medico> findByUtente(Utente utente);
}