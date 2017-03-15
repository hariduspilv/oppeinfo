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
		
		List<String> icsedRyhms = new ArrayList<>();
        if(!CollectionUtils.isEmpty(searchCommand.getIscedClass())) {
            icsedRyhms.addAll(searchCommand.getIscedClass());
        }
        if(!CollectionUtils.isEmpty(searchCommand.getIscedSuun())) {
            icsedRyhms.addAll(classifierRepository.findChildrenByMainClassifier(searchCommand.getIscedSuun(), "ISCED_RYHM"));
        }
        if(searchCommand.getIscedVald() != null) {
            List<String> iscedSuuns = classifierRepository.findChildrenByMainClassifier(Arrays.asList(searchCommand.getIscedVald()), "ISCED_SUUN");
              if(!CollectionUtils.isEmpty(iscedSuuns)) {
                   icsedRyhms.addAll(classifierRepository.findChildrenByMainClassifier(iscedSuuns, "ISCED_RYHM"));
              }
        }
        if(!CollectionUtils.isEmpty(icsedRyhms)) {
          filters.add(root.get("iscedClass").get("code").in(icsedRyhms));
        }
		return cb.and(filters.toArray(new Predicate[filters.size()]));
	}
}
