package verificando.uy.controllers;

import verificando.uy.model.Usuario;
import verificando.uy.repositories.UsuarioRepository;
import verificando.uy.utils.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")

public class UsuarioController {


    @Autowired
    private UsuarioRepository usuarioRepository;
    
       
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


   @PostMapping("/crear-usuario")
   public Usuario crearUsuarioConAtributos(
           @RequestParam String nombre,
           @RequestParam String email,
           @RequestParam String password) {

       Usuario usuario = new Usuario();
       usuario.setFullName(nombre);
       usuario.setEmail(email);

       // Hashear la contraseña antes de asignarla
       String hashedPassword = utils.hashPassword(password);
       usuario.setPassword(hashedPassword);

       return usuarioRepository.save(usuario);
   }


}
