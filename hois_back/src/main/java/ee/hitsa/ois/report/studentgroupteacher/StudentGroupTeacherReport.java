package ee.hitsa.ois.report.studentgroupteacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ee.hitsa.ois.enums.Absence;
import ee.hitsa.ois.enums.Language;
import ee.hitsa.ois.enums.MainClassCode;
import ee.hitsa.ois.report.ReportUtil;
import ee.hitsa.ois.util.ClassifierUtil.ClassifierCache;
import ee.hitsa.ois.util.StreamUtil;
import ee.hitsa.ois.util.TranslateUtil;
import ee.hitsa.ois.web.commandobject.report.StudentGroupTeacherCommand;
import ee.hitsa.ois.web.dto.AutocompleteResult;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.ModuleDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.ModuleTypeDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentDto;
import ee.hitsa.ois.web.dto.report.studentgroupteacher.StudentGroupTeacherDto;

public class StudentGroupTeacherReport {

    public static final String TEMPLATE_NAME = "studentgroupteacher.xhtml";
    private static final int TABLE_SIZE = 10;
    private static final int ABSENCE_COLUMNS = 4;
    
    private List<StudentGroupTeacherReportTable> tables;
    private List<StudentGroupTeacherReportJournal> journals;
    
    public StudentGroupTeacherReport(StudentGroupTeacherCommand criteria, StudentGroupTeacherDto dto,
            ClassifierCache classifierCache) {
        this(criteria, dto, classifierCache, Language.ET);
    }
    
    public StudentGroupTeacherReport(StudentGroupTeacherCommand criteria, StudentGroupTeacherDto dto,
            ClassifierCache classifierCache, Language lang) {
        
        List<String> resultColumns = StreamUtil.toMappedList(rc -> ReportUtil.resultColumnAsString(rc, Boolean.FALSE, lang),
                dto.getResultColumns());

        Map<Long, List<String>> studentResultColumns = new HashMap<>();
        for (StudentDto student : dto.getStudents()) {
            List<String> studentColumns = StreamUtil.toMappedList(rc -> ReportUtil.studentResultColumnAsString(criteria.getAbsencesPerJournals(), rc, classifierCache),
                    student.getResultColumns());
            studentResultColumns.put(student.getId(), studentColumns);
        }
        
        setTables(dto, resultColumns, studentResultColumns);
        
        List<StudentGroupTeacherReportJournal> reportJournals = new ArrayList<>();
        for (ModuleTypeDto t : dto.getModuleTypes()) {
            String type = ReportUtil.classifierName(t.getCode(), MainClassCode.KUTSEMOODUL.name(), classifierCache, lang);
            for (ModuleDto m : t.getModules()) {
                String module = TranslateUtil.name(m, lang);
                for (AutocompleteResult j : m.getJournals()) {
                    StudentGroupTeacherReportJournal journal = new StudentGroupTeacherReportJournal();
                    journal.setModuleType(type);
                    journal.setModule(module);
                    journal.setName(TranslateUtil.name(j, lang));
                    reportJournals.add(journal);
                }
            }
        }
        this.journals = reportJournals;
    }
    
    private void setTables(StudentGroupTeacherDto dto, List<String> resultColumns, Map<Long, List<String>> studentResultColumns) {
        int columnCount = resultColumns.size();
        int tableCount = columnCount / TABLE_SIZE;
        if (columnCount % TABLE_SIZE != 0) {
            tableCount++;
        }
        boolean absencesAdded = false;
        
        tables = new ArrayList<>();
        for (int i = 0; i < tableCount; i++) {
            StudentGroupTeacherReportTable table = new StudentGroupTeacherReportTable();
            List<String> tableResultColumns = resultColumns.subList(i * TABLE_SIZE, Math.min((i + 1) * TABLE_SIZE, resultColumns.size()));
            
            List<Map<String, Object>> tableStudents = new ArrayList<>();
            for (StudentDto student : dto.getStudents()) {
                Map<String, Object> tableStudent = new HashMap<>();
                
                List<String> results = studentResultColumns.get(student.getId());
                List<String> tableResults = results.subList(i * TABLE_SIZE, Math.min((i + 1) * TABLE_SIZE, results.size()));
                
                tableStudent.put("fullname", student.getFullname());
                tableStudent.put("status", student.getStatus());
                tableStudent.put("isIndividualCurriculum", student.getIsIndividualCurriculum());
                tableStudent.put("resultColumns", tableResults);
                
                if (i == tableCount - 1 && tableResults.size() + ABSENCE_COLUMNS <= TABLE_SIZE) {
                    absencesAdded = true;
                    setStudentAbsenceColumns(tableStudent, student);
                }
                tableStudents.add(tableStudent);
            }
            table.setResultColumns(tableResultColumns);
            table.setStudents(tableStudents);
            tables.add(table);
        }
        
        if (!absencesAdded) {
            StudentGroupTeacherReportTable table = new StudentGroupTeacherReportTable();
            List<Map<String, Object>> tableStudents = new ArrayList<>();
            for (StudentDto student : dto.getStudents()) {
                Map<String, Object> tableStudent = new HashMap<>();
                tableStudent.put("fullname", student.getFullname());
                setStudentAbsenceColumns(tableStudent, student);
                tableStudents.add(tableStudent);
            }
            table.setStudents(tableStudents);
            tables.add(table);
        }
    }
    
    private static void setStudentAbsenceColumns(Map<String, Object> tableStudent, StudentDto student) {
        tableStudent.put("totalAbsences", Long.valueOf(student.getAbsences().get(Absence.PUUDUMINE_P.name()).longValue()
                + student.getAbsences().get(Absence.PUUDUMINE_V.name()).longValue()
                + student.getAbsences().get(Absence.PUUDUMINE_PR.name()).longValue()));
        tableStudent.put("withoutReasonAbsences", student.getAbsences().get(Absence.PUUDUMINE_P.name()));
        tableStudent.put("withReasonAbsences",Long.valueOf(student.getAbsences().get(Absence.PUUDUMINE_V.name()).longValue()
                + student.getAbsences().get(Absence.PUUDUMINE_PR.name()).longValue()));
        tableStudent.put("beingLate", student.getAbsences().get(Absence.PUUDUMINE_H.name()));
    }

    public List<StudentGroupTeacherReportTable> getTables() {
        return tables;
    }

    public void setTables(List<StudentGroupTeacherReportTable> tables) {
        this.tables = tables;
    }

    public List<StudentGroupTeacherReportJournal> getJournals() {
        return journals;
    }

    public void setJournals(List<StudentGroupTeacherReportJournal> journals) {
        this.journals = journals;
    }

}
