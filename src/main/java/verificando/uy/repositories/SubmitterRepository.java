package verificando.uy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import verificando.uy.model.Submitter;

@Repository
public interface SubmitterRepository extends JpaRepository<Submitter, Long> {
}
