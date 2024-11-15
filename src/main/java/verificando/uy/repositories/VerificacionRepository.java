package verificando.uy.repositories;

import org.springframework.stereotype.Repository;
import verificando.uy.model.Verificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface VerificacionRepository  extends JpaRepository<Verificacion, Long> {
    Optional<Verificacion> findById(long id);

}
