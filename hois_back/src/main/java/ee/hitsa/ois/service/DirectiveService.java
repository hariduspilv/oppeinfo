package ee.hitsa.ois.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.DirectiveCoordinator;
import ee.hitsa.ois.repository.DirectiveCoordinatorRepository;
import ee.hitsa.ois.web.dto.DirectiveCoordinatorDto;

@Transactional
@Service
public class DirectiveService {

    @Autowired
    private DirectiveCoordinatorRepository directiveCoordinatorRepository;

    public Page<DirectiveCoordinatorDto> search(Long schoolId, Pageable pageable) {
        return directiveCoordinatorRepository.findAllBySchool_id(schoolId, pageable);
    }

    public DirectiveCoordinator save(DirectiveCoordinator coordinator) {
        return directiveCoordinatorRepository.save(coordinator);
    }

    public void delete(DirectiveCoordinator coordinator) {
        directiveCoordinatorRepository.delete(coordinator);
    }
}
