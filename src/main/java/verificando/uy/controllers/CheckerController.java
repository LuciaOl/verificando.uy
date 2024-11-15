package verificando.uy.controllers;

import org.springframework.web.bind.annotation.*;
import verificando.uy.model.Hecho;
import verificando.uy.model.PeripheralNode;
import verificando.uy.model.Verificacion;
import verificando.uy.repositories.VerificacionRepository;
import verificando.uy.services.PeripheralNodeService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/checker")
public class CheckerController extends UsuarioController {
    private PeripheralNode nodoSeleccionado;


    private final HechoController hechoController;

    private final PeripheralNodeService peripheralNodeService;

    public CheckerController(HechoController hechoController, PeripheralNodeService peripheralNodeService) {
        this.hechoController = hechoController;
        this.peripheralNodeService = peripheralNodeService;
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


    @PutMapping("/seleccionarNodo/{nodeID}")
    public void seleccionarNodo(@PathVariable Long nodeID, @RequestParam Hecho hechoAVerificar) throws Exception {
        this.nodoSeleccionado = peripheralNodeService.obtenerNodoPerifericoPorId(nodeID);
    }
}
