package ee.hitsa.ois.service;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ee.hitsa.ois.domain.Certificate;
import ee.hitsa.ois.domain.student.Student;
import ee.hitsa.ois.enums.CertificateType;
import ee.hitsa.ois.report.certificate.CertificateReport;
import ee.hitsa.ois.repository.StudentRepository;
import ee.hitsa.ois.util.ClassifierUtil;
import ee.hitsa.ois.util.EntityUtil;
import ee.hitsa.ois.validation.ValidationFailedException;
import ee.hitsa.ois.web.commandobject.CertificateContentCommand;

@Service
public class CertificateContentService {
    
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudyYearService studyYearService;
    @Autowired
    private TemplateService templateService;

    public String generate(CertificateContentCommand command) {
        Student student = studentRepository.findOne(command.getStudent());
        CertificateReport report = CertificateReport.of(student);
        report.setStudyYear(studyYearService.getCurrentStudyYear(student.getSchool()).getYear().getNameEt());
        return templateService.evaluateTemplate(getTemplateName(command.getType()), Collections.singletonMap("content", report));
    }

    private String getTemplateName(String type) {
        // TODO Auto-generated method stub
        return "TOEND_LIIK_OPI.xhtml";
    }

    public String generateFor(Certificate certificate) {
        CertificateReport report = CertificateReport.of(certificate.getStudent());
        report.setStudyYear(studyYearService.getCurrentStudyYear(certificate.getStudent().getSchool()).getYear().getNameEt());
        
        return reportToJson(report);
    }
    
    private String reportToJson(CertificateReport report) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = null;
        try {
            jsonInString = mapper.writeValueAsString(report);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonInString;
    }

    public String retrieve(Certificate certificate) {
        if(ClassifierUtil.equals(CertificateType.TOEND_LIIK_MUU, certificate.getType())) {
            throw new ValidationFailedException("muu.type.not.allowed");
        }
        // TODO: If block below required for old data. Delete when database is cleared
        if(!certificate.getContent().contains("{")) {
            return certificate.getContent();
        }
        CertificateReport report = jsonToReport(certificate);
        return templateService.evaluateTemplate(getTemplateName(EntityUtil.getCode(certificate.getType())), Collections.singletonMap("content", report));
    }

    private CertificateReport jsonToReport(Certificate certificate) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = certificate.getContent();
        CertificateReport report = null;
        try {
            report = mapper.readValue(jsonInString, CertificateReport.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return report;
    }
}
