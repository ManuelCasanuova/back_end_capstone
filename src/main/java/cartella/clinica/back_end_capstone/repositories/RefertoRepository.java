package cartella.clinica.back_end_capstone.repositories;

import cartella.clinica.back_end_capstone.entities.Referto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefertoRepository extends JpaRepository<Referto, Long> {

}