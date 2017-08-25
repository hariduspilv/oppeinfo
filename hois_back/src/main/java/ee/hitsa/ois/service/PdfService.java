package ee.hitsa.ois.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.coverity.security.Escape;
import com.lowagie.text.DocumentException;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Filter;
import com.mitchellbosecke.pebble.extension.escaper.EscapingStrategy;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.loader.Loader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.util.TranslateUtil;

/**
 * Pdf generator using pebble template engine and flying saycer xhtml to pdf renderer
 */
@Service
public class PdfService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String TEMPLATE_PATH = "templates/";

    private final PebbleEngine pebble = new PebbleEngine.Builder()
            .strictVariables(true).loader(templateLoader()).extension(new HoisExtension())
            .addEscapingStrategy("text", new TextEscapingStrategy())
            .defaultEscapingStrategy("text").build();

    /**
     * Generates pdf using flying saucer
     *
     * @param templateName
     * @param data
     * @return
     */
    public byte[] generate(String templateName, Object data) {
        String xhtml = evaluateTemplate(templateName, Collections.singletonMap("content", data));
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

    /**
     * renders xhtml template for flying saucer with supplied data using pebble template engine.
     *
     * @param templateName
     * @param data
     * @return
     * @throws NullPointerException if templateName is null
     */
    private String evaluateTemplate(String templateName, Map<String, Object> data) {
        try {
            PebbleTemplate template = pebble.getTemplate(Objects.requireNonNull(templateName));
            StringWriter w = new StringWriter();
            template.evaluate(w, data);
            return w.toString();
        } catch (IOException | PebbleException e) {
            log.error("pdf template evaluation failed", e);
            throw new HoisException(e);
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

    /**
     * Multi-row text split, rows escaped and joined using &lt;br/&gt;
     */
    static class TextEscapingStrategy implements EscapingStrategy {

        @Override
        public String escape(String input) {
            if(input == null) {
                return null;
            }
            String[] rows = input.split("\n");
            if(rows.length == 1) {
                return Escape.htmlText(input);
            }
            return Arrays.asList(rows).stream().map(Escape::htmlText).collect(Collectors.joining("<br/>"));
        }
    }

    static Loader<String> templateLoader() {
        ClasspathLoader loader = new ClasspathLoader(PdfService.class.getClassLoader());
        loader.setPrefix(TEMPLATE_PATH);
        return loader;
    }
}
