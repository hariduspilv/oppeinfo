package ee.hitsa.ois.enums;

import java.util.List;

import ee.hitsa.ois.util.EnumUtil;

public enum StudentStatus {

    OPPURSTAATUS_A, // Akadeemilisel
    OPPURSTAATUS_K, // Katkestanud
    OPPURSTAATUS_O, // Õpib
    OPPURSTAATUS_V, // Välisõppes
    OPPURSTAATUS_L; // Lõpetanud

    public static final List<String> STUDENT_STATUS_ACTIVE = EnumUtil.toNameList(OPPURSTAATUS_O, OPPURSTAATUS_A, OPPURSTAATUS_V);
}
