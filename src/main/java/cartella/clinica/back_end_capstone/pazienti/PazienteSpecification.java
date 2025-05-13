package cartella.clinica.back_end_capstone.pazienti;

import jakarta.persistence.criteria.CriteriaQuery;
import org.springframework.data.jpa.domain.Specification;



public class PazienteSpecification {

    public static Specification<Paziente> filterBy(PazienteFilter pazienteFilter) {
        return (root, query, criteriaBuilder) -> {
            var predicate = criteriaBuilder.conjunction();

            if(pazienteFilter.getCodiceFiscale() != null)
                predicate= criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("codiceFiscale"), pazienteFilter.getCodiceFiscale()));

            if(pazienteFilter.getNomeParziale() != null)
                predicate= criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + pazienteFilter.getNomeParziale().toLowerCase() + "%"));

            if(pazienteFilter.getCognomeParziale() != null)
                predicate= criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("cognome")), "%" + pazienteFilter.getCognomeParziale().toLowerCase() + "%"));

            if(pazienteFilter.getDataDiRegistrazione() != null)
                predicate= criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("dataDiRegistrazione"), pazienteFilter.getDataDiRegistrazione()));

            if(pazienteFilter.getDataDiNascita() != null)
                predicate= criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("dataDiNascita"), pazienteFilter.getDataDiNascita()));

            if(pazienteFilter.getGruppoSanguigno() != null)
                predicate= criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("gruppoSanguigno"), pazienteFilter.getGruppoSanguigno()));

            if(pazienteFilter.getTelefonoCellulare() != null)
                predicate= criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("telefonoCellulare"), pazienteFilter.getTelefonoCellulare()));


            return predicate;
        };
    }
}
