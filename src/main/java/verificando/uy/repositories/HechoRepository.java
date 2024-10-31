package verificando.uy.repositories;

import verificando.uy.model.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

public interface HechoRepository extends JpaRepository<Hecho, Long>  {
    public Optional<Hecho> findById(Long id);
    public List<Hecho> getHechos();
}
