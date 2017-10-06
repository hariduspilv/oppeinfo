package ee.hois.soap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.function.Supplier;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

public abstract class SoapUtil {

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
}
