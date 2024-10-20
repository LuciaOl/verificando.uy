package verificando.uy.repositories;

import verificando.uy.model.Verificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificacionRepository  extends JpaRepository<Verificacion, Long> {
}
