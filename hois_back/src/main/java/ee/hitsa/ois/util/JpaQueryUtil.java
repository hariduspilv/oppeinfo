package ee.hitsa.ois.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public abstract class JpaQueryUtil {

    public static <T, E> Page<T> query(Class<T> resultClass, Class<E> entityClass, Specification<E> specification, Pageable pageable, EntityManager entityManager) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> dq = cb.createQuery(resultClass);
        Root<E> root = dq.from(entityClass);
        Predicate filter = specification.toPredicate(root, dq, cb);
        if(filter != null) {
            dq = dq.where(filter);
        }

        List<Order> order = toOrderBy(cb, pageable.getSort(), s -> {
            Path<?> p = root;
            for(String property : StringUtils.delimitedListToStringArray(s, ".")) {
                Path<?> maybe = null;
                if(p instanceof From) {
                    for(Join<?, ?> j : ((From<?, ?>)p).getJoins()) {
                        if(property.equals(j.getAttribute().getName())) {
                            maybe = j;
                            break;
                        }
                    }
                }
                p = maybe != null ? maybe :  p.get(property);
            }
            return p;
        });
        TypedQuery<T> tq = entityManager.createQuery(dq.orderBy(order));
        return pagingResult(tq, pageable, () -> countQuery(entityClass, entityManager, filter));
    }

    public static <T> Page<T> pagingResult(JpaNativeQueryBuilder qb, String select, EntityManager em, Pageable pageable) {
        return pagingResult(qb.select(select, em), pageable, () -> qb.count(em));
    }

    public static <T> Page<T> pagingResult(JpaNativeQueryBuilder qb, String select, Map<String, Object> additionalParameters, 
            EntityManager em, Pageable pageable) {
        return pagingResult(qb.select(select, em, additionalParameters), pageable, () -> qb.count(em, additionalParameters));
    }

    public static <T> Page<T> pagingResult(JpaQueryBuilder<T> qb, EntityManager em, Pageable pageable) {
        return pagingResult(qb.select(em), pageable, () -> qb.count(em));
    }

    public static <T> Page<T> pagingResult(Query query, Pageable pageable, Supplier<Number> countSupplier) {
        @SuppressWarnings("unchecked")
        List<T> content = query.setFirstResult(pageable.getOffset())
                .setMaxResults(pageable.getPageSize()).getResultList();

        int fetched = content.size();
        long total;
        if((fetched > 0 || pageable.getPageNumber() == 0) && fetched < pageable.getPageSize()) {
            // on last page, can just calculate total
            total = (long) pageable.getOffset() + fetched;
        } else {
            // should query total
            total = countSupplier.get().longValue();
        }
        return new PageImpl<>(content, pageable, total);
    }
    
    public static Long countQuery(Class<?> entityClass, EntityManager em, Predicate... filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq = cq.select(cb.count(cq.from(entityClass)));
        if(filter != null) {
            cq = cq.where(filter);
        }
        return em.createQuery(cq).getSingleResult();
    }

    public static List<Order> toOrderBy(CriteriaBuilder cb, Sort sort, Function<String, Expression<?>> supplier) {
        List<Order> jpaOrders = new ArrayList<>();
        if(sort != null) {
            for(Sort.Order order : sort) {
                Expression<?> jpaOrder = supplier.apply(order.getProperty());
                if(jpaOrder != null) {
                    jpaOrders.add(order.isAscending() ? cb.asc(jpaOrder) : cb.desc(jpaOrder));
                }
            }
        }
        return jpaOrders;
    }

    public static <ID, T> List<T> loadRelationChilds(Class<T> resultClass, List<ID> data, EntityManager entityManager, String... relationPath) {
        if(data.isEmpty()) {
            return Collections.emptyList();
        }
        CriteriaQuery<T> dq = entityManager.getCriteriaBuilder().createQuery(resultClass);
        Path<T> path = dq.from(resultClass);
        for(String property : relationPath) {
            path = path.get(property);
        }
        return entityManager.createQuery(dq.where(path.in(data))).getResultList();
    }

    public static void propertyContains(Supplier<Path<String>> pathSupplier, CriteriaBuilder cb, String value, Consumer<Predicate> consumer) {
        if(StringUtils.hasText(value)) {
            consumer.accept(cb.like(cb.upper(pathSupplier.get()), toContains(value)));
        }
    }

    public static String toContains(String value) {
        return "%" + value.trim().toUpperCase() + "%";
    }

    public static Timestamp parameterAsTimestamp(LocalDate value) {
        return Timestamp.valueOf(LocalDateTime.of(value, LocalTime.MIN));
    }

    public static Timestamp parameterAsTimestamp(LocalDateTime value) {
        return Timestamp.valueOf(value);
    }

    public static Boolean resultAsBoolean(Object row, int index) {
        Object value = getValue(row, index);
        return (Boolean)value;
    }

    public static BigDecimal resultAsDecimal(Object row, int index) {
        Object value = getValue(row, index);
        return (BigDecimal)value;
    }
    
    /**
     * For this to work, query parameter type needs to be ArrayUserType
     * Scale needs to be set
     * Dialect needs to be CustomPostgreSQL94Dialect
     * @param row
     * @param index
     * @return
     */
    public static List<String> resultAsStringList(Object row, int index) {
        Object value = getValue(row, index);
        return value != null ? Arrays.asList((String[])value) : null;
    }

    public static Integer resultAsInteger(Object row, int index) {
        Object value = getValue(row, index);
        return value != null ? Integer.valueOf(((Number)value).intValue()) : null;
    }

    public static Short resultAsShort(Object row, int index) {
        Object value = getValue(row, index);
        return value != null ? Short.valueOf(((Number)value).shortValue()) : null;
    }

    public static LocalDate resultAsLocalDate(Object row, int index) {
        Object value = getValue(row, index);
        if(value instanceof java.sql.Date) {
            return ((java.sql.Date)value).toLocalDate();
        }
        return value != null ? ((java.sql.Timestamp)value).toLocalDateTime().toLocalDate() : null;
    }

    public static LocalDateTime resultAsLocalDateTime(Object row, int index) {
        Object value = getValue(row, index);
        return value != null ? ((java.sql.Timestamp)value).toLocalDateTime() : null;
    }

    public static LocalTime resultAsLocalTime(Object row, int index) {
        Object value = getValue(row, index);
        return value != null ? ((java.sql.Time)value).toLocalTime() : null;
    }

    public static Long resultAsLong(Object row, int index) {
        Object value = getValue(row, index);
        return value != null ? Long.valueOf(((Number)value).longValue()) : null;
    }

    public static String resultAsString(Object row, int index) {
        return (String)(getValue(row, index));
    }
    
    /**
     * For cases when "string_agg" is used to generated string object.
     * 
     * @param row
     * @param index
     * @param delimeter
     * @return
     */
    public static List<String> resultAsStringList(Object row, int index, String delimeter) {
        String value = (String)(getValue(row, index));
        return value == null || StringUtils.isEmpty(value) ? Collections.emptyList() : Arrays.asList(value.split(delimeter));
    }

    private static Object getValue(Object row, int index) {
        Object value = row;
        if (value instanceof Object[]) {
            value = ((Object[])value)[index];
        }
        return value;
    }
}
