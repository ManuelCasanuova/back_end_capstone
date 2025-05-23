package cartella.clinica.back_end_capstone.utenti;


import cartella.clinica.back_end_capstone.auth.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Utente findByAppUserUsername(String username);

    boolean existsByEmail(String email);

    Optional<Utente> findByAppUser(AppUser appUser);
}