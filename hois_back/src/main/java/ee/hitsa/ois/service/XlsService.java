package ee.hitsa.ois.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;

import ee.hitsa.ois.repository.ClassifierRepository;
import ee.hitsa.ois.util.AssertionFailedException;
import ee.hitsa.ois.util.ClassifierUtil;

public class XlsService {

    private static final String XLS_TEMPLATE_PATH = "/templates/";

    @Autowired
    private ClassifierRepository classifierRepository;

    public byte[] generate(String templateName, Map<String, Object> data) {
        try {
            try(InputStream is = ReportService.class.getResourceAsStream(XLS_TEMPLATE_PATH + templateName)) {
                if(is == null) {
                    throw new AssertionFailedException("XLS template " + XLS_TEMPLATE_PATH + templateName + " not found");
                }
                try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                    Context context = new Context(data);
                    context.putVar("classifiers", new ClassifierUtil.ClassifierCache(classifierRepository));
                    JxlsHelper.getInstance().processTemplate(is, os, context);
                    return os.toByteArray();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
