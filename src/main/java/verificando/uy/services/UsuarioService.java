package verificando.uy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import verificando.uy.enums.Role;
import verificando.uy.model.Usuario;
import verificando.uy.repositories.UsuarioRepository;
import verificando.uy.utils.Utils;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final Utils utils;
    
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, Utils utils) {
        this.usuarioRepository = usuarioRepository;
        this.utils = utils;
    }

    public void crearAdminPorDefecto() {
        String emailAdmin = "admin@example.com";
        if (!emailRegistrado(emailAdmin)) {
            Usuario admin = new Usuario();
            admin.setFullName("Admin");
            admin.setEmail(emailAdmin);
            admin.setPassword(utils.hashPassword("admin")); 
            admin.setRole(Role.ADMIN);  // Asignar el rol usando el enum Role

            usuarioRepository.save(admin);
            System.out.println("Usuario admin creado con email: " + emailAdmin);
        }
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
    
    public void actualizarUsuario(Long id, Usuario usuario) {
        usuarioRepository.save(usuario);
    }
    
    public boolean eliminarUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario obtenerUsuarioPorCedula(String cedula) {
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
                usuario.setRole(usuarioActualizado.getRole());  // Usar el enum Role

                return usuarioRepository.save(usuario);
            } else {
                throw new RuntimeException("Usuario no encontrado por ID.");
            }
        } else {
            throw new RuntimeException("Usuario no encontrado por cédula.");
        }
    }

    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public boolean emailRegistrado(String email) {
        return usuarioRepository.findByEmail(email) != null;
    }

    public boolean tieneContrasena(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        return usuario != null && usuario.getPassword() != null && !usuario.getPassword().isEmpty();
    }

    public Usuario actualizarUsuarioSiNoTieneContrasena(String cedula, Usuario usuarioActualizado) {
        String cedulaFormato = "uy-ci-" + cedula;
        Usuario usuarioPorCedula = usuarioRepository.findByCedula(cedulaFormato);
    
        if (usuarioPorCedula != null && (usuarioPorCedula.getPassword() == null || usuarioPorCedula.getPassword().isEmpty())) {
            return actualizarUsuario_Cedula(cedula, usuarioActualizado);
        } else {
            throw new RuntimeException("El usuario ya tiene una contraseña establecida o no fue encontrado.");
        }
    }

    public Usuario modificarRolUsuario(String email, Role nuevoRol) {  // Usar Role como tipo
        Usuario usuario = usuarioRepository.findByEmail(email);
        
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado con el email proporcionado.");
        }
        
        usuario.setRole(nuevoRol);  // Usar el enum directamente
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }
}
