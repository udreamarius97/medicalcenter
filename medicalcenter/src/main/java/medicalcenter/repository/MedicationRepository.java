package medicalcenter.repository;

import medicalcenter.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Integer> {

    Medication findByName(String name);

    Boolean existsByName(String Name);
}
