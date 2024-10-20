package verificando.uy.repositories;

import verificando.uy.model.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import verificando.uy.model.Hecho;

import java.util.List;
import java.util.Optional;


public interface CitizenRepository extends JpaRepository<Citizen, Long> {

    List<Citizen> findBySubscriptionsContaining(Hecho hecho);

}
