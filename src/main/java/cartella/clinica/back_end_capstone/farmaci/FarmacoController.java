package cartella.clinica.back_end_capstone.farmaci;



import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/farmaci")
public class FarmacoController {

    @Autowired
    private FarmacoService farmacoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FarmacoResponse createFarmaco(@RequestBody @Valid FarmacoRequest request) {
        return farmacoService.createFarmaco(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FarmacoResponse> getFarmacoById(@PathVariable Long id) {
        return ResponseEntity.ok(farmacoService.getFarmacoById(id));
    }

    @GetMapping
    public ResponseEntity<Page<FarmacoResponse>> getAllFarmaci(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nomeCommerciale,asc") String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();
        for (String sortParam : sort) {
            String[] parts = sortParam.split(",");
            String sortBy = parts[0];
            Sort.Direction direction = parts.length > 1 && parts[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            orders.add(new Sort.Order(direction, sortBy));
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        return ResponseEntity.ok(farmacoService.findAll(pageable));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<FarmacoResponse> updateFarmaco(@PathVariable Long id, @RequestBody @Valid FarmacoRequest request) {
        return ResponseEntity.ok(farmacoService.updateFarmaco(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFarmaco(@PathVariable Long id) {
        farmacoService.deleteFarmaco(id);
    }

    @GetMapping("/filter")
    public Page<FarmacoResponse> filterFarmaci(
            @RequestParam(required = false) String nomeCommerciale,
            @RequestParam(required = false) String codiceATC,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nomeCommerciale,asc") String[] sort) {

        FarmacoFilter filter = new FarmacoFilter();
        filter.setNomeCommerciale(nomeCommerciale);
        filter.setCodiceATC(codiceATC);

        Sort.Order order = new Sort.Order(Sort.Direction.fromString(sort[1]), sort[0]);
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));

        return farmacoService.filterFarmaci(filter, pageable);
    }

    private Sort buildSort(String[] sortParams) {
        List<Sort.Order> orders = new ArrayList<>();
        for (String sortParam : sortParams) {
            String[] parts = sortParam.split(",");
            String sortBy = parts[0];
            Sort.Direction direction = parts.length > 1 && parts[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            orders.add(new Sort.Order(direction, sortBy));
        }
        return Sort.by(orders);
    }
}
