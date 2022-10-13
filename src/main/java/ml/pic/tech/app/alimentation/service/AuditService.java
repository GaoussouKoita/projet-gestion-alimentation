package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.Audit;
import ml.pic.tech.app.alimentation.repository.AuditRepository;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return repository.findByUtilisateurId(id, PageRequest.of(page, 10, Sort.by("date").descending()));
    }
}
