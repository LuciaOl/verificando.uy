package verificando.uy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import verificando.uy.model.Checker;

@Repository
public interface CheckerRepository extends JpaRepository<Checker, Long> {
    // Métodos personalizados si es necesario
}