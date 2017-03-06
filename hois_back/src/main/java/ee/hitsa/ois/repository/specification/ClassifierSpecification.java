package ee.hitsa.ois.repository.specification;

import static ee.hitsa.ois.util.EntityUtil.propertyName;
import static ee.hitsa.ois.util.SearchUtil.propertyContains;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.web.commandobject.ClassifierSearchCommand;

public class ClassifierSpecification implements Specification<Classifier> {

	private ClassifierSearchCommand searchCommand;

	public ClassifierSearchCommand getSearchCommand() {
        return searchCommand;
    }

    public void setSearchCommand(ClassifierSearchCommand searchCommand) {
        this.searchCommand = searchCommand;
    }

    public ClassifierSpecification(ClassifierSearchCommand classifierSearchCommand) {
		setSearchCommand(classifierSearchCommand);
	}

	@Override
	public Predicate toPredicate(Root<Classifier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> filters = new ArrayList<>();

        if(StringUtils.hasText(searchCommand.getCode())) {
        	filters.add(cb.equal(root.get("code"), searchCommand.getCode()));
        }

        propertyContains(() -> root.get("value"), cb, searchCommand.getValue(), filters::add);

        if(searchCommand.isHigher() != null) {
            filters.add(cb.equal(root.get("higher"), searchCommand.isHigher()));
        }

        if(searchCommand.isVocational() != null) {
            filters.add(cb.equal(root.get("vocational"), searchCommand.isVocational()));
        }

        //This must be exact equal for dropdown's
        if(searchCommand.getMainClassCode() != null) {
        	filters.add(cb.equal(root.get("mainClassCode"), searchCommand.getMainClassCode()));
        }

        if(searchCommand.getMainClassCodes() != null) {
            filters.add(root.get("mainClassCode").in(searchCommand.getMainClassCodes()));
        }

        propertyContains(() -> root.get(propertyName("name", searchCommand.getLang())), cb, searchCommand.getName(), filters::add);

        return cb.and(filters.toArray(new Predicate[filters.size()]));
	}
}
