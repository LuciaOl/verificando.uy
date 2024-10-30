package verificando.uy.controllers;

import verificando.uy.model.Usuario;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController extends UsuarioController {

    // Métodos específicos para los administradores

    @PostMapping("/crear-usuario")
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        // Lógica para que el admin cree un nuevo usuario
        return usuario;
    }

    // Otros métodos específicos del administrador
}
