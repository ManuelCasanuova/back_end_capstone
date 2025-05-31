package cartella.clinica.back_end_capstone.anamnesi;

import cartella.clinica.back_end_capstone.pazienti.Paziente;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class AnamnesiSpecification {

    public static Specification<Anamnesi> filterBy(AnamnesiFilter anamnesiFilter) {
        return (root, query, criteriaBuilder) -> {
            var predicate = criteriaBuilder.conjunction();

            Join<Anamnesi, Paziente> pazienteJoin = root.join("paziente");

            if (anamnesiFilter.getPazienteId() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(pazienteJoin.get("id"), anamnesiFilter.getPazienteId()));
            }

            if (anamnesiFilter.getDataAnamnesi() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("dataAnamnesi"), anamnesiFilter.getDataAnamnesi()));
            }

            if (anamnesiFilter.getCodiceFiscalePaziente() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(pazienteJoin.get("codiceFiscale"), anamnesiFilter.getCodiceFiscalePaziente()));
            }

            if (anamnesiFilter.getNominativoPaziente() != null && !anamnesiFilter.getNominativoPaziente().isEmpty()) {
                Predicate nomePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(pazienteJoin.get("nome")),
                        "%" + anamnesiFilter.getNominativoPaziente().toLowerCase() + "%"
                );
                Predicate cognomePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(pazienteJoin.get("cognome")),
                        "%" + anamnesiFilter.getNominativoPaziente().toLowerCase() + "%"
                );

                Predicate nominativoPredicate = criteriaBuilder.or(nomePredicate, cognomePredicate);
                predicate = criteriaBuilder.and(predicate, nominativoPredicate);
            } else {
                if (anamnesiFilter.getNomePaziente() != null && !anamnesiFilter.getNomePaziente().isEmpty()) {
                    Predicate nomePredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(pazienteJoin.get("nome")),
                            "%" + anamnesiFilter.getNomePaziente().toLowerCase() + "%"
                    );
                    predicate = criteriaBuilder.and(predicate, nomePredicate);
                }

                if (anamnesiFilter.getCognomePaziente() != null && !anamnesiFilter.getCognomePaziente().isEmpty()) {
                    Predicate cognomePredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(pazienteJoin.get("cognome")),
                            "%" + anamnesiFilter.getCognomePaziente().toLowerCase() + "%"
                    );
                    predicate = criteriaBuilder.and(predicate, cognomePredicate);
                }
            }

            return predicate;
        };
    }

}
