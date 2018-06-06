package ee.hitsa.ois.service;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Form;
import ee.hitsa.ois.domain.diploma.Diploma;
import ee.hitsa.ois.domain.diploma.DiplomaSupplement;
import ee.hitsa.ois.enums.DocumentStatus;
import ee.hitsa.ois.enums.FormStatus;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.ValidationFailedException;

@Transactional
@Service
public class FormDefectedService {
    
    @Autowired
    private EntityManager em;
    
    private Classifier formStatus;
    private Classifier documentStatus;
    private LocalDate now;
    private String username;
    private String reason;
    
    public void setDefected(HoisUserDetails user, List<Form> forms, String reason) {
        this.formStatus = em.getReference(Classifier.class, FormStatus.LOPUBLANKETT_STAATUS_R.name());
        this.documentStatus = em.getReference(Classifier.class, DocumentStatus.LOPUDOK_STAATUS_K.name());
        this.now = LocalDate.now();
        this.username = user.getUsername();
        this.reason = reason;
        for (Form form : forms) {
            if (ClassifierUtil.equals(FormStatus.LOPUBLANKETT_STAATUS_R, form.getStatus())) {
                throw new ValidationFailedException("form.error.defected");
            }
            if (ClassifierUtil.equals(FormStatus.LOPUBLANKETT_STAATUS_T, form.getStatus())) {
                setRelatedDefected(form);
            }
            setDefected(form);
        }
    }

    private void setDefected(Form form) {
        if (ClassifierUtil.equals(FormStatus.LOPUBLANKETT_STAATUS_R, form.getStatus())) {
            return;
        }
        form.setStatus(formStatus);
        form.setDefectReason(reason);
        form.setDefected(now);
        form.setDefectedBy(username);
        EntityUtil.save(form, em);
    }

    private void setRelatedDefected(Form form) {
        em.createQuery("select d from Diploma d where d.form = ?1", Diploma.class)
                .setParameter(1, form)
                .getResultList()
                .stream().forEach(this::setDefected);
        em.createQuery("select dsf.diplomaSupplement"
                + " from DiplomaSupplementForm dsf where dsf.form = ?1", DiplomaSupplement.class)
                .setParameter(1, form)
                .getResultList()
                .stream().forEach(this::setDefected);
    }

    private void setDefected(Diploma diploma) {
        diploma.setStatus(documentStatus);
        EntityUtil.save(diploma, em);
        em.createQuery("select ds from DiplomaSupplement ds where ds.diploma = ?1", DiplomaSupplement.class)
                .setParameter(1, diploma)
                .getResultList()
                .stream().forEach(this::setDefected);
    }

    private void setDefected(DiplomaSupplement diplomaSupplement) {
        diplomaSupplement.setStatus(documentStatus);
        EntityUtil.save(diplomaSupplement, em);
        em.createQuery("select dsf.form from DiplomaSupplementForm dsf"
                + " where dsf.diplomaSupplement = ?1", Form.class)
                .setParameter(1, diplomaSupplement)
                .getResultList()
                .stream().forEach(this::setDefected);
    }

}
