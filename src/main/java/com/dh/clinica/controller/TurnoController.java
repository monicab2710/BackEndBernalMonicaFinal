package com.dh.clinica.controller;

import com.dh.clinica.exceptions.BadRequestException;
import com.dh.clinica.model.Turno;
import com.dh.clinica.service.OdontologoService;
import com.dh.clinica.service.PacienteService;
import com.dh.clinica.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    //

    @PostMapping
    public ResponseEntity<?> registrarTurno(@RequestBody Turno turno) throws BadRequestException {
        turnoService.registrarTurno(turno);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/turnos")
    public ResponseEntity<List<Turno>> listar() {
        return ResponseEntity.ok(turnoService.listar());
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id)  throws BadRequestException{
        ResponseEntity<String> response;
        if (turnoService.buscar(id).isPresent()) {
            try {
                turnoService.eliminar(id);
            } catch (BadRequestException e) {
                e.printStackTrace();
            }
            response = ResponseEntity.status(NO_CONTENT).body("Eliminado");
        } else {
            response = ResponseEntity.status(NOT_FOUND).build();
        }
        return response;
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Turno> actualizarTurno(@RequestBody Turno turno) {
        return ResponseEntity.ok(turnoService.actualizar(turno));

    }

    @ExceptionHandler ({BadRequestException.class})

    public ResponseEntity<String> procesarErrorBadRequest(BadRequestException ex) {return ResponseEntity.status(BAD_REQUEST).body(ex .getMessage());
    }


}
