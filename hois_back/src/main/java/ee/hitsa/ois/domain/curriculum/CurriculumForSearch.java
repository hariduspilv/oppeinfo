package ee.hitsa.ois.domain.curriculum;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.SchoolWithoutLogo;

@Entity
@NamedNativeQuery(
		name = CurriculumForSearch.SEARCH_QUERY,
		query = "select id, name_en, name_et, credits, valid_thru, valid_from, "
				+ "school_id, status_code, orig_study_level_code, is_higher as higher "
				+ "from curriculum "
				+ "where "
				+ " (name_et ilike ?1  OR ?2 ) AND "
				+ " (name_en ilike ?1  OR ?3 ) AND "
				+ " (valid_from >= ?4  OR ?5 ) AND "
				+ " (valid_thru <= ?6  OR ?7 ) AND "
				+ " (credits    >= ?8  OR ?9 ) AND "
				+ " (credits    <= ?10 OR ?11) AND "
				+ " (is_joint = ?12 OR NOT ?12) AND "
				+ " (code ilike ?13 OR ?14) AND "
				+ " (mer_code ilike ?15 OR ?16) AND "
				+ " (school_id        in ?17 OR ?18) AND "
				+ " (status_code      in ?19 OR ?20) AND "
				+ " (ehis_status_code in ?21 OR ?22) AND "
				+ " (isced_class_code in ?23 OR ?24) AND "
				+ " (orig_study_level_code in ?25 OR ?26) AND "
				+ " (orig_study_level_code in ("
				    + "select classifier_code "
				    + "from classifier_connect "
				    + "where connect_classifier_code in ?27"
				+ ") OR ?28) AND"
				+ "(id in ("
					+ "select curriculum_id "
					+ "from curriculum_study_lang "
					+ "where study_lang_code in ?29"
				+ ") OR ?30) AND "
				+ "(id in ("
					+ "select cd.curriculum_id "
					+ "from curriculum_department as cd "
					+ "where cd.school_department_id in ?31"
				+ ") OR ?32)"
				+ "order by id ASC"
				, 
		resultClass = CurriculumForSearch.class)
public class CurriculumForSearch {
	
	public final static String SEARCH_QUERY = "searchCurriculums";
	
	@Id
	Long id; 
	String nameEt;
	String nameEn;
	Integer credits;
	LocalDate validThru;
	LocalDate validFrom;
	boolean higher;
	
    @ManyToOne
    SchoolWithoutLogo school;
    
    @ManyToOne
    Classifier status;
    
    @ManyToOne(optional = false)
    Classifier origStudyLevel;
    
    
    
	public boolean isHigher() {
		return higher;
	}
	public Classifier getOrigStudyLevel() {
		return origStudyLevel;
	}
	public Classifier getStatus() {
		return status;
	}
	public LocalDate getValidFrom() {
		return validFrom;
	}
	public LocalDate getValidThru() {
		return validThru;
	}
	public Integer getCredits() {
		return credits;
	}
	public Long getId() {
		return id;
	}
	public String getNameEn() {
		return nameEn;
	}
	public String getNameEt() {
		return nameEt;
	}
	public SchoolWithoutLogo getSchool() {
		return school;
	}
	@Override
	public String toString() {
		return "CurriculumForSearch [id=" + id + ", nameEt=" + nameEt + ", nameEn=" + nameEn + ", credits=" + credits
				+ ", validThru=" + validThru + ", validFrom=" + validFrom + ", school=" + school + ", status=" + status
				+ ", origStudyLevel=" + origStudyLevel + ", ]";
	}
}
