package verificando.uy.controllers;

import org.springframework.web.bind.annotation.*;
import verificando.uy.model.Hecho;
import verificando.uy.model.PeripheralNode;
import verificando.uy.model.Verificacion;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/checker")
public class CheckerController extends UsuarioController {

    private final HechoController hechoController;

    public CheckerController(HechoController hechoController) {
        this.hechoController = hechoController;
    }


    // Métodos específicos para los checkers

    @GetMapping("/listaHechosEnProceso")
    public Optional<List<Hecho>> HechosEnProceso(){
        return hechoController.obtenerHechosEnProceso();
    }


    @PostMapping("/verificar-hecho")
    public Verificacion verificarHecho(@RequestBody Hecho hecho, @RequestParam boolean esVerdadero, @RequestParam String justificacion) {
        // Generar un ID único para la verificación (puedes usar UUID o un generador de IDs)

        // Crear una nueva instancia de Verificacion
        Verificacion verificacion = new Verificacion(hecho, esVerdadero, justificacion);

        // Aquí puedes añadir lógica para guardar la verificación en una base de datos o realizar otras acciones

        return verificacion; // Retornar la verificación
    }


    public Optional<PeripheralNode> solicitarDatosAdicionalesNodoPeriferico(Long id){
        
    }

}
