package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.User;
import ml.pic.tech.app.alimentation.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public User lecture(Long id) {

        return userRepository.findById(id).get();
    }

    public void suppression(Long id) {

        userRepository.deleteById(id);
    }

    public List<User> liste() {

        return userRepository.findAll();
    }
}
