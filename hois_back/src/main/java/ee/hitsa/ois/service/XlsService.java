package ee.hitsa.ois.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.Translatable;
import ee.hitsa.ois.util.TranslateUtil;

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
            try(InputStream is = ReportService.class.getResourceAsStream(fullTemplatePath)) {
                if(is == null) {
                    throw new AssertionFailedException("XLS template " + fullTemplatePath + " not found");
                }
                try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                    JxlsHelper jxlsHelper = JxlsHelper.getInstance();
                    Transformer transformer = jxlsHelper.createTransformer(is, os);
                    JexlExpressionEvaluator evaluator = ((JexlExpressionEvaluator)transformer.getTransformationConfig().getExpressionEvaluator());
                    evaluator.getJexlEngine().setFunctions(Collections.singletonMap("hois", new HoisFunctions(classifierRepository)));
                    // TODO make silent for production
                    // evaluator.getJexlEngine().setSilent(true);

                    jxlsHelper.processTemplate(new Context(data), transformer);
                    return os.toByteArray();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class HoisFunctions {
        // TODO parametrized language
        private final Language lang = Language.ET;
        private final ClassifierUtil.ClassifierCache classifierCache;

        public HoisFunctions(ClassifierRepository classifierRepository) {
            classifierCache = new ClassifierUtil.ClassifierCache(classifierRepository);
        }

        public String classifierName(String code, String mainClassCode) {
            if(code == null) {
                return "";
            }
            Classifier c = classifierCache.getByCode(code, MainClassCode.valueOf(mainClassCode));
            return c != null ? name(c) : "? - " + code;
        }

        public List<Classifier> allClassifiers(String... mainClassCodes) {
            List<Classifier> classifiers = new ArrayList<>();
            if(mainClassCodes != null) {
                for(String mainClassCode : mainClassCodes) {
                    if(mainClassCode != null) {
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
    }
}
