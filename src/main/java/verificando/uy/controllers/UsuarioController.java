package verificando.uy.controllers;

import verificando.uy.dtos.DtCrearUsuarioRequest;
import verificando.uy.dtos.DtLoginRequest;
import verificando.uy.dtos.DtLoginResponse;
import verificando.uy.dtos.DtUsuario;
import verificando.uy.enums.Role;
import verificando.uy.model.Usuario;
import verificando.uy.services.UsuarioService;
import verificando.uy.utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.couchbase.client.core.deps.com.fasterxml.jackson.core.JsonProcessingException;
import com.couchbase.client.core.deps.com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private Utils utils;
       
    @GetMapping("/{gubuy_cedula}")
    public Usuario obtenerUsuarioGubuy(@PathVariable String gubuy_cedula) {
        return usuarioService.obtenerUsuarioPorCedula(gubuy_cedula);
    }

    @PostMapping("/guardar")
    public Usuario guardarUsuario(@RequestBody Usuario usuario) {
        if (usuario.getRole() == null) {
            throw new RuntimeException("Rol no válido para el usuario");
        }
        return usuarioService.crearUsuario(usuario); 
    }

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
   public ResponseEntity<String> crearUsuarioConAtributos(@RequestBody DtCrearUsuarioRequest crearUsuarioRequest) {
       if (usuarioService.emailRegistrado(crearUsuarioRequest.getEmail())) {
           return ResponseEntity.status(HttpStatus.CONFLICT).body("El email ya está registrado.");
       }
       
       Usuario usuario = new Usuario();
       usuario.setFullName(crearUsuarioRequest.getNombre());
       usuario.setEmail(crearUsuarioRequest.getEmail());
       usuario.setRole(Role.CITIZEN);  // Asignar el rol directamente como Role.CITIZEN
   
       String hashedPassword = utils.hashPassword(crearUsuarioRequest.getPassword());
       usuario.setPassword(hashedPassword);
   
       usuarioService.crearUsuario(usuario);
       return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado exitosamente.");
   }
   
   @PostMapping("/login")
   public ResponseEntity<String> loginUsuario(@RequestBody DtLoginRequest loginRequest) {
       Usuario usuario = usuarioService.obtenerUsuarioPorEmail(loginRequest.getEmail());
       
       if (usuario == null) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
       }
   
       if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
           return ResponseEntity.status(HttpStatus.FORBIDDEN).body("El usuario no tiene una contraseña configurada.");
       }
       
       boolean isPasswordMatch = utils.verifyPassword(loginRequest.getPassword(), usuario.getPassword());
       
       if (!isPasswordMatch) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta.");
       }
       
       DtUsuario usuarioDTO = new DtUsuario(
           usuario.getid(),
           usuario.getFullName(),
           usuario.getEmail(),
           usuario.getRole()
       );
       
       DtLoginResponse response = new DtLoginResponse("Login exitoso.", usuarioDTO);
       
       try {
           String jsonResponse = new ObjectMapper().writeValueAsString(response);
           return ResponseEntity.ok(jsonResponse);
       } catch (JsonProcessingException e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la respuesta de login.");
       }
   }

    @GetMapping("/listar-usuarios")
    public ResponseEntity<String> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();

        List<DtUsuario> dtUsuarios = usuarios.stream()
            .map(usuario -> new DtUsuario(usuario.getid(), usuario.getFullName(), usuario.getEmail(),usuario.getRole()))
            .collect(Collectors.toList());

        try {
            String jsonResponse = new ObjectMapper().writeValueAsString(dtUsuarios);
            return ResponseEntity.ok(jsonResponse);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la lista de usuarios.");
        }
    }

    // Leer Usuario (Read)
    @GetMapping("/{id}")
    public ResponseEntity<String> obtenerUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario != null) {
            return ResponseEntity.ok("Usuario: " + usuario.getFullName() + ", Email: " + usuario.getEmail() + ", Rol: " + usuario.getRole());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
    }

    // Actualizar Usuario (Update)
    @PutMapping("/actualizar-usuario/{id}")
    public ResponseEntity<String> actualizarUsuario(@PathVariable Long id, @RequestParam String nombre, @RequestParam String email, @RequestParam Role role) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }

        usuario.setFullName(nombre);
        usuario.setEmail(email);
        usuario.setRole(role);  // Asignar role directamente como un enum
        usuarioService.actualizarUsuario(id, usuario);

        return ResponseEntity.status(HttpStatus.OK).body("Usuario actualizado exitosamente.");
    }

    // Eliminar Usuario (Delete)
    @DeleteMapping("/eliminar-usuario/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        if (usuarioService.eliminarUsuario(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Usuario eliminado exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
    }
    
    @PutMapping("/modificar-rol")
    public ResponseEntity<String> modificarRolUsuario(@RequestParam String email, @RequestParam Role nuevoRol) {
        try {
            usuarioService.modificarRolUsuario(email, nuevoRol);
            return ResponseEntity.status(HttpStatus.OK).body("Rol del usuario modificado exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
   
}
