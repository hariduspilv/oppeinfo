package ee.hitsa.ois.web.dto.timetable;

import java.util.List;
import java.util.Map;

import ee.hitsa.ois.util.Translatable;

public class LessonPlanXlsDto {

    public static class LessonPlanXlsStudyPeriodDto implements Translatable {

        private Long id;
        private String nameEt;
        private String nameEn;
        private List<Short> colspanColumns;
        
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        @Override
        public String getNameEt() {
            return nameEt;
        }
        
        public void setNameEt(String nameEt) {
            this.nameEt = nameEt;
        }
        
        @Override
        public String getNameEn() {
            return nameEn;
        }
        
        public void setNameEn(String nameEn) {
            this.nameEn = nameEn;
        }
        
        public List<Short> getColspanColumns() {
            return colspanColumns;
        }
        
        public void setColspanColumns(List<Short> colspanColumns) {
            this.colspanColumns = colspanColumns;
        }
    }
    
    public static class LessonPlanXlsModuleDto implements Translatable {
        
        private String nameEt;
        private String nameEn;
        private String teacher;
        private List<LessonPlanXlsJournalDto> journals;
        private Map<String, List<Double>> hours;
        private List<Double> totalHours;
        
        @Override
        public String getNameEt() {
            return nameEt;
        }
        
        public void setNameEt(String nameEt) {
            this.nameEt = nameEt;
        }
        
        @Override
        public String getNameEn() {
            return nameEn;
        }
        
        public void setNameEn(String nameEn) {
            this.nameEn = nameEn;
        }
        
        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public List<LessonPlanXlsJournalDto> getJournals() {
            return journals;
        }

        public void setJournals(List<LessonPlanXlsJournalDto> journals) {
            this.journals = journals;
        }

        public Map<String, List<Double>> getHours() {
            return hours;
        }

        public void setHours(Map<String, List<Double>> hours) {
            this.hours = hours;
        }

        public List<Double> getTotalHours() {
            return totalHours;
        }

        public void setTotalHours(List<Double> totalHours) {
            this.totalHours = totalHours;
        }

    }
    
    public static class LessonPlanXlsJournalDto {
        
        private String nameEt;
        private List<String> teachers;
        private String studentGroups;
        private String groupProportion;
        private Map<String, List<Short>> hours;
        private List<Short> totalHours;
        
        public String getNameEt() {
            return nameEt;
        }
        
        public void setNameEt(String nameEt) {
            this.nameEt = nameEt;
        }
        
        public List<String> getTeachers() {
            return teachers;
        }
        
        public void setTeachers(List<String> teachers) {
            this.teachers = teachers;
        }
        
        public String getStudentGroups() {
            return studentGroups;
        }

        public void setStudentGroups(String studentGroups) {
            this.studentGroups = studentGroups;
        }

        public String getGroupProportion() {
            return groupProportion;
        }

        public void setGroupProportion(String groupProportion) {
            this.groupProportion = groupProportion;
        }

        public Map<String, List<Short>> getHours() {
            return hours;
        }
        
        public void setHours(Map<String, List<Short>> hours) {
            this.hours = hours;
        }
        
        public List<Short> getTotalHours() {
            return totalHours;
        }
        
        public void setTotalHours(List<Short> totalHours) {
            this.totalHours = totalHours;
        }

    }
    
    public static class LessonPlanXlsTotalsDto {
        
        private Map<String, List<Double>> hours;
        private List<Double> totalHours;
        private List<Double> totalContactHours;
        
        public Map<String, List<Double>> getHours() {
            return hours;
        }
        
        public void setHours(Map<String, List<Double>> hours) {
            this.hours = hours;
        }
        
        public List<Double> getTotalHours() {
            return totalHours;
        }
        
        public void setTotalHours(List<Double> totalHours) {
            this.totalHours = totalHours;
        }

        public List<Double> getTotalContactHours() {
            return totalContactHours;
        }

        public void setTotalContactHours(List<Double> totalContactHours) {
            this.totalContactHours = totalContactHours;
        }
    }
}
