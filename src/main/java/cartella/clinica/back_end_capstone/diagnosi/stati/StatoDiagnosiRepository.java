package cartella.clinica.back_end_capstone.diagnosi.stati;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatoDiagnosiRepository extends JpaRepository<StatoDiagnosi, Long> {
    Optional<StatoDiagnosi> findByNome(String nomeStatoDiagnosi);
}