package ml.pic.tech.app.alimentation.securite.service;

import ml.pic.tech.app.alimentation.securite.entity.Role;
import ml.pic.tech.app.alimentation.securite.entity.Utilisateur;
import ml.pic.tech.app.alimentation.securite.repository.RoleRepository;
import ml.pic.tech.app.alimentation.securite.repository.UtilisateurRepository;
import ml.pic.tech.app.alimentation.utils.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class AccountService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    public PasswordEncoder passwordEncoder;


    public Utilisateur addUtilisateur(Utilisateur utilisateur) {
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur findByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
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

        return utilisateurRepository.findAll(PageRequest.of(page, Constante.NBRE_PAR_PAGE,
                Sort.by("prenom").ascending().and(Sort.by("nom").ascending())));
    }
    public Page<Utilisateur> utilisateurNomPage(String nom, int page) {

        return utilisateurRepository.findByNomContaining(nom, PageRequest.of(page, Constante.NBRE_PAR_PAGE,
                Sort.by("prenom").ascending().and(Sort.by("nom").ascending())));
    }

    public void addRole(Role role){
        roleRepository.save(role);
    }

    public List<Role> roleList() {
        return roleRepository.findAll();
    }


    public Utilisateur currentUtilisateur() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        Utilisateur utilisateur = utilisateurRepository.findByEmail(login);
        return utilisateur;
    }

    public void updatePassword(String email, String newPassword) {
        String newPasswordEncode = passwordEncoder.encode(newPassword);

        utilisateurRepository.updateUtilisateurByLogin(email, newPasswordEncode);
    }

    public boolean passwordEncodeVerifie(String oldPassword, String password) {
        return passwordEncoder.matches(oldPassword, password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = findByEmail(username);
        List<Role> roles = utilisateur.getRoles();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(r -> {
            authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
        });
        return new User(utilisateur.getEmail(), utilisateur.getPassword(), authorities);
    }
}
