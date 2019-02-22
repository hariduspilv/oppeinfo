package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.Form;
import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.FormStatus;
import ee.hitsa.ois.enums.FormType;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.EnumUtil;
import ee.hitsa.ois.util.JpaNativeQueryBuilder;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.ControllerErrorHandler;
import ee.hitsa.ois.web.commandobject.form.FormDefectedForm;
import ee.hitsa.ois.web.commandobject.form.FormForm;
import ee.hitsa.ois.web.commandobject.form.FormSearchForm;
import ee.hitsa.ois.web.dto.form.FormSearchDto;

@Transactional
@Service
public class FormService {

    private static final Set<String> SUPPLEMENT_TYPES = EnumUtil.toNameSet(
            FormType.LOPUBLANKETT_HIN, FormType.LOPUBLANKETT_HINL);
    private static final Set<String> SCHOOL_CODE_TYPES = EnumUtil.toNameSet(
            FormType.LOPUBLANKETT_E, FormType.LOPUBLANKETT_L, FormType.LOPUBLANKETT_M, FormType.LOPUBLANKETT_O);
    private static final Sort GROUP_SORT = new Sort(new Sort.Order("type_code"),
            new Sort.Order("code"), new Sort.Order(Direction.ASC, "numeral"), new Sort.Order("status_code"),
            new Sort.Order("defect_reason"));

    @Autowired
    private EntityManager em;
    @Autowired
    private XlsService xlsService;
    @Autowired
    private FormDefectedService defectedService;

    public List<FormSearchDto> search(HoisUserDetails user, FormSearchForm criteria) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from form f").sort(GROUP_SORT);

        qb.requiredCriteria("f.school_id = :school", "school", user.getSchoolId());
        qb.optionalCriteria("f.type_code = :type", "type", criteria.getType());
        qb.optionalCriteria("f.status_code = :status", "status", criteria.getStatus());
        qb.optionalCriteria("f.numeral >= :from", "from", criteria.getFrom());
        qb.optionalCriteria("f.numeral <= :thru", "thru", criteria.getThru());
        
        List<?> data = qb.select("type_code, status_code, code, defect_reason, numeral, full_code", em)
            .getResultList();
        List<FormSearchDto> result = new ArrayList<>();
        FormSearchDto dto = null;
        long lastNumeral = 0;
        long counter = 0;
        for (Object r : data) {
            String typeCode = resultAsString(r, 0);
            String statusCode = resultAsString(r, 1);
            String code = resultAsString(r, 2);
            String defectReason = resultAsString(r, 3);
            Long numeral = resultAsLong(r, 4);
            String fullCode = resultAsString(r, 5);
            if (dto == null || !Objects.equals(typeCode, dto.getType()) || !Objects.equals(statusCode, dto.getStatus())
                    || !Objects.equals(code, dto.getCode()) || !Objects.equals(defectReason, dto.getDefectReason())
                    || numeral.longValue() != (lastNumeral + 1)) {
                if (dto != null) {
                    result.add(dto);
                }
                dto = new FormSearchDto();
                dto.setType(typeCode);
                dto.setStatus(statusCode);
                dto.setCode(code);
                dto.setDefectReason(defectReason);
                dto.setFrom(numeral);
                dto.setFromFullCode(fullCode);
                counter = 1;
            }
            dto.setThru(numeral);
            dto.setThruFullCode(fullCode);
            dto.setCount(Long.valueOf(counter++));
            lastNumeral = numeral.longValue();
        }
        if (dto != null) {
            result.add(dto);
        }
        return result;
    }
    
    public byte[] excel(HoisUserDetails user, FormSearchForm criteria) {
        return xlsService.generate("forms.xls", Collections.singletonMap("forms", search(user, criteria)));
    }
    
    public void create(HoisUserDetails user, FormForm formForm) {
        long from = Long.parseLong(formForm.getFrom());
        long thru = Long.parseLong(formForm.getThru());
        
        checkExisting(user, formForm.getType(), from, thru);
        
        School school = em.getReference(School.class, user.getSchoolId());
        Classifier type = em.getReference(Classifier.class, formForm.getType());
        Classifier status = em.getReference(Classifier.class, FormStatus.LOPUBLANKETT_STAATUS_K.name());
        String code;
        if (SUPPLEMENT_TYPES.contains(formForm.getType())) {
            code = null;
        } else {
            code = type.getValue();
            if (SCHOOL_CODE_TYPES.contains(formForm.getType())) {
                String schoolCode = school.getEhisSchool().getExtraval2();
                if (schoolCode == null) {
                    throw new ValidationFailedException("form.error.noSchoolCode");
                }
                code = code + schoolCode;
            }
        }
        String format = "%s%0" + formForm.getThru().length() + "d";
        for (long i = from; i <= thru; i++) {
            Form form = new Form();
            form.setSchool(school);
            form.setType(type);
            form.setStatus(status);
            form.setCode(code);
            form.setNumeral(Long.valueOf(i));
            form.setFullCode(String.format(format, code == null ? "" : code, Long.valueOf(i)));
            EntityUtil.save(form, em);
        }
    }
    
    private void checkExisting(HoisUserDetails user, String typeCode, long from, long thru) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from form f").sort("f.numeral");
        if (SUPPLEMENT_TYPES.contains(typeCode)) {
            qb.requiredCriteria("f.school_id = :school", "school", user.getSchoolId());
        }
        qb.requiredCriteria("f.type_code = :type", "type", typeCode);
        qb.requiredCriteria("f.numeral >= :from", "from", Long.valueOf(from));
        qb.requiredCriteria("f.numeral <= :thru", "thru", Long.valueOf(thru));
        List<?> result = qb.select("f.full_code", em)
                .getResultList();
        if (!result.isEmpty()) {
            throw new ValidationFailedException(Collections.singletonList(
                    new ControllerErrorHandler.ErrorInfo.Error("form.error.existing", 
                            Collections.singletonMap("nrs", result.stream()
                                    .map(r -> resultAsString(r, 0)).collect(Collectors.joining(", "))))));
        }
    }

    public void delete(HoisUserDetails user, FormForm formForm) {
        long from = Long.parseLong(formForm.getFrom());
        long thru = Long.parseLong(formForm.getThru());
        
        checkUsed(user, formForm.getType(), from, thru);
        
        Query query = em.createNativeQuery("delete from form f"
                + " where f.school_id = ?1 and f.type_code = ?2"
                + " and f.numeral between ?3 and ?4")
            .setParameter(1, user.getSchoolId())
            .setParameter(2, formForm.getType())
            .setParameter(3, Long.valueOf(from))
            .setParameter(4, Long.valueOf(thru));
        if (query.executeUpdate() == 0) {
            throw new ValidationFailedException("form.error.notFound");
        }
    }

    private void checkUsed(HoisUserDetails user, String typeCode, long from, long thru) {
        JpaNativeQueryBuilder qb = new JpaNativeQueryBuilder("from form f");
        qb.requiredCriteria("f.school_id = :school", "school", user.getSchoolId());
        qb.requiredCriteria("f.type_code = :type", "type", typeCode);
        qb.requiredCriteria("f.numeral >= :from", "from", Long.valueOf(from));
        qb.requiredCriteria("f.numeral <= :thru", "thru", Long.valueOf(thru));
        qb.requiredCriteria("f.status_code != :status", "status", FormStatus.LOPUBLANKETT_STAATUS_K.name());
        List<?> result = qb.select("1", em)
                .setMaxResults(1)
                .getResultList();
        if (!result.isEmpty()) {
            throw new ValidationFailedException("form.error.used");
        }
    }

    public void defected(HoisUserDetails user, FormDefectedForm form) {
        List<Form> forms = em.createQuery("select f from Form f"
                + " where f.school.id = ?1 and f.type.code = ?2"
                + " and f.numeral between ?3 and ?4", Form.class)
            .setParameter(1, user.getSchoolId())
            .setParameter(2, form.getType())
            .setParameter(3, Long.valueOf(Long.parseLong(form.getFrom())))
            .setParameter(4, Long.valueOf(Long.parseLong(form.getThru())))
            .getResultList();
        if (forms.isEmpty()) {
            throw new ValidationFailedException("form.error.notFound");
        }
        defectedService.setDefected(user, forms, form.getReason());
    }
    
}
