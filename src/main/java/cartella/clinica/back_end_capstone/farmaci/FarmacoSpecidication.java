package cartella.clinica.back_end_capstone.farmaci;

import org.springframework.data.jpa.domain.Specification;

public class FarmacoSpecidication {

    public static Specification<Farmaco> filterBy(FarmacoFilter farmacoFilter) {
        return (root, query, criteriaBuilder) -> {
            var predicate = criteriaBuilder.conjunction();

            if (farmacoFilter.getNomeCommerciale() != null && !farmacoFilter.getNomeCommerciale().isEmpty()) {
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("nomeCommerciale")),
                                "%" + farmacoFilter.getNomeCommerciale().toLowerCase() + "%"
                        )
                );
            }


            if (farmacoFilter.getCodiceATC() != null && !farmacoFilter.getCodiceATC().isEmpty()) {
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.equal(root.get("codiceATC"), farmacoFilter.getCodiceATC())
                );
            }

            return predicate;
        };
    }
}
