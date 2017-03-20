package ee.hitsa.ois.repository.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import ee.hitsa.ois.domain.ClassifierConnect;
import ee.hitsa.ois.web.commandobject.ClassifierConnectSearchCommand;

public class ClassifierConnectSpecification  implements Specification<ClassifierConnect> {

    private ClassifierConnectSearchCommand searchCommand;

    public ClassifierConnectSpecification(ClassifierConnectSearchCommand classifierConnectSearchCommand) {
        this.searchCommand = classifierConnectSearchCommand;
    }

    @Override
    public Predicate toPredicate(Root<ClassifierConnect> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> filters = new ArrayList<>();

        if(searchCommand.getClassifierCode() != null && !searchCommand.getClassifierCode().isEmpty()) {
            filters.add(root.get("classifier").get("code").in(searchCommand.getClassifierCode()));
        }

        if(searchCommand.getConnectClassifierCode() != null && !searchCommand.getClassifierCode().isEmpty()) {
            filters.add(root.get("connectClassifier").get("code").in(searchCommand.getConnectClassifierCode()));
        }

        if(searchCommand.getMainClassifierCode() != null) {
            filters.add(cb.equal(root.get("mainClassifierCode"), searchCommand.getMainClassifierCode()));
        }

        return cb.and(filters.toArray(new Predicate[filters.size()]));
    }

}
