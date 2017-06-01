package ee.hitsa.ois.repository.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.ClassifierConnect;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.ClassifierConnectSearchCommand;

public class ClassifierConnectSpecification implements Specification<ClassifierConnect> {

    private ClassifierConnectSearchCommand command;

    public ClassifierConnectSpecification(ClassifierConnectSearchCommand classifierConnectSearchCommand) {
        this.command = classifierConnectSearchCommand;
    }

    @Override
    public Predicate toPredicate(Root<ClassifierConnect> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        root.fetch("classifier"); root.fetch("connectClassifier");

        List<Predicate> filters = new ArrayList<>();

        if(!CollectionUtils.isEmpty(command.getClassifierCode())) {
            filters.add(root.get("classifier").get("code").in(command.getClassifierCode()));
        }

        if(!CollectionUtils.isEmpty(command.getConnectClassifierCode())) {
            filters.add(root.get("connectClassifier").get("code").in(command.getConnectClassifierCode()));
        }

        if(StringUtils.hasText(command.getMainClassifierCode())) {
            filters.add(cb.equal(root.get("mainClassifierCode"), command.getMainClassifierCode()));
        }

        if (!CollectionUtils.isEmpty(command.getClassifierMainClassCode())) {
            filters.add(root.get("classifier").get("mainClassCode").in(command.getClassifierMainClassCode()));
        }

        if (!CollectionUtils.isEmpty(command.getConnectClassifierMainClassCode())) {
            filters.add(root.get("connectClassifier").get("mainClassCode").in(command.getConnectClassifierMainClassCode()));
        }

        //do not allow query with no restrictions
        if (CollectionUtils.isEmpty(filters)) {
            throw new ValidationFailedException("queryWithNoParametersIsNotAllowed");
        }

        return cb.and(filters.toArray(new Predicate[filters.size()]));
    }
}
