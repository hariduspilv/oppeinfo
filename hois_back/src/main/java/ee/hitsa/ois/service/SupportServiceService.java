package ee.hitsa.ois.service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.OisFile;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.domain.student.StudentSupportService;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.student.StudentSupportServiceForm;
import ee.hitsa.ois.web.dto.StudentSupportServiceDto;

@Transactional
@Service
public class SupportServiceService {
    
    @Autowired
    private EntityManager em;
    @Autowired
    private ClassifierRepository classifierRepository;

    public StudentSupportServiceDto get(StudentSupportService service) {
        return StudentSupportServiceDto.of(service);
    }

    public StudentSupportService create(Student student, StudentSupportServiceForm form) {
        StudentSupportService entity = new StudentSupportService();
        entity.setStudent(student);
        return update(entity, form);
    }

    public StudentSupportService update(StudentSupportService service, StudentSupportServiceForm form) {
        EntityUtil.bindToEntity(form, service, classifierRepository, "file", "entrySubmitter");
        OisFile file = service.getOisFile();
        if (form.getFile() != null) {
            if (form.getFile().getFdata() != null) {
                service.setOisFile(EntityUtil.bindToEntity(form.getFile(), new OisFile()));
                if (file != null) {
                    EntityUtil.deleteEntity(file, em);
                }
            }
            // otherwise it is the same file
        } else {
            service.setOisFile(null);
            if (file != null) {
                EntityUtil.deleteEntity(file, em);
            }
        }
        return EntityUtil.save(service, em);
    }

    public void delete(StudentSupportService service) {
        EntityUtil.deleteEntity(service, em);
    }

}
