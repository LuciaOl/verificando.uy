package verificando.uy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import verificando.uy.model.DonationConfig;

public interface DonationConfigRepository extends JpaRepository<DonationConfig, Long> {
}