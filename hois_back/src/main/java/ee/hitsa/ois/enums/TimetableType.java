package ee.hitsa.ois.enums;

public enum TimetableType {

    TUNNIPLAAN_LIIK_H,  // higher
    TUNNIPLAAN_LIIK_V; // vocational

    public static boolean isHigher(String source) {
        return TUNNIPLAAN_LIIK_H.name().equals(source);
    }
}
