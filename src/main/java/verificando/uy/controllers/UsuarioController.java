package verificando.uy.controllers;

import verificando.uy.dtos.DtCrearUsuarioRequest;
import verificando.uy.dtos.DtLoginRequest;
import verificando.uy.dtos.DtLoginResponse;
import verificando.uy.dtos.DtUsuario;
import verificando.uy.model.Usuario;
import verificando.uy.services.UsuarioService;
import verificando.uy.utils.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private Utils utils;
       
    @GetMapping("/usuarios/{gubuy_cedula}")
    public Usuario obtenerUsuarioGubuy(@PathVariable String gubuy_cedula) {
        return usuarioService.obtenerUsuarioPorCedula(gubuy_cedula);
    }

    // Agrega o modifica, no es necesario crear más funciones
   @PostMapping("/guardar")
   public Usuario guardarUsuario(@RequestBody Usuario usuario) {
       return usuarioService.crearUsuario(usuario); 
   }

   // Para futuro
   @PutMapping("/actualizar-tokens/{gubuy_cedula}")
   public Usuario actualizarTokens(
           @PathVariable String gubuy_cedula,
           @RequestParam String id_token,
           @RequestParam String refresh_token) {
       Usuario usuario = usuarioService.obtenerUsuarioPorCedula(gubuy_cedula);
       if (usuario != null) {
           usuario.setId_token(id_token);
           usuario.setRefresh_token(refresh_token);
           return usuarioService.actualizarUsuario_Cedula(usuario.getCedula(), usuario); 
       } else {
           throw new RuntimeException("Usuario no encontrado");
       }
   }

   @PostMapping("/registro")
   public ResponseEntity<Usuario> crearUsuarioConAtributos(@RequestBody DtCrearUsuarioRequest crearUsuarioRequest) {

        if (usuarioService.emailRegistrado(crearUsuarioRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(null); 
        }
        Usuario usuario = new Usuario();
        usuario.setFullName(crearUsuarioRequest.getNombre());
        usuario.setEmail(crearUsuarioRequest.getEmail());
        usuario.setRole("Citizen");

        // Hashear la contraseña antes de asignarla
        String hashedPassword = utils.hashPassword(crearUsuarioRequest.getPassword());
        usuario.setPassword(hashedPassword);

        Usuario usuarioGuardado = usuarioService.crearUsuario(usuario);
        return new ResponseEntity<>(usuarioGuardado, HttpStatus.CREATED);
   }

   @PostMapping("/login")
   public ResponseEntity<?> loginUsuario(@RequestBody DtLoginRequest loginRequest) {
       Usuario usuario = usuarioService.obtenerUsuarioPorEmail(loginRequest.getEmail());
       
       if (usuario == null) {
           return new ResponseEntity<>("Usuario no encontrado.", HttpStatus.NOT_FOUND);
       }
   
       if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
           return new ResponseEntity<>("El usuario no tiene una contraseña configurada.", HttpStatus.FORBIDDEN);
       }
       
       boolean isPasswordMatch = utils.verifyPassword(loginRequest.getPassword(), usuario.getPassword());
       
       if (!isPasswordMatch) {
           return new ResponseEntity<>("Contraseña incorrecta.", HttpStatus.UNAUTHORIZED);
       }
       
       DtUsuario usuarioDTO = new DtUsuario(
           usuario.getid(),
           usuario.getFullName(),
           usuario.getEmail()
       );
       
       DtLoginResponse response = new DtLoginResponse("Login exitoso.", usuarioDTO);
       
       return new ResponseEntity<>(response, HttpStatus.OK);
   }
   
}
