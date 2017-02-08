package ee.hitsa.ois.web.dto;


import org.springframework.beans.factory.annotation.Value;

public interface UserProjection {

    Long getId();

    @Value("#{target.role.nameEt} #{target.school != null ? target.school.code : ''}")
    String getRoleEt();

    @Value("#{target.role.nameEn} #{target.school != null ? target.school.code : ''}")
    String getRoleEn();

    @Value("#{target.role.nameRu} #{target.school != null ? target.school.code : ''}")
    String getRoleRu();
}
