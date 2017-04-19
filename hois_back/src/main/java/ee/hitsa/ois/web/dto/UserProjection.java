package ee.hitsa.ois.web.dto;


import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;

public interface UserProjection extends Serializable {

    Long getId();

    @Value("#{target.role.nameEt} #{target.school != null ? target.school.code : ''}")
    String getNameEt();

    @Value("#{target.role.nameEn} #{target.school != null ? target.school.code : ''}")
    String getNameEn();

    @Value("#{target.role.nameRu} #{target.school != null ? target.school.code : ''}")
    String getNameRu();
}
