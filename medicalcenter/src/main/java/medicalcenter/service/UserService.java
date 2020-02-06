package medicalcenter.service;

import medicalcenter.repository.UserRepository;
import medicalcenter.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void deleteByEmail(String email){
        User u=userRepository.findByUsernameOrEmail(email,email).orElseThrow(() ->
                new UsernameNotFoundException("User not found with username or email : " + email));
        userRepository.delete(u);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User save(User user){
       return userRepository.save(user);
    }

    public User findByUsernameOrEmail(String usernameOrEmail){
        return userRepository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail).orElseThrow(() ->
                new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
        );
    }

    public User findById(int id){
        return userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("User not found")
        );
    }

   /* public List<User> getAllByRole(String role){
        return userRepository.findAllByRole(role);
    }*/

}
