package verificando.uy.verificando.controllers;

import verificando.uy.verificando.dto.PeripheralNodeRequest;
import verificando.uy.verificando.model.PeripheralNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import verificando.uy.verificando.services.PeripheralNodeService;



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
            // Manejo de errores
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
