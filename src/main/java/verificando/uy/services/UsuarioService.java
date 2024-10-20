package verificando.uy.services;

import org.springframework.stereotype.Service;
import verificando.uy.model.Usuario;

@Service
public class UsuarioService {

    public Usuario obtenerUsuario(Long id) {
        // Lógica para obtener un usuario de la base de datos
        return new Usuario();
    }

    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        // Lógica para actualizar un usuario en la base de datos
        return usuario;
    }

    public void eliminarUsuario(Long id) {
        // Lógica para eliminar un usuario de la base de datos
    }
}

