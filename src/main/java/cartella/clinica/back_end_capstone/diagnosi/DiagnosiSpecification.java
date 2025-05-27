package cartella.clinica.back_end_capstone.diagnosi;

import org.springframework.data.jpa.domain.Specification;

public class DiagnosiSpecification {


    public static Specification<Diagnosi> filterBy(DiagnosiFilter diagnosiFilter) {
        return (root, query, criteriaBuilder) -> {
            var predicate = criteriaBuilder.conjunction();

            if(diagnosiFilter.getPazienteId() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("paziente").get("pazienteId"), diagnosiFilter.getPazienteId()));
            }

            if (diagnosiFilter.getCodiceCIM10() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("codiceCIM10"), diagnosiFilter.getCodiceCIM10()));
            }

            if (diagnosiFilter.getDataDiagnosi() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("dataDiagnosi"), diagnosiFilter.getDataDiagnosi()));
            }

            return predicate;
        };
    }
}
