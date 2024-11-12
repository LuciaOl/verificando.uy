package verificando.uy.controllers;

import verificando.uy.model.Usuario;
import verificando.uy.services.UsuarioService;
import verificando.uy.enums.Role;
import verificando.uy.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController extends UsuarioController {

    private final UsuarioService usuarioService;
    private final Utils utils;

    @Autowired
    public AdminController(UsuarioService usuarioService, Utils utils) {
        this.usuarioService = usuarioService;
        this.utils = utils;
    }

    @PostMapping("/crear-usuario")
    public ResponseEntity<String> crearUsuario(@RequestParam String nombre, @RequestParam String email, @RequestParam Role role) {
        
        if (usuarioService.emailRegistrado(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El email ya está registrado.");
        }
        
        Usuario usuario = new Usuario();
        usuario.setFullName(nombre);
        usuario.setEmail(email);
        usuario.setRole(role.name()); 
        
        String defaultPassword = "defaultPassword123";
        usuario.setPassword(utils.hashPassword(defaultPassword));
        
        usuario.setCedula(null);
        usuario.setId_token(null);
        usuario.setRefresh_token(null);
        
        usuarioService.crearUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado exitosamente.");
    }

    @PutMapping("/modificar-rol")
    public ResponseEntity<String> modificarRolUsuario(@RequestParam String email, @RequestParam Role nuevoRol) {
        try {
            usuarioService.modificarRolUsuario(email, nuevoRol.name());
            return ResponseEntity.status(HttpStatus.OK).body("Rol del usuario modificado exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
