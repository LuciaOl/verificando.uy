package verificando.uy.controllers;

import verificando.uy.dtos.PeripheralNodeRequest;
import verificando.uy.model.PeripheralNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import verificando.uy.services.PeripheralNodeService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/peripheral-nodes")
public class PeripheralNodeController {

    @Autowired
    private PeripheralNodeService peripheralNodeService;

    @PostMapping("/crear")
    public PeripheralNode crearNodoPeriferico(@RequestBody PeripheralNodeRequest request) {
        try {
            return peripheralNodeService.crearNodoPeriferico(request);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public PeripheralNode obtenerNodoPerifericoPorId(@PathVariable String id) {
        try {
            return peripheralNodeService.obtenerNodoPerifericoPorId(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping
    public List<PeripheralNode> listarTodosLosNodos() {
        try {
            return peripheralNodeService.listarTodos();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
