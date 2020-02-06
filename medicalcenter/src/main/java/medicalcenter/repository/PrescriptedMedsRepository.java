package medicalcenter.repository;

import medicalcenter.model.PrescriptedMeds;
import medicalcenter.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptedMedsRepository extends JpaRepository<PrescriptedMeds, Integer> {

}
