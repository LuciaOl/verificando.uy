package verificando.uy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import verificando.uy.model.Notificacion;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
}

