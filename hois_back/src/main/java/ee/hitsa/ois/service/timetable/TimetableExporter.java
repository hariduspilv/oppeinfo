package ee.hitsa.ois.service.timetable;

import java.io.ByteArrayInputStream;
import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import ee.hitsa.ois.domain.school.School;
import ee.hitsa.ois.enums.SchoolTimetableType;
import ee.hitsa.ois.exception.HoisException;
import ee.hitsa.ois.repository.TimetableObjectRepository;
import ee.hitsa.ois.service.TimetableService;
import ee.hitsa.ois.service.security.HoisUserDetails;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.dto.timetable.NameAndCode;
import ee.hitsa.ois.web.dto.timetable.TimetableImportDto;
import ee.hitsa.ois.xml.timetable.asc.AscTimetable;

@Transactional
@Component
public class TimetableExporter {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private EntityManager em;
    @Autowired
    private TimetableService service;
    @Autowired
    private TimetableObjectRepository repository;

    private Unmarshaller ascUnmarshaller;

    public TimetableExporter() {
        try {
            ascUnmarshaller = JAXBContext.newInstance(AscTimetable.class).createUnmarshaller();
        } catch (JAXBException e) {
            LOG.error("ASC Unmarshaller could not be initialized", e);
        }
    }

    public Map<String, List<NameAndCode>> importXml(HoisUserDetails user, TimetableImportDto dto) {
        School school = em.getReference(School.class, user.getSchoolId());
        if (ClassifierUtil.oneOf(school.getTimetable(), SchoolTimetableType.TIMETABLE_ASC)) {
            if (ascUnmarshaller == null) {
                LOG.error("Unmarshaller is not available for this document. Error happened during initialization.");
                throw new ValidationFailedException("Cannot read file. Ask an administator.");
            }
            AscTimetable timetable = null;
            try {
                timetable = (AscTimetable) ascUnmarshaller
                        .unmarshal(new ByteArrayInputStream(dto.getOisFile().getFdata()));
            } catch (JAXBException e) {
                // Happened error, document is not valid
                LOG.error("Exception during unmarshalling document", e);
            }
            AscTimetableImport ascImport = new AscTimetableImport(em, user, service);
            return ascImport.processData(school, dto, timetable).stream()
                    .collect(Collectors.groupingBy(m -> m.getUniid(), LinkedHashMap::new, Collectors.toList()));
        } else if (ClassifierUtil.oneOf(school.getTimetable(), SchoolTimetableType.TIMETABLE_UNTIS)) {
            Document document = null;
            try {
                document = byteArrayToDocument(dto.getOisFile().getFdata());
            } catch (@SuppressWarnings("unused") Exception e) {
                throw new HoisException("timetable.importDialog.messages.invalidDocument");
            }
            if (document == null) {
                throw new HoisException("timetable.importDialog.messages.invalidDocument");
            }
            UntisTimetableImport untisImport = new UntisTimetableImport(user, em, service, repository);
            return untisImport.processData(school, dto, document).stream()
                    .collect(Collectors.groupingBy(m -> m.getName(), LinkedHashMap::new, Collectors.toList()));
        }
        return Collections.emptyMap();
    }

    private static Document byteArrayToDocument(byte[] bytes) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(bytes), "document");
    }
}
