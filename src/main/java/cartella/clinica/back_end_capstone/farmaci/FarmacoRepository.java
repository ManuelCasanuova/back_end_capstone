package cartella.clinica.back_end_capstone.farmaci;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FarmacoRepository extends JpaRepository<Farmaco, Long>, JpaSpecificationExecutor<Farmaco> {

    Farmaco findByNomeCommerciale(String nomeCommerciale);


    Farmaco findByCodiceATC(String codiceATC);
}