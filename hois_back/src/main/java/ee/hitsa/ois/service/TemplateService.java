package ee.hitsa.ois.service;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.coverity.security.Escape;
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

@Service
public class TemplateService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String TEMPLATE_PATH = "templates/";

    private final PebbleEngine pebble = new PebbleEngine.Builder()
            .strictVariables(true).loader(templateLoader()).extension(new HoisExtension())
            .addEscapingStrategy("text", new TextEscapingStrategy())
            .defaultEscapingStrategy("text").build();

    static Loader<String> templateLoader() {
        ClasspathLoader loader = new ClasspathLoader(PdfService.class.getClassLoader());
        loader.setPrefix(TEMPLATE_PATH);
        return loader;
    }

    /**
     * renders xhtml template with supplied data using pebble template engine.
     *
     * @param templateName
     * @param data
     * @return
     * @throws NullPointerException if templateName is null
     */
    public String evaluateTemplate(String templateName, Map<String, Object> data) {
        try {
            PebbleTemplate template = pebble.getTemplate(Objects.requireNonNull(templateName));
            StringWriter w = new StringWriter();
            template.evaluate(w, data);
            return w.toString();
        } catch (IOException | PebbleException e) {
            log.error("template evaluation failed", e);
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
            return Arrays.stream(rows).map(Escape::htmlText).collect(Collectors.joining("<br/>"));
        }
    }
}
