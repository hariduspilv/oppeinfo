package ee.hitsa.ois.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class ProtocolUtil {
    
    private static DateTimeFormatter shortYearFormatter = DateTimeFormatter.ofPattern("YY");

    
    // TODO: proper per school protocol number generation
    public static String generateProtocolNumber(EntityManager em) {
        Query q = em.createNativeQuery("select nextval('public.protocol_id_seq')");
        return LocalDate.now().format(shortYearFormatter) + String.format("%04d", q.getSingleResult());
    }
}
