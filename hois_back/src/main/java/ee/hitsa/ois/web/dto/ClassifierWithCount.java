package ee.hitsa.ois.web.dto;

import org.springframework.beans.factory.annotation.Value;

public interface ClassifierWithCount {
    String getCode();
    String getNameEt();
    String getNameEn();

    @Value("#{ target.children.size() }")
    long getCount();
}