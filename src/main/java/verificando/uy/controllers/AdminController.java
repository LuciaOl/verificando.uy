package verificando.uy.controllers;

import verificando.uy.model.Usuario;
import verificando.uy.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import verificando.uy.utils.Utils;


@RestController
@RequestMapping("/admin")
public class AdminController extends UsuarioController {

    private final UsuarioService usuarioService;
    private Utils utils;


    @Autowired
    public AdminController(UsuarioService usuarioService, Utils utils) {
        this.usuarioService = usuarioService;
        this.utils = utils;

    }

    @PostMapping("/crear-usuario")
    public Usuario crearUsuario(@RequestParam String nombre, @RequestParam String email, @RequestParam String role) {
        Usuario usuario = new Usuario();
        usuario.setFullName(nombre);
        usuario.setEmail(email);
        usuario.setRole(role);
    
        String defaultPassword = "defaultPassword123";
        usuario.setPassword(utils.hashPassword(defaultPassword));
    
        usuario.setCedula(null);
        usuario.setId_token(null);
        usuario.setRefresh_token(null);
    
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
