package ee.hitsa.ois.repository.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.SearchUtil;
import ee.hitsa.ois.web.commandobject.StateCurriculumSearchCommand;

public class StateCurriculumSpecification implements Specification<StateCurriculum> {

	StateCurriculumSearchCommand searchCommand;

	public StateCurriculumSpecification(StateCurriculumSearchCommand stateCurriculumSearchCommand) {
		this.searchCommand = stateCurriculumSearchCommand;
	}

	@Override
	public Predicate toPredicate(Root<StateCurriculum> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> filters = new ArrayList<>();

		String nameField = searchCommand.getLang() == Language.EN ? "nameEn" : "nameEt";
		SearchUtil.propertyContains(() -> root.get(nameField), cb, searchCommand.getName(), filters::add);

		if(searchCommand.getStatus() != null) {
			filters.add(root.get("status").get("code").in(searchCommand.getStatus()));
		}
		if(searchCommand.getIscedClass() != null) {
			filters.add(root.get("iscedClass").get("code").in(searchCommand.getIscedClass()));
		}
		if(searchCommand.getValidFrom() != null) {
			filters.add(cb.greaterThanOrEqualTo(root.get("validFrom"), searchCommand.getValidFrom()));
		}
		if(searchCommand.getValidThru() != null) {
			filters.add(cb.lessThanOrEqualTo(root.get("validThru"), searchCommand.getValidThru()));
		}
		return cb.and(filters.toArray(new Predicate[filters.size()]));
	}
}
