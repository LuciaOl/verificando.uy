package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    // Aquí puedes agregar métodos personalizados si es necesario
    Usuario findByEmail(String email); // Ejemplo de un método personalizado
}