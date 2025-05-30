package cartella.clinica.back_end_capstone.notifiche;


import cartella.clinica.back_end_capstone.auth.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificaRepository extends JpaRepository<Notifica, Long> {


    List<Notifica> findByDestinatarioAndLettaFalse(AppUser destinatario);

    List<Notifica> findByDestinatarioOrderByDataCreazioneDesc(AppUser destinatario);
}