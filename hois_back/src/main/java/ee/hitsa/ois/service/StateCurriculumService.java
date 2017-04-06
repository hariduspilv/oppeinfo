package ee.hitsa.ois.service;

import static ee.hitsa.ois.util.JpaQueryUtil.resultAsLong;
import static ee.hitsa.ois.util.JpaQueryUtil.resultAsString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculum;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculumModule;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculumModuleOccupation;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculumModuleOutcome;
import ee.hitsa.ois.domain.statecurriculum.StateCurriculumOccupation;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.repository.StateCurriculumRepository;
import ee.hitsa.ois.repository.specification.StateCurriculumSpecification;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.util.SearchUtil;
import ee.hitsa.ois.web.commandobject.StateCurriculumForm;
import ee.hitsa.ois.web.commandobject.StateCurriculumSearchCommand;
import ee.hitsa.ois.web.commandobject.UniqueCommand;
import ee.hitsa.ois.web.dto.StateCurriculumModuleDto;
import ee.hitsa.ois.web.dto.StateCurriculumSearchDto;

@Transactional
@Service
public class StateCurriculumService {

	@Autowired
	private StateCurriculumRepository stateCurriculumRepository;

    @Autowired
    private ClassifierRepository classifierRepository;

    /**
     * TODO: this is not optimal solution.
     */
    public Page<StateCurriculumSearchDto> search(StateCurriculumSearchCommand stateCurriculumSearchCommand, Pageable pageable) {

        if(stateCurriculumSearchCommand.getEkrLevel() != null && !stateCurriculumSearchCommand.getEkrLevel().isEmpty() || pageable.getSort() != null && (
                pageable.getSort().toString().equals("ekrLevel: DESC") || pageable.getSort().toString().equals("ekrLevel: ASC"))) {
            List<StateCurriculum> theBestList = stateCurriculumRepository.findAll(new StateCurriculumSpecification(stateCurriculumSearchCommand));
            setEkrLevels(theBestList);

            if(stateCurriculumSearchCommand.getEkrLevel() != null && !stateCurriculumSearchCommand.getEkrLevel().isEmpty()) {
                theBestList =  theBestList.stream().filter(
                        sc -> correctEkrLevel(sc.getEkrLevel(), stateCurriculumSearchCommand.getEkrLevel())
                        ).collect(Collectors.toList());
            }
            Page<StateCurriculum> page = sortList(theBestList, pageable);
            return page.map(StateCurriculumSearchDto::of);
        }
        Page<StateCurriculum> page = stateCurriculumRepository.findAll(new StateCurriculumSpecification(stateCurriculumSearchCommand), pageable);
        setEkrLevels(page);
        return page.map(StateCurriculumSearchDto::of);
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
      List<Object[]> list = stateCurriculumRepository.getEkrLEvels();
      Map<Long, String> map = list.stream().filter(e -> e[1] != null).collect(
              Collectors.toMap(e -> resultAsLong(e, 0), e -> resultAsString(e, 1), (o, n) -> n));
      Iterator<StateCurriculum> iterator = iterable.iterator();
      while(iterator.hasNext()) {
          StateCurriculum s = iterator.next();
          Long key = s.getId();
          String ekr = map.get(key);
          s.setEkrLevel(ekr);
      }
	}

    public boolean isUnique(UniqueCommand command) {
        return stateCurriculumRepository.count((root, query, cb) -> {
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
		EntityUtil.deleteEntity(stateCurriculumRepository, curriculum);
	}

    public List<StateCurriculum> searchAll(StateCurriculumSearchCommand command, Sort sort) {
        return stateCurriculumRepository.findAll((root, query, cb) -> {
            List<Predicate> filters = new ArrayList<>();
            if(Boolean.TRUE.equals(command.getValid())) {
                LocalDate now = LocalDate.now();
                filters.add(cb.or(cb.lessThanOrEqualTo(root.get("validFrom"), now), cb.isNull(root.get("validFrom"))));
                filters.add(cb.or(cb.greaterThanOrEqualTo(root.get("validThru"), now), cb.isNull(root.get("validThru"))));
            }

            if(Boolean.FALSE.equals(command.getExpired())) {
                LocalDate now = LocalDate.now();
                filters.add(cb.or(cb.greaterThanOrEqualTo(root.get("validThru"), now), cb.isNull(root.get("validThru"))));
            }

            if (!CollectionUtils.isEmpty(command.getStatus())) {
                filters.add(root.get("status").get("code").in(command.getStatus()));
            }
            return cb.and(filters.toArray(new Predicate[filters.size()]));
        }, sort);
    }

    public StateCurriculum create(StateCurriculumForm stateCurriculumForm) {
        return save(new StateCurriculum(), stateCurriculumForm);
    }

    public StateCurriculum save(StateCurriculum stateCurriculum, StateCurriculumForm stateCurriculumForm) {

        EntityUtil.bindToEntity(stateCurriculumForm, stateCurriculum, classifierRepository, "occupations", "modules");
        updateOccupations(stateCurriculum, stateCurriculumForm.getOccupations());
        updateModules(stateCurriculum, stateCurriculumForm.getModules());
        return stateCurriculumRepository.save(stateCurriculum);
    }

    private void updateOccupations(StateCurriculum stateCurriculum, Set<String> occupations) {
        Set<StateCurriculumOccupation> storedOccupations = stateCurriculum.getOccupations();
        if(occupations != null && !occupations.isEmpty()) {
            EntityUtil.bindClassifierCollection(storedOccupations, o -> EntityUtil.getCode(o.getOccupation()), occupations, occupation -> {
                return new StateCurriculumOccupation(EntityUtil.validateClassifier(classifierRepository.getOne(occupation), MainClassCode.KUTSE));
            });
        } else {
            stateCurriculum.setOccupations(new HashSet<>());
        }
    }

    private void updateModules(StateCurriculum stateCurriculum, Set<StateCurriculumModuleDto> moduleDtos) {
        Set<StateCurriculumModule> newModules = new HashSet<>();
        moduleDtos.forEach(dto -> {
            StateCurriculumModule module = dto.getId() == null ? new StateCurriculumModule() :
                stateCurriculum.getModules().stream().filter(m -> m.getId().equals(dto.getId())).findFirst().get();
            module = EntityUtil.bindToEntity(dto, module, classifierRepository, "outcome", "moduleOccupations");
            StateCurriculumModuleOutcome outcome = module.getOutcome() != null
                    ? module.getOutcome() : new StateCurriculumModuleOutcome();
            outcome.setOutcomesEt(dto.getOutcomesEt());
            outcome.setOutcomesEn(dto.getOutcomesEn());
            module.setOutcome(outcome);
            updateModuleOccupations(module, dto.getModuleOccupations());
            newModules.add(module);
        });
        stateCurriculum.setModules(newModules);
    }

    private void updateModuleOccupations(StateCurriculumModule module, Set<String> moduleOccupations) {
        Set<StateCurriculumModuleOccupation> newSet = new HashSet<>();

        if(moduleOccupations != null && !moduleOccupations.isEmpty()) {
            Map<String, StateCurriculumModuleOccupation> occupations = module.getModuleOccupations().stream()
                    .collect(Collectors.toMap(o -> EntityUtil.getCode(o.getOccupation()), e -> e));
            for(String occupation : moduleOccupations) {
                if(occupations.keySet().contains(occupation)) {
                    newSet.add(occupations.get(occupation));
                } else {
                  Classifier c = EntityUtil.validateClassifier(classifierRepository.getOne(occupation),
                          MainClassCode.KUTSE, MainClassCode.OSAKUTSE, MainClassCode.SPETSKUTSE);

                  newSet.add(new StateCurriculumModuleOccupation(c));
                }
            }
        }
        module.setModuleOccupations(newSet);
    }

    public StateCurriculum create(StateCurriculum stateCurriculum) {
        return stateCurriculumRepository.save(stateCurriculum);
    }
}
