package ee.hitsa.ois.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.domain.SchoolDepartment;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.repository.SchoolDepartmentRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.SchoolDepartmentSearchCommand;
import ee.hitsa.ois.web.dto.SchoolDepartmentDto;

@Transactional
@Service
public class SchoolDepartmentService {

    @Autowired
    SchoolDepartmentRepository schoolDepartmentRepository;

    public Page<SchoolDepartmentDto> findAll(Long schoolId, SchoolDepartmentSearchCommand criteria, Pageable pageable) {
        // load full structure for given school, already sorted
        List<SchoolDepartmentDto> structure = schoolDepartmentRepository.findAllTree(schoolId, pageable.getSort());
        Map<Long, SchoolDepartmentDto> mappedStructure = structure.stream().collect(Collectors.toMap(SchoolDepartmentDto::getId, Function.identity()));
        // filter out matched departments and their parents
        LocalDate now = LocalDate.now();
        Set<Long> filtered = structure.stream().filter(sdt -> {
            String code = criteria.getCode();
            if(StringUtils.hasText(code)) {
                if(!code.equalsIgnoreCase(sdt.getCode())) {
                    return false;
                }
            }
            String name = criteria.getName();
            if(StringUtils.hasText(name)) {
                String nameValue = Language.EN.equals(criteria.getLang()) ? sdt.getNameEn() : sdt.getNameEt();
                if(!StringUtils.hasText(nameValue) || !nameValue.toUpperCase().contains(name.toUpperCase())) {
                    return false;
                }
            }
            if(Boolean.TRUE.equals(criteria.getValid())) {
                // valid: validFrom <= today && (validThru >= today || validThru is null)
                if(now.isBefore(sdt.getValidFrom()) || (sdt.getValidThru() != null && now.isAfter(sdt.getValidThru()))) {
                    return false;
                }
            }
            return true;
        }).map(sd -> {
            List<Long> ids = new ArrayList<>();
            for(SchoolDepartmentDto parent = sd; parent != null; parent = mappedStructure.get(parent.getParentSchoolDepartment())) {
                ids.add(parent.getId());
            }
            return ids;
        }).flatMap(ids -> ids.stream()).collect(Collectors.toSet());

        Map<Long, List<SchoolDepartmentDto>> children = structure.stream().filter(sd -> sd.getParentSchoolDepartment() != null).collect(Collectors.groupingBy(sd -> sd.getParentSchoolDepartment()));
        List<SchoolDepartmentDto> items = structure.stream().filter(sd -> sd.getParentSchoolDepartment() == null && filtered.contains(sd.getId())).map(sd -> createTreeItem(sd, children, filtered)).collect(Collectors.toList());
        int totalCount = items.size();
        int offset = Math.min(pageable.getOffset(), totalCount);
        items = new ArrayList<>(items.subList(offset, Math.min(offset + pageable.getPageSize(), totalCount)));

        return new PageImpl<>(items, pageable, totalCount);
    }

    public SchoolDepartment save(SchoolDepartment schoolDepartment, Long parentSchoolDepartmentId) {
        SchoolDepartment parentSchoolDepartment = null;
        if(parentSchoolDepartmentId != null) {
            parentSchoolDepartment = schoolDepartmentRepository.findOne(parentSchoolDepartmentId);
            Long id = schoolDepartment.getId();
            if(parentSchoolDepartment == null || parentSchoolDepartmentId.equals(id) ||
               !EntityUtil.getId(parentSchoolDepartment.getSchool()).equals(EntityUtil.getId(schoolDepartment.getSchool()))) {
                // bad input, trying to set as parent school department from another school or missing one or itself
                throw new IllegalArgumentException();
            }
            if(id != null) {
                // existing school department - verify that new parent is not child of us
                for(SchoolDepartment psd = parentSchoolDepartment, grandParent; psd != null; psd = grandParent) {
                    grandParent = psd.getParentSchoolDepartment();
                    if(grandParent != null && id.equals(EntityUtil.getId(grandParent))) {
                        // new parent is child if us, move us below it
                        // adjust both school departments
                        psd.setParentSchoolDepartment(schoolDepartment.getParentSchoolDepartment());
                        schoolDepartmentRepository.save(psd);
                        break;
                    }
                }
            }
        }
        schoolDepartment.setParentSchoolDepartment(parentSchoolDepartment);

        return schoolDepartmentRepository.save(schoolDepartment);
    }

    public void delete(SchoolDepartment schoolDepartment) {
        schoolDepartmentRepository.delete(schoolDepartment);
    }

    private static SchoolDepartmentDto createTreeItem(SchoolDepartmentDto sd, Map<Long, List<SchoolDepartmentDto>> children, Set<Long> filtered) {
        sd.setChildren(children.getOrDefault(sd.getId(), Collections.emptyList()).stream().filter(childsd -> filtered.contains(childsd.getId())).map(childsd -> createTreeItem(childsd, children, filtered)).collect(Collectors.toList()));
        return sd;
    }
}
