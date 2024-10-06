package controllers;

import model.Hecho;
import model.Verificacion;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/checker")
public class CheckerController extends UsuarioController {

    // Métodos específicos para los checkers

    @PostMapping("/verificar-hecho")
    public Verificacion verificarHecho(@RequestBody Hecho hecho, @RequestParam boolean esVerdadero, @RequestParam String justificacion) {
        // Generar un ID único para la verificación (puedes usar UUID o un generador de IDs)
        String verificacionId = UUID.randomUUID().toString();

        // Crear una nueva instancia de Verificacion
        Verificacion verificacion = new Verificacion(verificacionId, hecho, esVerdadero, justificacion);

        // Aquí puedes añadir lógica para guardar la verificación en una base de datos o realizar otras acciones

        return verificacion; // Retornar la verificación
    }

    // Otros métodos específicos del checker
}
