package ee.hitsa.ois.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import ee.hitsa.ois.domain.Subject;
import ee.hitsa.ois.enums.SubjectStatus;
import ee.hitsa.ois.web.commandobject.SubjectAutocompleteCommand;
import ee.hitsa.ois.web.commandobject.SubjectSearchCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.repository.CurriculumRepository;
import ee.hitsa.ois.repository.SchoolDepartmentRepository;
import ee.hitsa.ois.web.commandobject.SchoolDepartmentAutocompleteCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;

@Transactional
@Service
public class AutocompleteService {

    private static final int MAX_ITEM_COUNT = 20;

    @Autowired
    private CurriculumRepository curriculumRepository;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private SchoolDepartmentRepository schoolDepartmentRepository;

    public List<AutocompleteResult<Long>> curriculums(Long schoolId) {
        return curriculumRepository.findAllBySchool_id(schoolId);
    }

    /**
     * Values for selecting department. All departments of given school are returned ordered by name(Et|En) field
     * @param schoolId
     * @param criteria
     * @return
     */
    public List<AutocompleteResult<Long>> schoolDepartments(Long schoolId, SchoolDepartmentAutocompleteCommand criteria) {
        List<AutocompleteResult<Long>> result = schoolDepartmentRepository.findAllBySchool_id(schoolId);
        Long excludedId = criteria.getExcludedId();
        if(excludedId != null) {
            result = result.stream().filter(r -> !excludedId.equals(r.getId())).collect(Collectors.toList());
        }
        return result;
    }

    public Page<Subject> subjects(Long schoolId, SubjectAutocompleteCommand command) {
        SubjectSearchCommand subjectSearchCommand = new SubjectSearchCommand();
        subjectSearchCommand.setName(command.getName());
        subjectSearchCommand.setStatus(Collections.singletonList(SubjectStatus.AINESTAATUS_K.name()));
        return subjectService.search(schoolId, subjectSearchCommand, new PageRequest(0, MAX_ITEM_COUNT));
    }

    private static PageRequest sortAndLimit(String sortField) {
        return new PageRequest(0, MAX_ITEM_COUNT, new Sort(sortField));
    }
}
