package ee.hitsa.ois.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jxls.area.Area;
import org.jxls.builder.AreaBuilder;
import org.jxls.common.AreaListener;
import org.jxls.common.AreaRef;
import org.jxls.common.CellData;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.formula.FastFormulaProcessor;
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
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.DateUtils;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.Translatable;
import ee.hitsa.ois.util.TranslateUtil;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.xls.HasPoiTransformer;
import ee.hitsa.ois.xls.HasWorkbook;

/**
 * xls generator using jxls
 */
@Service
public class XlsService {

    private static final String XLS_TEMPLATE_PATH = "/templates/";

    @Autowired
    private ClassifierService classifierService;
    
    @SuppressWarnings("resource")
    public byte[] generate(String templateName, Map<String, Object> data, List<SimpleEntry<String, AreaListener>> areaListeners) {
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
                    evaluator.getJexlEngine().setFunctions(Collections.singletonMap("hois", new HoisFunctions(classifierService)));
                    // TODO make silent for production
                    // evaluator.getJexlEngine().setSilent(true);
                    AreaBuilder areaBuilder = jxlsHelper.getAreaBuilder();
                    areaBuilder.setTransformer(transformer);
                    List<Area> xlsAreaList = areaBuilder.build();
                    Workbook workbook;
                    if (transformer instanceof PoiTransformer) {
                        PoiTransformer poiTransformer = (PoiTransformer) transformer;
                        // SuppressWarnings("resource") because if we close it then we will receive an exception about closed stream when call transformer.write()
                        // Workbook is closed in after.
                        workbook = poiTransformer.getWorkbook();
                        Map<AreaRef, Area> mappedAreas = mapAreas(xlsAreaList);
                        areaListeners.forEach(entry -> {
                            Area area = mappedAreas.get(new AreaRef(entry.getKey()));
                            if (area != null) {
                                if (entry.getValue() instanceof HasPoiTransformer && ((HasPoiTransformer) entry.getValue()).getTransformer() == null) {
                                    ((HasPoiTransformer) entry.getValue()).setTransformer(poiTransformer);
                                }
                                if (entry.getValue() instanceof HasWorkbook && ((HasWorkbook) entry.getValue()).getWorkbook() == null) {
                                    ((HasWorkbook) entry.getValue()).setWorkbook(workbook);
                                }
                                area.addAreaListener(entry.getValue());
                            }
                        });
                    } else {
                        workbook = null;
                    }
                    Context context = new Context(data);
                    FastFormulaProcessor fp = new FastFormulaProcessor();
                    for (Area xlsArea : xlsAreaList) {
                        xlsArea.applyAt(new CellRef(xlsArea.getStartCellRef().getCellName()), context);
                        xlsArea.setFormulaProcessor(fp);
                        xlsArea.processFormulas();
                    }
                    transformer.write();
                    if (workbook != null) {
                        workbook.close();
                    }
                    return postProcess(os.toByteArray());
                }
            }
        } catch (IOException e) {
            throw new BadConfigurationException(String.format("XLS template %s not found. %s", templateName, e.getMessage()), e);
        }
    }

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
                    evaluator.getJexlEngine().setFunctions(Collections.singletonMap("hois", new HoisFunctions(classifierService)));
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
                            /**
                             * FIXME: hois:autoheight comments are left in output file
                             */
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
        } catch (@SuppressWarnings("unused") Throwable e) {
            //XXX: On exception initial excel is returned
        }

        return excel;
    }
    
    private static Map<AreaRef, Area> mapAreas(List<Area> areas) {
        Map<AreaRef, Area> mappedAreas = new HashMap<>();
        Area currentArea;
        Queue<Area> queue = new LinkedList<>();
        if (!areas.isEmpty()) {
            queue.add(areas.get(0));
        }
        while (!queue.isEmpty()) {
            currentArea = queue.poll();
            mappedAreas.put(currentArea.getAreaRef(), currentArea);
            queue.addAll(currentArea.getCommandDataList().stream().flatMap(c -> c.getCommand().getAreaList().stream()).collect(Collectors.toList()));
        }
        return mappedAreas;
    }

    public static class HoisFunctions {
        // TODO parametrized language
        private final Language lang = Language.ET;
        private final ClassifierUtil.ClassifierCache classifierCache;

        public HoisFunctions(ClassifierService classifierService) {
            classifierCache = new ClassifierUtil.ClassifierCache(classifierService);
        }

        public String classifierName(String code, String mainClassCode) {
            if (code == null) {
                return "";
            }
            Classifier c = classifierCache.getByCode(code, MainClassCode.valueOf(mainClassCode));
            return c != null ? name(c) : "? - " + code;
        }
        
        public String classifierValue(String code, String mainClassCode) {
            if (code == null) {
                return "";
            }
            Classifier c = classifierCache.getByCode(code, MainClassCode.valueOf(mainClassCode));
            return c != null ? c.getValue() : "? - " + code;
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
            return !CollectionUtils.isEmpty(elements) ? String.join(", ", StreamUtil.toMappedList(this::name, elements)) : "-";
        }

        public Date date(LocalDate localDate) {
            if(localDate == null) {
                return null;
            }
            return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        }

        /**
         * For cases when date is shown with other data in the same cell
         */
        public String dateAsString(LocalDate localDate) {
            if(localDate == null) {
                return null;
            }
            return DateUtils.date(localDate);
        }

        public List<?> colspanArray(int colspan) {
            return new ArrayList<>(Collections.nCopies(colspan > 0 ? colspan - 1 : 0, null));
        }

        public Long hideZeroValue(int value) {
            if (value == 0) {
                return null;
            }
            return Long.valueOf(value);
        }
    }
    
}
