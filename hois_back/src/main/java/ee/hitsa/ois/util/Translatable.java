package ee.hitsa.ois.util;


public interface Translatable {

    String getNameEt();
    String getNameEn();
    default String getNameRu() {
        return getNameEt();
    }
}
