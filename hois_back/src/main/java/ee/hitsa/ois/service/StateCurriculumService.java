package ee.hitsa.ois.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.StateCurriculum;
import ee.hitsa.ois.domain.StateCurriculumModule;
import ee.hitsa.ois.domain.StateCurriculumModuleOccupation;
import ee.hitsa.ois.domain.StateCurriculumModuleOutcome;
import ee.hitsa.ois.domain.StateCurriculumOccupation;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.StateCurriculumRepository;
import ee.hitsa.ois.repository.specification.StateCurriculumSpecification;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.SearchUtil;
import ee.hitsa.ois.web.commandobject.StateCurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.UniqueCommand;

@Transactional
@Service
public class StateCurriculumService {

	@Autowired
    StateCurriculumRepository repository;

    @Autowired
    ClassifierRepository classifierRepository;

    public StateCurriculum create(StateCurriculum curriculum) {
        return repository.save(curriculum);
    }

    /**
     * TODO: Searching and filtering by EKR level seems to work fine,
     * however, this is probably not the optimal solution.
     */
	public Page<StateCurriculum> search(StateCurriculumSearchCommand stateCurriculumSearchCommand, Pageable pageable) {
		if(stateCurriculumSearchCommand.getEkrLevels() != null && !stateCurriculumSearchCommand.getEkrLevels().isEmpty() ||
				pageable.getSort().toString().equals("ekrLevel: DESC") || pageable.getSort().toString().equals("ekrLevel: ASC")) {
			List<StateCurriculum> theBestList = repository.findAll(new StateCurriculumSpecification(stateCurriculumSearchCommand));
			setEkrLevels(theBestList);

			if(stateCurriculumSearchCommand.getEkrLevels() != null && !stateCurriculumSearchCommand.getEkrLevels().isEmpty()) {
				theBestList =  theBestList.stream().filter(
						sc -> correctEkrLevel(sc.getEkrLevel(), stateCurriculumSearchCommand.getEkrLevels())
						).collect(Collectors.toList());
			}
			return sortList(theBestList, pageable);
		}
		Page<StateCurriculum> page = repository.findAll(new StateCurriculumSpecification(stateCurriculumSearchCommand), pageable);
		setEkrLevels(page);
		return page;
	}

	private static boolean correctEkrLevel(String ekrLevel, List<String> ekrLevels) {
		return ekrLevels.contains(ekrLevel);
	}

	private static Page<StateCurriculum> sortList(List<StateCurriculum> theBestList, Pageable pageable) {
	    return SearchUtil.sort(theBestList, pageable, (order) -> {
			switch(order.getProperty()) {
			case "id":
				return Comparator.comparing(StateCurriculum::getId);
			case "nameEt":
				return Comparator.comparing(StateCurriculum::getNameEt);
			case "credits":
				return Comparator.comparing(StateCurriculum::getCredits);
			case "validFrom":
				return Comparator.comparing(StateCurriculum::getValidFrom);
			case "validThru":
                // field which can be null
				return Comparator.comparing(StateCurriculum::getValidThru, Comparator.nullsLast(Comparator.naturalOrder()));
			case "status":
			    // nested field which can be null
				return Comparator.comparing(StateCurriculum::getStatus, Comparator.nullsFirst(Comparator.comparing(Classifier::getNameEt)));
			case "ekrLevel":
				return Comparator.comparing(StateCurriculum::getEkrLevel, Comparator.nullsFirst(Comparator.naturalOrder()));
			default:
				// FIXME maybe it's better to throw IllegalArgumentException?
				return null;
			}
		});
	}

	private void setEkrLevels(Iterable<StateCurriculum> iterable) {
		Iterator<StateCurriculum> iterator = iterable.iterator();

		while(iterator.hasNext()) {
			StateCurriculum s = iterator.next();
			if(!s.getOccupations().isEmpty()) {
				String occupation = s.getOccupations().iterator().next().getOccupation().getCode();
				List<Classifier> occupations = classifierRepository.findParentsByMainClassifier(occupation, "EKR");
				if(occupations != null && !occupations.isEmpty()) {
					s.setEkrLevel(occupations.get(0).getNameEt());
				}
			}
		}
	}

    public boolean isUnique(UniqueCommand command) {
        return repository.count((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            if(command.getId() != null) {
                filters.add(cb.notEqual(root.get("id"), command.getId()));
            }
            final Set<String> ALLOWED_PROPERTIES = new HashSet<>(Arrays.asList("nameEt", "nameEn"));
            if(ALLOWED_PROPERTIES.contains(command.getParamName())) {
                filters.add(cb.equal(root.get(command.getParamName()), command.getParamValue()));
            }
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }) == 0;
    }

	public void delete(StateCurriculum curriculum) {
		repository.delete(curriculum);
	}

	public StateCurriculum update(StateCurriculum newStateCurriculum, StateCurriculum stateCurriculum) {
	   EntityUtil.bindToEntity(newStateCurriculum, stateCurriculum, "occupations", "modules");
	   updateOccupations(stateCurriculum, newStateCurriculum.getOccupations());
	   updateModules(stateCurriculum, newStateCurriculum.getModules());
       return repository.save(stateCurriculum);
	}

    private void updateOccupations(StateCurriculum stateCurriculum, Set<StateCurriculumOccupation> occupations) {
        Set<StateCurriculumOccupation> newOccupations = new HashSet<>(); 
        if(occupations != null) {
            Set<Long> existingOccupations = stateCurriculum.getOccupations().stream().map(o -> o.getId()).collect(Collectors.toSet());
            occupations.forEach(o ->{
                if(o.getId() != null && existingOccupations.contains(o.getId())) {
                    newOccupations.add(stateCurriculum.getOccupations().stream()
                            .filter(o2 -> o2.getId().equals(o.getId())).findFirst().get());
                } else if(o.getId() == null) {
                    newOccupations.add(o);
                }
            });

            }
        stateCurriculum.setOccupations(newOccupations);
    }
    
    private void updateModules(StateCurriculum stateCurriculum, Set<StateCurriculumModule> modules) {
        Set<StateCurriculumModule> newModules = new HashSet<>();
        
        if(modules != null) {
            Set <Long> existingModulesIds =  stateCurriculum.getModules().stream().map(m -> m.getId()).collect(Collectors.toSet());
            modules.forEach(m -> {
                Long id = m.getId();
                if(id == null) {
                    newModules.add(m);
                } else if (existingModulesIds.contains(id)){
                    StateCurriculumModule editedModule = getModuleById(id, stateCurriculum.getModules());
                    EntityUtil.bindToEntity(m, editedModule, "version", "moduleOccupations", "outcome");
                    updateModuleOutcome(editedModule.getOutcome(), m.getOutcome());
                    updateModuleOccupations(editedModule, m.getModuleOccupations());
                    newModules.add(editedModule);
                }
            });
        }
        stateCurriculum.setModules(newModules);
    }

    private void updateModuleOccupations(StateCurriculumModule editedModule,
            Set<StateCurriculumModuleOccupation> moduleOccupations) {
        Set<StateCurriculumModuleOccupation> newOccupations = new HashSet<>();
        Set<Long> existingIds = editedModule.getModuleOccupations().stream()
                .map(StateCurriculumModuleOccupation::getId).collect(Collectors.toSet());

        if(moduleOccupations != null) {
            moduleOccupations.forEach(mo -> {
                Long id = mo.getId();
                if(id == null) {
                    newOccupations.add(mo);
                } else if(existingIds.contains(id)) {
                    StateCurriculumModuleOccupation moduleOccupation = getModuleOccupationById(id, editedModule.getModuleOccupations());
                    newOccupations.add(moduleOccupation);
                }
            });
        }
        editedModule.setModuleOccupations(newOccupations);
    }

    private StateCurriculumModuleOccupation getModuleOccupationById(Long id,
            Set<StateCurriculumModuleOccupation> moduleOccupations) {
        return moduleOccupations.stream().filter(mo -> mo.getId().equals(id)).findFirst().get();
    }

    private void updateModuleOutcome(StateCurriculumModuleOutcome oldOutcome, StateCurriculumModuleOutcome newOutCome) {
        oldOutcome.setOutcomesEt(newOutCome.getOutcomesEt());
        oldOutcome.setOutcomesEn(newOutCome.getOutcomesEn());
    }

    private StateCurriculumModule getModuleById(Long id, Set<StateCurriculumModule> modules) {
        return modules.stream().filter(m -> m.getId().equals(id)).findFirst().get();
    }

    public List<StateCurriculum> searchAll(StateCurriculumSearchCommand stateCurriculumSearchCommand, Sort sort) {
        return repository.findAll((root, query, cb) -> {
            return null;
        }, sort);
    }
}
