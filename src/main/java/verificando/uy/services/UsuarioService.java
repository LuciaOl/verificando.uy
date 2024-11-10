package verificando.uy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import verificando.uy.model.Usuario;
import verificando.uy.repositories.UsuarioRepository;

import java.util.Optional;


// Si un usuario no tiene password es porque solo tiene login con iduy

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario obtenerUsuarioPorCedula(String cedula) {
        // Agregar prefijo antes de buscar en la base de datos
        String cedulaFormato = "uy-ci-" + cedula;
        return usuarioRepository.findByCedula(cedulaFormato);
    }

    public Usuario actualizarUsuario_Cedula(String cedula, Usuario usuarioActualizado) {
        String cedulaFormato = "uy-ci-" + cedula;
        Usuario usuarioPorCedula = usuarioRepository.findByCedula(cedulaFormato);
        
        if (usuarioPorCedula != null) {
            Long id = usuarioPorCedula.getid();

            Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
            if (usuarioExistente.isPresent()) {
                Usuario usuario = usuarioExistente.get();

                usuario.setFullName(usuarioActualizado.getFullName());
                usuario.setEmail(usuarioActualizado.getEmail());
                usuario.setId_token(usuarioActualizado.getId_token());
                usuario.setRefresh_token(usuarioActualizado.getRefresh_token());
                usuario.setPassword(usuarioActualizado.getPassword()); // Solo si es necesario actualizar la contraseña

                return usuarioRepository.save(usuario);
            } else {
                throw new RuntimeException("Usuario no encontrado por ID.");
            }
        } else {
            throw new RuntimeException("Usuario no encontrado por cédula.");
        }
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public boolean emailRegistrado(String email) {
        return usuarioRepository.findByEmail(email) != null;
    }
}
