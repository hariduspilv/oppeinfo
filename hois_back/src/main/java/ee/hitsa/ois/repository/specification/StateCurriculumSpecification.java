package ee.hitsa.ois.repository.specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.util.SearchUtil;
import ee.hitsa.ois.web.commandobject.StateCurriculumSearchCommand;

public class StateCurriculumSpecification implements Specification<StateCurriculum> {

	StateCurriculumSearchCommand searchCommand;
	ClassifierRepository classifierRepository;

	public StateCurriculumSpecification(StateCurriculumSearchCommand stateCurriculumSearchCommand, ClassifierRepository classifierRepository) {
		this.searchCommand = stateCurriculumSearchCommand;
		this.classifierRepository = classifierRepository;
	}

	@Override
	public Predicate toPredicate(Root<StateCurriculum> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> filters = new ArrayList<>();

		String nameField = searchCommand.getLang() == Language.EN ? "nameEn" : "nameEt";
		SearchUtil.propertyContains(() -> root.get(nameField), cb, searchCommand.getName(), filters::add);

		if(!CollectionUtils.isEmpty(searchCommand.getStatus())) {
			filters.add(root.get("status").get("code").in(searchCommand.getStatus()));
		}
		if(searchCommand.getValidFrom() != null) {
			filters.add(cb.greaterThanOrEqualTo(root.get("validFrom"), searchCommand.getValidFrom()));
		}
		if(searchCommand.getValidThru() != null) {
			filters.add(cb.lessThanOrEqualTo(root.get("validThru"), searchCommand.getValidThru()));
		}
		
		if(!CollectionUtils.isEmpty(searchCommand.getIscedClass()) || 
		        !CollectionUtils.isEmpty(searchCommand.getIscedSuun()) || 
		        searchCommand.getIscedVald() != null) {
	        List<String> icsedRyhms = new ArrayList<>();
	        
	        if(!CollectionUtils.isEmpty(searchCommand.getIscedClass())) {
	            icsedRyhms.addAll(searchCommand.getIscedClass());
	        } else if(!CollectionUtils.isEmpty(searchCommand.getIscedSuun())) {
	            icsedRyhms.addAll(classifierRepository.findChildrenByMainClassifier(searchCommand.getIscedSuun(), 
	                    MainClassCode.ISCED_RYHM.name()));
	        } else if(searchCommand.getIscedVald() != null) {
	            List<String> iscedSuuns = classifierRepository.findChildrenByMainClassifier(
	                    Arrays.asList(searchCommand.getIscedVald()), MainClassCode.ISCED_SUUN.name());
	              if(!CollectionUtils.isEmpty(iscedSuuns)) {
	                   icsedRyhms.addAll(classifierRepository.findChildrenByMainClassifier(iscedSuuns, 
	                           MainClassCode.ISCED_RYHM.name()));
	              }
	        }
	        /*
	         * There may be situations, when user selects isced_vald or isced_suun
	         * and no isced_ryhm corresponds for them.
	         * In this case search result must be empty.
	         */
	        if(!CollectionUtils.isEmpty(icsedRyhms)) {
	            filters.add(root.get("iscedClass").get("code").in(icsedRyhms));
	        } else {
//	            condition which is always false
	            filters.add(cb.isNull(root.get("id")));
	        }
		}
		return cb.and(filters.toArray(new Predicate[filters.size()]));
	}
}
