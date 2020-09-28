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
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;

import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.exception.HoisException;

/**
 * Pdf generator using pebble template engine and flying saucer xhtml to pdf renderer or Apache FOP
 */
@Service
public class PdfService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
    
    @Autowired
    private TemplateService templateService;

    /**
     * Generates pdf using flying saucer
     *
     * @param templateName
     * @param data
     * @return
     */
    public byte[] generate(String templateName, Object data, Language lang) {
        ITextRenderer renderer = createRenderer(templateName, data, lang);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            renderer.createPDF(os);
            renderer.finishPDF();
            return os.toByteArray();
        } catch (DocumentException e) {
            log.error("pdf generation failed", e);
            throw new HoisException(e);
        }
    }
    

    public byte[] generate(String templateName, Object data) {
        return generate(templateName, data, Language.ET);
    }

    /**
     * Generates pdf using flying saucer and returns total number of pages used
     *
     * @param templateName
     * @param data
     * @return
     */
    public int getPageCount(String templateName, Object data, Language lang) {
        ITextRenderer renderer = createRenderer(templateName, data, lang);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            renderer.createPDF(os);
            renderer.finishPDF();
            return renderer.getRootBox().getLayer().getPages().size();
        } catch (DocumentException e) {
            log.error("pdf generation failed", e);
            throw new HoisException(e);
        }
    }
    
    public int getPageCount(String templateName, Object data) {
        return getPageCount(templateName, data, Language.ET);
    }
    
    private ITextRenderer createRenderer(String templateName, Object data, Language lang) {
        String xhtml = evaluateTemplate(templateName, data, lang);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(xhtml);
        try {
            renderer.getFontResolver().addFont("fonts/GARA.TTF", BaseFont.IDENTITY_H, true/*BaseFont.NOT_EMBEDDED*/);
        } catch (Exception e) {
            log.error("pdf generation failed, cannot add font", e);
            throw new HoisException(e);
        }
        renderer.layout();
        return renderer;
    }

    /**
     * Generates pdf using Apache FOP
     *
     * @param templateName
     * @param data
     * @return
     */
    public byte[] generateFop(String templateName, Object data) {
        String fo = evaluateTemplate(templateName, data);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            @SuppressWarnings("static-access")
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
            
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            Source src = new StreamSource(new StringReader(fo));
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, res);
            
            return out.toByteArray();
        } catch (Exception e) {
            log.error("fop pdf generation failed", e);
            throw new HoisException(e);
        }
    }

    private String evaluateTemplate(String templateName, Object data, Language lang) {
        Map<String, Object> map = new HashMap<>();
        map.put("content", data);
        map.put("lang", lang);
        return templateService.evaluateTemplate(templateName, map);
    }

    private String evaluateTemplate(String templateName, Object data) {
        return evaluateTemplate(templateName, data, Language.ET);
    }
}
