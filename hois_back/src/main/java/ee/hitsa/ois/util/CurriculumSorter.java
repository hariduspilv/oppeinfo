package ee.hitsa.ois.util;

import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ee.hitsa.ois.domain.Classifier;
import ee.hitsa.ois.domain.CurriculumForSearch;
/**
 * TODO: try sorting in sql query
 * " order by ?1 " where "?1" is "id ASC" did not work!
 * 
 */
public class CurriculumSorter {
	public static Page<CurriculumForSearch> sort(List<CurriculumForSearch> list, Pageable p) {
	    return SearchUtil.sort(list, p, (order) -> {
			switch(order.getProperty()) {
			case "id":
				return Comparator.comparing(CurriculumForSearch::getId);
			case "nameEt":
				return Comparator.comparing(CurriculumForSearch::getNameEt, Comparator.nullsFirst(Comparator.naturalOrder()));
			case "nameEn":
				return Comparator.comparing(CurriculumForSearch::getNameEn, Comparator.nullsFirst(Comparator.naturalOrder()));
			case "credits":
				return Comparator.comparing(CurriculumForSearch::getCredits, Comparator.nullsFirst(Comparator.naturalOrder()));
			case "studyLevel":
				return Comparator.comparing(CurriculumForSearch::getOrigStudyLevel, Comparator.nullsFirst(Comparator.comparing(Classifier::getNameEt, Comparator.naturalOrder())));
			case "validFrom":
				return Comparator.comparing(CurriculumForSearch::getValidFrom, Comparator.nullsFirst(Comparator.naturalOrder()));
			case "validThru":
				return Comparator.comparing(CurriculumForSearch::getValidThru, Comparator.nullsFirst(Comparator.naturalOrder()));
			case "status":
				return Comparator.comparing(CurriculumForSearch::getStatus, Comparator.nullsFirst(Comparator.comparing(Classifier::getNameEt, Comparator.naturalOrder())));
			default:
				return null;
			}
		});
	}
}
