package ee.hitsa.ois.util;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

public class SearchUtil {

    private SearchUtil(){}

    public static <T> List<T> sort(List<T> data, Sort sort, Function<Sort.Order, Comparator<T>> sorter) {
        if(sort == null) {
            return data;
        }
        Comparator<T> comparator = null, c;
        for(Sort.Order order : sort) {
            c = sorter.apply(order);
            if(c != null) {
                if(!order.isAscending()) {
                    c = c.reversed();
                }
                if(comparator != null) {
                    comparator = comparator.thenComparing(c);
                }else {
                    comparator = c;
                }
            }
        }
        if(comparator != null) {
            data.sort(comparator);
        }
        return data;
    }

    public static <T> Page<T> sort(List<T> data, Pageable pageable, Function<Sort.Order, Comparator<T>> sorter) {
        List<T> sorted = sort(data, pageable.getSort(), sorter);
        int total = sorted.size();
        return new PageImpl<>(sorted.subList(Math.min(pageable.getOffset(), total), Math.min(pageable.getOffset()+pageable.getPageSize(), total)), pageable, total);
    }

	public static void propertyContains(Supplier<Path<String>> pathSupplier, CriteriaBuilder cb, String value, Consumer<Predicate> consumer) {
	    if(StringUtils.hasText(value)) {
	        consumer.accept(cb.like(cb.upper(pathSupplier.get()), toContains(value)));
	    }
	}

	public static String toContains(String value) {
	    return "%" + value.toUpperCase() + "%";
	}
}
