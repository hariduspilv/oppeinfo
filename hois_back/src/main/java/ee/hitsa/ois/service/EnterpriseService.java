package ee.hitsa.ois.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Enterprise;
import ee.hitsa.ois.repository.EnterpriseRepository;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.web.commandobject.EnterpriseForm;
import ee.hitsa.ois.web.dto.EnterpriseDto;

@Transactional
@Service
public class EnterpriseService {

    @Autowired
    EnterpriseRepository enterpriseRepository;

    public EnterpriseDto get(Enterprise enterprise) {
        return EnterpriseDto.of(enterprise);
    }

    public Enterprise create(EnterpriseForm enterpriseForm) {
        Enterprise enterprise = EntityUtil.bindToEntity(enterpriseForm, new Enterprise());
        return enterpriseRepository.save(enterprise);
    }

    public void delete(Enterprise enterprise) {
        enterpriseRepository.delete(enterprise);
    }

}
