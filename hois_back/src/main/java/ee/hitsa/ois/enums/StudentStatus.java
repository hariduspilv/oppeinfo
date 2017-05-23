package ee.hitsa.ois.enums;

import java.util.Arrays;
import java.util.List;

public enum StudentStatus {

    OPPURSTAATUS_A, // Akadeemilisel
    OPPURSTAATUS_K, // Katkestanud
    OPPURSTAATUS_O, // Õpib
    OPPURSTAATUS_V, // Välisõppes
    OPPURSTAATUS_L; // Lõpetanud

    public static final List<String> STUDENT_STATUS_ACTIVE = Arrays.asList(OPPURSTAATUS_O.name(), OPPURSTAATUS_A.name(), OPPURSTAATUS_V.name());
}
