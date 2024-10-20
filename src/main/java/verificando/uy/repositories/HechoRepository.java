package verificando.uy.repositories;

import verificando.uy.model.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HechoRepository extends JpaRepository<Hecho, Long>  {
    public Optional<Hecho> findById(String id);
}
