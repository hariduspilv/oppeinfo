package ee.hitsa.ois.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.ClassifierConnect;
import ee.hitsa.ois.repository.ClassifierConnectRepository;
import ee.hitsa.ois.repository.specification.ClassifierConnectSpecification;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.web.commandobject.ClassifierConnectSearchCommand;


/**
 * TODO: Current solution for managing connections between classifiers seems not to be optimal, though it works.
 */
@Transactional
@Service
public class ClassifierConnectService {

    @Autowired
    private ClassifierConnectRepository classifierConnectRepository;

    public Page<ClassifierConnect> search(ClassifierConnectSearchCommand searchCommand, Pageable pageable) {
        return classifierConnectRepository.findAll(new ClassifierConnectSpecification(searchCommand), pageable);
    }

    public List<ClassifierConnect> searchAll(ClassifierConnectSearchCommand classifierConnectSearchCommand, Sort sort) {
        return classifierConnectRepository.findAll(new ClassifierConnectSpecification(classifierConnectSearchCommand), sort);
    }

    public void updateParents(String code, List<Classifier> newParents) {
        if(newParents == null || newParents.isEmpty()) {
            classifierConnectRepository.removeAllByClassifierCode(code);
            return;
        }

        List<ClassifierConnect> oldParents = classifierConnectRepository.findAllByClassifierCode(code);
        deleteConnections(newParents, oldParents, code);
        Set<String> oldParentCodes = StreamUtil.toMappedSet(p -> EntityUtil.getCode(p.getConnectClassifier()), oldParents);
        newParents.removeIf(newParent -> oldParentCodes.contains(newParent.getCode()));

        /**
         * TODO: It would be better to save new ClassifierConnect object using standard method,
         * not that, which is written manually
         */
        for(Classifier newParent : newParents) {
            classifierConnectRepository.saveNewConnection(code, newParent.getCode(), newParent.getMainClassCode());
//          classifierConnectRepository.save(new ClassifierConnect(classifierRepository.getOne(code), newParent, newParent.getMainClassCode()));
        }
    }

	private void deleteConnections(List<Classifier> newParents, List<ClassifierConnect> oldParents, String code) {
	    Set<String> newParentCodes = StreamUtil.toMappedSet(Classifier::getCode, newParents);

        Iterator<ClassifierConnect> iterator = oldParents.iterator();
		while(iterator.hasNext()) {
            String oldParentCode = EntityUtil.getCode(iterator.next().getConnectClassifier());
            if(!newParentCodes.contains(oldParentCode)) {
                classifierConnectRepository.removeAllByClassifierCodeAndConnectClassifierCode(code, oldParentCode);
                iterator.remove();
            }
        }
    }
}
