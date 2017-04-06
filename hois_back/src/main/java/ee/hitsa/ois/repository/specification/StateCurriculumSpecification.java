package ee.hitsa.ois.repository.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.ClassifierConnect;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.SearchUtil;
import ee.hitsa.ois.web.commandobject.StateCurriculumSearchCommand;

public class StateCurriculumSpecification implements Specification<StateCurriculum> {

	StateCurriculumSearchCommand criteria;

	public StateCurriculumSpecification(StateCurriculumSearchCommand stateCurriculumSearchCommand) {
		this.criteria = stateCurriculumSearchCommand;
	}

	@Override
	public Predicate toPredicate(Root<StateCurriculum> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> filters = new ArrayList<>();

		String nameField = criteria.getLang() == Language.EN ? "nameEn" : "nameEt";
		SearchUtil.propertyContains(() -> root.get(nameField), cb, criteria.getName(), filters::add);

		if(!CollectionUtils.isEmpty(criteria.getStatus())) {
			filters.add(root.get("status").get("code").in(criteria.getStatus()));
		}
		if(criteria.getValidFrom() != null) {
			filters.add(cb.greaterThanOrEqualTo(root.get("validFrom"), criteria.getValidFrom()));
		}
		if(criteria.getValidThru() != null) {
			filters.add(cb.lessThanOrEqualTo(root.get("validThru"), criteria.getValidThru()));
		}
		
        if(!CollectionUtils.isEmpty(criteria.getIscedClass())) {
            filters.add(root.get("iscedClass").get("code").in(criteria.getIscedClass()));
        }
        
        if(!CollectionUtils.isEmpty(criteria.getIscedSuun())) {
            Subquery<String> targetQuery = query.subquery(String.class);
            Root<ClassifierConnect> targetRoot = targetQuery.from(ClassifierConnect.class);
            targetQuery = targetQuery.select(targetRoot.get("classifier").get("code")).where(targetRoot.get("connectClassifier").get("code").in(criteria.getIscedSuun()));
            filters.add(root.get("iscedClass").get("code").in(targetQuery));
        }
        
        if(criteria.getIscedVald() != null) {
            // get ISCED_SUUN classifier from isced_class
            Subquery<String> getIscedSuun = query.subquery(String.class);
            Root<ClassifierConnect> iscedSuun = getIscedSuun.from(ClassifierConnect.class);
            getIscedSuun = getIscedSuun.select(iscedSuun.get("classifier")
                    .get("code")).where(cb.equal(iscedSuun.get("connectClassifier").get("code"), criteria.getIscedVald()));

            // get ISCED_RYHM classifier from ISCED_SUUN
            Subquery<String> getIscedRyhm = getIscedSuun.subquery(String.class);
            Root<ClassifierConnect> iscedRyhm = getIscedRyhm.from(ClassifierConnect.class);
            getIscedRyhm = getIscedRyhm.select(iscedRyhm.get("classifier")
                    .get("code")).where(iscedRyhm.get("connectClassifier").get("code").in(getIscedSuun));
            filters.add(root.get("iscedClass").get("code").in(getIscedRyhm));
        }
		return cb.and(filters.toArray(new Predicate[filters.size()]));
	}
}
