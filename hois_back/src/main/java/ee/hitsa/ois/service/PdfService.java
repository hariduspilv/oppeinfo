package ee.hitsa.ois.service;

import java.io.ByteArrayOutputStream;
import java.lang.invoke.MethodHandles;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

import ee.hitsa.ois.exception.HoisException;

/**
 * Pdf generator using pebble template engine and flying saycer xhtml to pdf renderer
 */
@Service
public class PdfService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
    @Autowired
    private TemplateService templateService;

    /**
     * Generates pdf using flying saucer
     *
     * @param templateName
     * @param data
     * @return
     */
    public byte[] generate(String templateName, Object data) {
        String xhtml = templateService.evaluateTemplate(templateName, Collections.singletonMap("content", data));
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(xhtml);
        renderer.layout();

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
}
