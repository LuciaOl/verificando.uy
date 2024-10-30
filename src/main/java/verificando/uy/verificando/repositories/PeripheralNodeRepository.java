package verificando.uy.verificando.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import verificando.uy.verificando.model.PeripheralNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface PeripheralNodeRepository extends JpaRepository<PeripheralNode, Long> {
    Optional<PeripheralNode> findByUrl(String url);
}
