package verificando.uy.controllers;

import verificando.uy.dtos.DtCrearUsuarioRequest;
import verificando.uy.dtos.DtLoginRequest;
import verificando.uy.dtos.DtLoginResponse;
import verificando.uy.dtos.DtUsuario;
import verificando.uy.model.Usuario;
import verificando.uy.repositories.UsuarioRepository;
import verificando.uy.utils.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;



@RestController
@RequestMapping("/usuarios")
public class UsuarioController {


    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private Utils utils;
       
    @GetMapping("/usuarios/{gubuy_cedula}")
    public Usuario obtenerUsuarioGubuy(@PathVariable String gubuy_cedula) {
        return usuarioRepository.findByCedula(gubuy_cedula);
    }

    // Agrega o modifica, no es necesario crear mas funciones
   @PostMapping("/guardar")
   public Usuario guardarUsuario(@RequestBody Usuario usuario) {
       return usuarioRepository.save(usuario); 
   }

   // Para futuro
   @PutMapping("/actualizar-tokens/{gubuy_cedula}")
   public Usuario actualizarTokens(
           @PathVariable String gubuy_cedula,
           @RequestParam String id_token,
           @RequestParam String refresh_token) {
       Usuario usuario = usuarioRepository.findByCedula(gubuy_cedula);
       if (usuario != null) {
           usuario.setId_token(id_token);
           usuario.setRefresh_token(refresh_token);
           return usuarioRepository.save(usuario); 
       } else {
           throw new RuntimeException("Usuario no encontrado");
       }
   }


   @PostMapping("/registro")
   public ResponseEntity<Usuario> crearUsuarioConAtributos(@RequestBody DtCrearUsuarioRequest crearUsuarioRequest) {
       Usuario usuario = new Usuario();
       usuario.setFullName(crearUsuarioRequest.getNombre());
       usuario.setEmail(crearUsuarioRequest.getEmail());

       // Hashear la contraseña antes de asignarla
       String hashedPassword = utils.hashPassword(crearUsuarioRequest.getPassword());
       usuario.setPassword(hashedPassword);

       Usuario usuarioGuardado = usuarioRepository.save(usuario);
       

       return new ResponseEntity<>(usuarioGuardado, HttpStatus.CREATED);
   }


    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody DtLoginRequest loginRequest) {
        // Buscar el usuario por email
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail());
        
        if (usuario == null) {
            return new ResponseEntity<>("Usuario no encontrado.", HttpStatus.NOT_FOUND);
        }
        
        // Verificar la contraseña
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
