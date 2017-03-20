package ee.hitsa.ois.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

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

    public static <T> Page<T> pagingResult(Query query, Pageable pageable, Supplier<Number> countSupplier) {
        @SuppressWarnings("unchecked")
        List<T> content = query.setFirstResult(pageable.getOffset())
                .setMaxResults(pageable.getPageSize()).getResultList();

        int fetched = content.size();
        long total;
        if((fetched > 0 || pageable.getPageNumber() == 0) && fetched < pageable.getPageSize()) {
            // on last page, can just calculate total
            total = pageable.getOffset() + fetched;
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

    public static class NativeQueryBuilder {
        private final String from;
        private final Sort sort;
        private final Map<String, Object> parameters = new HashMap<>();
        private final StringBuilder where = new StringBuilder();

        public NativeQueryBuilder(String from) {
            this(from, (Sort)null);
        }

        public NativeQueryBuilder(String from, Sort sort) {
            this.from = Objects.requireNonNull(from);
            this.sort = sort;
        }

        public NativeQueryBuilder(String from, Pageable pageable) {
            this(from, pageable != null ? pageable.getSort() : null);
        }

        public void optionalCriteria(String criteria, String name, Collection<?> value) {
            if(value != null && !value.isEmpty()) {
                filter(criteria, name, value);
            }
        }

        public void optionalCriteria(String criteria, String name, String value) {
            if(StringUtils.hasText(value)) {
                filter(criteria, name, value);
            }
        }

        public void optionalCriteria(String criteria, String name, EntityConnectionCommand value) {
            if(value != null && value.getId() != null) {
                filter(criteria, name, value.getId());
            }
        }

        public void optionalCriteria(String criteria, String name, LocalDate value) {
            if(value != null) {
                filter(criteria, name, Timestamp.valueOf(LocalDateTime.of(value, LocalTime.MIN)));
            }
        }

        public void optionalCriteria(String criteria, String name, LocalDateTime value) {
            if(value != null) {
                filter(criteria, name, Timestamp.valueOf(value));
            }
        }

        public void optionalCriteria(String criteria, String name, Long value) {
            if(value != null) {
                filter(criteria, name, value);
            }
        }

        public void optionalContains(String field, String name, String value) {
            if(StringUtils.hasText(value)) {
                filter(String.format("upper(%s) like :%s", field, name), name, "%"+value.toUpperCase()+"%");
            }
        }

        public void requiredCriteria(String criteria, String name, Collection<?> value) {
            if(value == null || value.isEmpty()) {
                throw new AssertionFailedException("Required criteria is missing");
            }
            filter(criteria, name, value);
        }

        public void requiredCriteria(String criteria, String name, LocalDate value) {
            filter(criteria, name, Timestamp.valueOf(LocalDateTime.of(value, LocalTime.MIN)));
        }

        public void requiredCriteria(String criteria, String name, LocalDateTime value) {
            filter(criteria, name, Timestamp.valueOf(value));
        }

        public void requiredCriteria(String criteria, String name, Long value) {
            filter(criteria, name, value);
        }

        public void requiredCriteria(String criteria, String name, String value) {
            if(!StringUtils.hasText(value)) {
                throw new AssertionFailedException("Required criteria is missing");
            }
            filter(criteria, name, value);
        }

        public void parameter(String name, Object value) {
            parameters.put(Objects.requireNonNull(name), Objects.requireNonNull(value));
        }

        public void filter(String filter) {
            if(where.length() > 0) {
                where.append(" and ");
            }
            where.append(Objects.requireNonNull(filter));
        }

        private void filter(String filter, String name, Object value) {
            filter(filter);
            parameter(name, value);
        }

        public Query select(String projection, EntityManager em) {
            return select(projection, em, null);
        }

        public Query select(String projection, EntityManager em, Map<String, Object> additionalParameters) {
            return buildQuery(projection, em, true, additionalParameters);
        }

        public Number count(EntityManager em) {
            return (Number)buildQuery("count(*)", em, false, null).getSingleResult();
        }

        private Query buildQuery(String projection, EntityManager em, boolean ordered, Map<String, Object> additionalParameters) {
            StringBuilder sql = new StringBuilder("select ");
            sql.append(Objects.requireNonNull(projection));
            sql.append(' ');
            sql.append(from);
            if(where.length() > 0) {
                sql.append(" where ");
                sql.append(where);
            }

            if(sort != null && ordered) {
                StringBuilder orderBy = new StringBuilder();
                for(Sort.Order order : sort) {
                    if(orderBy.length() > 0) {
                        orderBy.append(", ");
                    }
                    orderBy.append(camelCaseToUnderScore(order.getProperty()));
                    orderBy.append(order.isAscending() ? "" : " desc");
                }
                if(orderBy.length() > 0) {
                    sql.append(" order by ");
                    sql.append(orderBy);
                }
            }

            Query q = em.createNativeQuery(sql.toString());

            for(Map.Entry<String, Object> me : parameters.entrySet()) {
                q.setParameter(me.getKey(), me.getValue());
            }
            if(additionalParameters != null) {
                for(Map.Entry<String, Object> me : additionalParameters.entrySet()) {
                    q.setParameter(me.getKey(), me.getValue());
                }
            }

            return q;
        }

        private static String camelCaseToUnderScore(String value) {
            StringBuilder sb = new StringBuilder();
            for(int i = 0, cnt = value.length(); i < cnt; i++) {
                char ch = value.charAt(i);
                if(Character.isUpperCase(ch)) {
                    sb.append('_');
                    sb.append(Character.toLowerCase(ch));
                } else {
                    sb.append(ch);
                }
            }
            return sb.toString();
        }
    }

    public static Boolean resultAsBoolean(Object value, int index) {
        value = (((Object[])value)[index]);
        return (Boolean)value;
    }

    public static Integer resultAsInteger(Object value, int index) {
        value = (((Object[])value)[index]);
        return value != null ? Integer.valueOf(((Number)value).intValue()) : null;
    }

    public static LocalDate resultAsLocalDate(Object value, int index) {
        value = (((Object[])value)[index]);
        if(value instanceof java.sql.Date) {
            return ((java.sql.Date)value).toLocalDate();
        }
        return value != null ? ((java.sql.Timestamp)value).toLocalDateTime().toLocalDate() : null;
    }

    public static LocalDateTime resultAsLocalDateTime(Object value, int index) {
        value = (((Object[])value)[index]);
        return value != null ? ((java.sql.Timestamp)value).toLocalDateTime() : null;
    }

    public static Long resultAsLong(Object value, int index) {
        value = (((Object[])value)[index]);
        return value != null ? Long.valueOf(((Number)value).longValue()) : null;
    }

    public static String resultAsString(Object value, int index) {
        return (String)(((Object[])value)[index]);
    }
}
