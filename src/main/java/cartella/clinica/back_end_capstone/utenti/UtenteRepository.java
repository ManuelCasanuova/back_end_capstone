package cartella.clinica.back_end_capstone.utenti;


import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Utente findByAppUserUsername(String username);

    boolean existsByEmail(String email);
}