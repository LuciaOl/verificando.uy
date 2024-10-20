package repositories;

import model.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CitizenRepository extends JpaRepository<Citizen, Long> {

    List<Citizen> findSuscriptores(String factID);
}
