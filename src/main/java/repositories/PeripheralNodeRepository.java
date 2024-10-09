package repositories;

import model.PeripheralNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

public interface PeripheralNodeRepository extends JpaRepository<PeripheralNode, Long> {
    Optional<PeripheralNode> findByUrl(String url);
}
