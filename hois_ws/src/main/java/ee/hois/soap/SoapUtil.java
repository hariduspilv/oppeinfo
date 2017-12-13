package ee.hois.soap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

public abstract class SoapUtil {

    private static final Set<String> RTIP_MISSING_DATES = new HashSet<>(Arrays.asList("0000-00-00", "    -  -  "));
    private static final DateTimeFormatter RTIP_DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final TransformerFactory transformerFactory = TransformerFactory.newInstance();

    public static String asPrettyString(SOAPMessage msg) throws IOException, SOAPException {
        try {
            Transformer tf = transformerFactory.newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            tf.transform(msg.getSOAPPart().getContent(), new StreamResult(os));
            return os.toString(StandardCharsets.UTF_8.name());
        } catch (@SuppressWarnings("unused") TransformerException e) {
            // fallback to write without transformer
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            msg.writeTo(out);
            return out.toString(StandardCharsets.UTF_8.name());
        }
    }

    public static <T> T withExceptionHandler(LogContext ctx, Supplier<T> supplier) {
        try {
            T result = supplier.get();
            ctx.setQueryEnd(LocalDateTime.now());
            return result;
        } catch(Exception e) {
            ctx.setError(e);
            return null;
        }
    }

    /**
     * RTIP date parsing - there is magic "missing" value
     *
     * @param value
     * @return
     */
    public static LocalDate rtipParseDate(String value) {
        if(RTIP_MISSING_DATES.contains(value)) {
            return null;
        }
        return parseDate(value);
    }

    /**
     * LocalDate to RTIP format
     *
     * @param date
     * @return
     */
    public static String rtipPrintDate(LocalDate date) {
        return date.format(RTIP_DATE_FORMAT);
    }

    /**
     * xsd:date to LocalDate conversion
     *
     * @param value
     * @return
     */
    public static LocalDate parseDate(String value) {
        return LocalDate.parse(value);
    }

    /**
     * LocalDate to xsd:date conversion
     *
     * @param date
     * @return
     */
    public static String printDate(LocalDate date) {
        return date.toString();
    }

    /**
     * Kutseregister sends integer as empty text tag, but as we need it as string, do no conversion
     */
    public static String parseTaseType(String value) {
        return "".equals(value) ? null : value;
    }

    public static String printTaseType(String value) {
        return value;
    }
}
