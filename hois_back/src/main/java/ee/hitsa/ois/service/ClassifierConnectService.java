package ee.hitsa.ois.service;

import java.util.Iterator;
import java.util.List;

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
import ee.hitsa.ois.web.commandobject.ClassifierConnectSearchCommand;


/**
 * TODO: Current solution for managing connections between classifiers seems not to be optimal, though it works.
 */
@Transactional
@Service
public class ClassifierConnectService {

	private ClassifierConnectRepository repository;

    public ClassifierConnectService(@Autowired ClassifierConnectRepository repository) {
    	this.repository = repository;
    }

    public Page<ClassifierConnect> search(ClassifierConnectSearchCommand searchCommand, Pageable pageable) {
        return repository.findAll(new ClassifierConnectSpecification(searchCommand), pageable);
    }

    public void updateParents(String code, List<Classifier> newParents) {
        if(newParents == null || newParents.isEmpty()) {
            repository.removeAllByClassifierCode(code);
            return;
        }

        List<ClassifierConnect> oldParents = repository.findAllByClassifierCode(code);
        deleteConnections(newParents, oldParents, code);
        removeUnchangedConnections(newParents, oldParents, code);
        addNewConnections(newParents, code);
    }

	private void deleteConnections(List<Classifier> newParents, List<ClassifierConnect> oldParents, String code) {

		Iterator<ClassifierConnect> iterator = oldParents.iterator();

		while(iterator.hasNext()) {
			ClassifierConnect oldParent = iterator.next();
			if(notInNewParents(oldParent, newParents)) {
				repository.removeAllByClassifierCodeAndConnectClassifierCode(code, oldParent.getConnectClassifier().getCode());
				iterator.remove();
			}
		}
	}

	private boolean notInNewParents(ClassifierConnect oldParent, List<Classifier> newParents) {
	    return !newParents.stream().anyMatch(newParent -> newParent.getCode().equals(oldParent.getConnectClassifier().getCode()));
	}

	private void removeUnchangedConnections(List<Classifier> newParents, List<ClassifierConnect> oldParents,
			String code) {

		newParents.removeIf(newParent -> inOldParents(newParent, oldParents));
	}
    /**
     * TODO: It would be better to save new ClassifierConnect object using standard method,
     * not that, which is written manually
     */
	private void addNewConnections(List<Classifier> newParents, String code) {
		for(Classifier newParent : newParents) {
			repository.saveNewConnection(code, newParent.getCode(), newParent.getMainClassCode());
//			repository.save(new ClassifierConnect(classifierRepository.getOne(code), newParent, newParent.getMainClassCode()));
		}
	}

	private boolean inOldParents(Classifier newParent, List<ClassifierConnect> oldParents) {
		return oldParents.stream().anyMatch(oldParent -> newParent.getCode().equals(oldParent.getConnectClassifier().getCode()));
	}

	public List<ClassifierConnect> searchAll(ClassifierConnectSearchCommand classifierConnectSearchCommand, Sort sort) {
        return repository.findAll(new ClassifierConnectSpecification(classifierConnectSearchCommand), sort);
    }
}
