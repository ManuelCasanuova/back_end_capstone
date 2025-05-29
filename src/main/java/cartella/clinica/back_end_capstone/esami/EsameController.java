package cartella.clinica.back_end_capstone.esami;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/esami")
public class EsameController {

    @Autowired
    private EsameService esameService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EsameResponse> uploadEsame(
            @RequestParam("pazienteId") Long pazienteId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "note", required = false) String note,
            @RequestParam("dataEsame") LocalDate dataEsame
    ) throws IOException {
        var esame = esameService.salvaEsame(pazienteId, file, note, dataEsame);
        return ResponseEntity.ok(EsameMapper.toResponse(esame));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EsameResponse> aggiornaEsame(
            @PathVariable Long id,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "note", required = false) String note,
            @RequestParam(value = "dataEsame", required = false) LocalDate dataEsame
    ) throws IOException {
        var esame = esameService.updateEsame(id, file, note, dataEsame);
        return ResponseEntity.ok(EsameMapper.toResponse(esame));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminaEsame(@PathVariable Long id) {
        esameService.deleteEsame(id);
        return ResponseEntity.ok("Esame eliminato con successo");
    }

    @GetMapping("/{id}/visualizza")
    public ResponseEntity<byte[]> visualizzaEsame(@PathVariable Long id) {
        var esame = esameService.getEsame(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + esame.getNomeFile() + "\"")
                .body(esame.getFile());
    }

    @GetMapping("/paziente/{pazienteId}")
    public ResponseEntity<List<EsameResponse>> getEsamiPerPaziente(@PathVariable Long pazienteId) {
        return ResponseEntity.ok(esameService.getEsamiDtoByPaziente(pazienteId));
    }
}
