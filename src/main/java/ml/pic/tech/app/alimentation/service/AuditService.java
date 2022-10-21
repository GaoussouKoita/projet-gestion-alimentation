package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.Audit;
import ml.pic.tech.app.alimentation.repository.AuditRepository;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.utils.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuditRepository repository;

    public void ajoutAudit(Audit audit){
        audit.setUtilisateur(accountService.currentUtilisateur());
        repository.save(audit);

    }

    public Page<Audit> auditList(Long id, int page){
        return repository.findByUtilisateurId(id, PageRequest.of(page, Constante.NBRE_PAR_PAGE, Sort.by("date").descending().
                and(Sort.by("heure").ascending())));
    }
}
