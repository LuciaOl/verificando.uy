package verificando.uy.controllers;

import verificando.uy.model.Usuario;
import verificando.uy.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController extends UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public AdminController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Método para que el admin cree un nuevo usuario
    @PostMapping("/crear-usuario")
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        // Lógica para que el admin cree un nuevo usuario
        return usuarioService.crearUsuario(usuario);
    }

    // Método para modificar el rol de un usuario
    @PutMapping("/modificar-rol")
    public ResponseEntity<?> modificarRolUsuario(@RequestParam String email, @RequestParam String nuevoRol) {
        try {
            Usuario usuarioActualizado = usuarioService.modificarRolUsuario(email, nuevoRol);
            return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
