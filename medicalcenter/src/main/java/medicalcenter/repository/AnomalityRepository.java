package medicalcenter.repository;

import medicalcenter.model.Activity;
import medicalcenter.model.Anomality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnomalityRepository extends JpaRepository<Anomality,Integer> {
}
