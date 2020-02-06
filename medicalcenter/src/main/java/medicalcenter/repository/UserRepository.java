package medicalcenter.repository;

import medicalcenter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

   Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    List<User> findAll();
    void deleteByEmail(String email);

    User findUserByPatiensContains(int idPacient);
    //Optional<User> findById(int id);

    //@Query("from medicalcenter.model.User u where u.role=:role")
    //List<User> findAllByRole(@Param("role") String role);

}
