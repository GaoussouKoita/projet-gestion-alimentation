package ml.pic.tech.app.alimentation.securite.service;

import ml.pic.tech.app.alimentation.securite.entity.Role;
import ml.pic.tech.app.alimentation.securite.entity.Utilisateur;
import ml.pic.tech.app.alimentation.securite.repository.RoleRepository;
import ml.pic.tech.app.alimentation.securite.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AccountService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    public PasswordEncoder passwordEncoder;


    public void addUtilisateur(Utilisateur utilisateur) {
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        utilisateurRepository.save(utilisateur);
    }

    public Utilisateur findByLogin(String login) {
        return utilisateurRepository.findByLogin(login);
    }

    public void suppression(Long id) {

        utilisateurRepository.deleteById(id);
    }
    public Utilisateur lecture(Long id) {

       return utilisateurRepository.findById(id).get();
    }

    public List<Utilisateur> utilisateurList() {

        return utilisateurRepository.findAll(Sort.by("nom").ascending());
    }

    public Page<Utilisateur> utilisateurListPage(int page) {

        return utilisateurRepository.findAll(PageRequest.of(page, 9,
                Sort.by("prenom").ascending().and(Sort.by("nom").ascending())));
    }

    public List<Role> roleList() {
        return roleRepository.findAll();
    }


    public Utilisateur currentUtilisateur() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        Utilisateur utilisateur = utilisateurRepository.findByLogin(login);
        return utilisateur;
    }

    public void updatePassword(String email, String newPassword) {
        String newPasswordEncode = passwordEncoder.encode(newPassword);

        utilisateurRepository.updateUtilisateurByLogin(email, newPasswordEncode);
    }

    public boolean passwordEncodeVerifie(String oldPassword, String password) {
        return passwordEncoder.matches(oldPassword, password);
    }
}
