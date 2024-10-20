package verificando.uy.controllers;

import verificando.uy.model.Usuario;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    // Este mapa simula una base de datos para fines de demostración
    private Map<Long, Usuario> usuarios = new HashMap<>();

    // Servicios comunes para todos los usuarios
    // Estos métodos serán usados o sobreescritos por los controladores hijos

    @GetMapping("/{id}")
    public Usuario obtenerUsuario(@PathVariable Long id) {
        // Lógica para obtener un usuario por ID
        return usuarios.get(id); // Devolver el usuario correspondiente
    }

    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        // Lógica para actualizar un usuario
        if (usuarios.containsKey(id)) {
            usuarios.put(id, usuario); // Actualizar el usuario en el "almacenamiento"
            return usuario;
        }
        return null; // O lanzar una excepción si el usuario no se encuentra
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        // Lógica para eliminar un usuario
        usuarios.remove(id); // Eliminar el usuario del "almacenamiento"
    }

    // Método para agregar usuarios (solo para demostración)
    @PostMapping
    public Usuario agregarUsuario(@RequestBody Usuario usuario) {
        usuarios.put(Long.valueOf(usuario.getUserID()), usuario); // Simular la adición del usuario
        return usuario;
    }
}
