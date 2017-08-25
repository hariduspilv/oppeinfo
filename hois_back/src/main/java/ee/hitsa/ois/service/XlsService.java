package ee.hitsa.ois.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jxls.common.CellData;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.exception.BadConfigurationException;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.Translatable;
import ee.hitsa.ois.util.TranslateUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;

/**
 * xls generator using jxls
 */
@Service
public class XlsService {

    private static final String XLS_TEMPLATE_PATH = "/templates/";

    @Autowired
    private ClassifierRepository classifierRepository;

    public byte[] generate(String templateName, Map<String, Object> data) {
        try {
            String fullTemplatePath = XLS_TEMPLATE_PATH + templateName;
            try (InputStream is = ReportService.class.getResourceAsStream(fullTemplatePath)) {
                if (is == null) {
                    throw new AssertionFailedException("XLS template " + fullTemplatePath + " not found");
                }
                try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                    JxlsHelper jxlsHelper = JxlsHelper.getInstance();
                    Transformer transformer = jxlsHelper.createTransformer(is, os);
                    JexlExpressionEvaluator evaluator = ((JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator());
                    evaluator.getJexlEngine().setFunctions(Collections.singletonMap("hois", new HoisFunctions(classifierRepository)));
                    // TODO make silent for production
                    // evaluator.getJexlEngine().setSilent(true);

                    jxlsHelper.processTemplate(new Context(data), transformer);
                    return postProcess(os.toByteArray());
                }
            }
        } catch (IOException e) {
            throw new BadConfigurationException(String.format("XLS template %s not found", templateName), e);
        }
    }

    /**
     * Adding comment "hois:autoheight" to excel template will make that cell to
     * use text wrapping and auto height
     *
     * FIXME: Hacky solution for post processing, refactor to use AreaListener, if possible.
     */
    private static byte[] postProcess(byte[] excel) {
        try (ByteArrayInputStream is = new ByteArrayInputStream(excel)) {
            try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                JxlsHelper jxlsHelper = JxlsHelper.getInstance();
                Transformer transformer = jxlsHelper.createTransformer(is, os);
                if (transformer instanceof PoiTransformer) {
                    try(Workbook workbook = ((PoiTransformer) transformer).getWorkbook()) {
                        Sheet sheet = workbook.getSheetAt(0);
                        List<CellData> list = transformer.getCommentedCells();
                        for (CellData cellData : list) {
                            if (cellData.getCellComment().contains("hois:autoheight")) {
                                Row row = sheet.getRow(cellData.getRow());
                                if (row != null) {
                                    Cell cell = row.getCell(cellData.getCol());
                                    if (cell != null) {
                                        cell.setCellComment(null);
                                        cell.getCellStyle().setWrapText(true);
                                        row.setHeight((short) -1);
                                    }
                                }
                            }
                        }
                    }
                    jxlsHelper.processTemplate(new Context(), transformer);
                    excel = os.toByteArray();
                }

            }
        } catch (@SuppressWarnings("unused") Exception e) {
            //XXX: On exception initial excel is returned
        }

        return excel;
    }

    public static class HoisFunctions {
        // TODO parametrized language
        private final Language lang = Language.ET;
        private final ClassifierUtil.ClassifierCache classifierCache;
        private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        public HoisFunctions(ClassifierRepository classifierRepository) {
            classifierCache = new ClassifierUtil.ClassifierCache(classifierRepository);
        }

        public String classifierName(String code, String mainClassCode) {
            if (code == null) {
                return "";
            }
            Classifier c = classifierCache.getByCode(code, MainClassCode.valueOf(mainClassCode));
            return c != null ? name(c) : "? - " + code;
        }

        public List<Classifier> allClassifiers(String... mainClassCodes) {
            List<Classifier> classifiers = new ArrayList<>();
            if (mainClassCodes != null) {
                for (String mainClassCode : mainClassCodes) {
                    if (mainClassCode != null) {
                        classifiers.addAll(classifierCache.getAll(MainClassCode.valueOf(mainClassCode)));
                    }
                }
            }
            return classifiers;
        }

        public String translate(String key) {
            return TranslateUtil.translate(key, lang);
        }

        public String name(Translatable data) {
            return TranslateUtil.name(data, lang);
        }

        public String join(List<String> elements) {
            return !CollectionUtils.isEmpty(elements) ? String.join(", ", elements) : "-";
        }

        public String joinAutocomplete(List<AutocompleteResult> elements) {
            return !CollectionUtils.isEmpty(elements) ? String.join(", ", elements.stream().map(this::name).collect(Collectors.toList())) : "-";
        }

        public String date(LocalDate date) {
            return date != null ? date.format(dateFormatter) : "-";
        }

        public String dateTime(LocalDateTime dateTime) {
            return dateTime != null ? dateTime.format(dateTimeFormatter) : "-";
        }

    }
}
