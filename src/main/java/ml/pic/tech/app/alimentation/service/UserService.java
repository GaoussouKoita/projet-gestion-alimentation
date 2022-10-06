package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.User;
import ml.pic.tech.app.alimentation.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public User findCurrentUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        User user = userRepository.findByLogin(login);
        return user;
    }

    public List<User> liste() {

        return userRepository.findAll(Sort.by("nom").ascending());
    }

    public Page<User> liste(int page) {

        return userRepository.findAll(PageRequest.of(page, 9,
                Sort.by("prenom").ascending().and(Sort.by("nom").ascending())));
    }
}
