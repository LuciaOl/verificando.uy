package verificando.uy.repositories;

import verificando.uy.model.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HechoRepository extends JpaRepository<Hecho, Long> {
    Optional<Hecho> findById(Long id);
}
