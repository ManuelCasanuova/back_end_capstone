package cartella.clinica.back_end_capstone.appuntamenti;

import org.springframework.data.jpa.domain.Specification;

public class AppuntamentoSpecification {
    public static Specification<Appuntamento> appuntamentoFilter(AppuntamentoFilter appuntamentoFilter) {
        return (root, query, criteriaBuilder) -> {
            var predicate = criteriaBuilder.conjunction();

        if(appuntamentoFilter.getPazienteId() != null)  {
            predicate = criteriaBuilder.equal(root.get("paziente").get("id"), appuntamentoFilter.getPazienteId());
        }

        if(appuntamentoFilter.getMotivoRichiesta() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("motivoRichiesta"), "%" + appuntamentoFilter.getMotivoRichiesta() + "%"));
        }

        if(appuntamentoFilter.getDataOraAppuntamento() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("dataOraAppuntamento"), appuntamentoFilter.getDataOraAppuntamento()));
        }

        return predicate;
        };
    }
}
