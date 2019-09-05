package ee.hitsa.ois.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringReader;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.exception.HoisException;

/**
 * RTF (Rich Text Format) file generator using pebble template engine and Apache FOP
 */
@Service
public class RtfService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
    
    @Autowired
    private TemplateService templateService;

    /**
     * Generates pdf using Apache FOP
     *
     * @param templateName
     * @param data
     * @return
     */
    public byte[] generateFop(String templateName, Object data) {
        return generateFop(templateName, data, Language.ET);
    }
    
    /**
     * Generates pdf using Apache FOP
     *
     * @param templateName
     * @param data
     * @return
     */
    public byte[] generateFop(String templateName, Object data, Language lang) {
        String fo = evaluateTemplate(templateName, data, lang == null ? Language.ET : lang);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_RTF, out);
            
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            Source src = new StreamSource(new StringReader(fo));
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, res);
            
            return out.toByteArray();
        } catch (Exception e) {
            log.error("fop rtf generation failed", e);
            throw new HoisException(e);
        }
    }
    
    private String evaluateTemplate(String templateName, Object data, Language lang) {
        Map<String, Object> map = new HashMap<>();
        map.put("content", data);
        map.put("lang", lang);
        return templateService.evaluateTemplate(templateName, map);
    }
}
