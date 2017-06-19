package ee.hitsa.ois.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Filter;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.loader.Loader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.util.TranslateUtil;

@Service
public class PdfService {

    private static final String TEMPLATE_PATH = "templates/";

    private final PebbleEngine pebble = new PebbleEngine.Builder()
            .strictVariables(true).loader(templateLoader()).extension(new HoisExtension()).build();

    /**
     * Generates pdf using flying saucer
     *
     * @param templateName
     * @param data
     * @return
     */
    public byte[] generatePdf(String templateName, Object data) {
        String xhtml = evaluateTemplate(templateName, data);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(xhtml);
        renderer.layout();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            renderer.createPDF(os);
            renderer.finishPDF();
            return os.toByteArray();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * renders xhtml template for flying saucer with supplied data using pebble template engine.
     *
     * @param templateName
     * @param data
     * @return
     * @throws NullPointerException if templateName is null
     */
    private String evaluateTemplate(String templateName, Object data) {
        try {
            PebbleTemplate template = pebble.getTemplate(Objects.requireNonNull(templateName));
            StringWriter w = new StringWriter();
            template.evaluate(w, Collections.singletonMap("content", data));
            return w.toString();
        } catch (IOException | PebbleException e) {
            throw new RuntimeException(e);
        }
    }

    static class HoisExtension extends AbstractExtension {

        @Override
        public Map<String, Filter> getFilters() {
            Map<String, Filter> filters = new HashMap<>();
            filters.put("translate", new TranslateFilter());
            filters.put("hoisDate", new DateFilter());
            return filters;
        }
    }

    static class TranslateFilter implements Filter {

        @Override
        public List<String> getArgumentNames() {
            return null;
        }

        @Override
        public Object apply(Object input, Map<String, Object> args) {
            // TODO parametrized language
            return TranslateUtil.translate((String)input, Language.ET);
        }
    }

    static class DateFilter implements Filter {
        private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        @Override
        public List<String> getArgumentNames() {
            return null;
        }

        @Override
        public Object apply(Object input, Map<String, Object> args) {
            if(input == null) {
                return null;
            }
            return ((LocalDate)input).format(format);
        }
    }

    static Loader<String> templateLoader() {
        ClasspathLoader loader = new ClasspathLoader(PdfService.class.getClassLoader());
        loader.setPrefix(TEMPLATE_PATH);
        return loader;
    }
}
