package cartella.clinica.back_end_capstone.services;

import cartella.clinica.back_end_capstone.entities.Medico;
import cartella.clinica.back_end_capstone.entities.Paziente;
import cartella.clinica.back_end_capstone.repositories.MedicoRepository;
import cartella.clinica.back_end_capstone.repositories.PazienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public class MedicoService {



    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PazienteRepository pazienteRepository;


}
