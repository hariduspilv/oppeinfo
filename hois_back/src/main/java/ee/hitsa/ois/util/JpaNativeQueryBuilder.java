package ee.hitsa.ois.util;

import static ee.hitsa.ois.util.JpaQueryUtil.parameterAsTimestamp;
import static ee.hitsa.ois.util.JpaQueryUtil.toContains;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.NullHandling;
import org.springframework.util.StringUtils;

import ee.hitsa.ois.exception.AssertionFailedException;
import ee.hitsa.ois.web.commandobject.EntityConnectionCommand;

public class JpaNativeQueryBuilder {
    private final String from;
    private Sort sort;
    private final Map<String, Object> parameters = new HashMap<>();
    private final StringBuilder where = new StringBuilder();
    private final StringBuilder having = new StringBuilder();
    private String groupBy;
    private long limit;
    private String beforeSelect;

    public JpaNativeQueryBuilder(String from) {
        this.from = Objects.requireNonNull(from);
    }

    public JpaNativeQueryBuilder sort(Sort sortFields) {
        this.sort = sortFields;
        return this;
    }

    public JpaNativeQueryBuilder sort(String... sortFields) {
        return sort(new Sort(sortFields));
    }
    
    public JpaNativeQueryBuilder sort(Sort.Direction direction, String... sortFields) {
        return sort(new Sort(direction, sortFields));
    }

    public JpaNativeQueryBuilder sort(Pageable pageable) {
        return sort(pageable != null ? pageable.getSort() : null);
    }

    public JpaNativeQueryBuilder groupBy(String groupByFields) {
        this.groupBy = groupByFields;
        return this;
    }
    
    public JpaNativeQueryBuilder limit(long limitValue) {
        this.limit = limitValue;
        return this;
    }
    
    public JpaNativeQueryBuilder beforeSelect(String sql) {
        this.beforeSelect = sql;
        return this;
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

    public void optionalCriteria(String criteria, String name, Boolean value) {
        if(value != null) {
            filter(criteria, name, value);
        }
    }

    public void optionalCriteria(String criteria, String name, String value, Function<String, String> adjuster) {
        if(StringUtils.hasText(value)) {
            filter(criteria, name, adjuster.apply(value));
        }
    }

    public void optionalCriteria(String criteria, String name, EntityConnectionCommand value) {
        if(value != null && value.getId() != null) {
            filter(criteria, name, value.getId());
        }
    }

    public void optionalCriteria(String criteria, String name, Enum<?> value) {
        if(value != null) {
            filter(criteria, name, value.name());
        }
    }

    public void optionalCriteria(String criteria, String name, LocalDate value) {
        if(value != null) {
            filter(criteria, name, value);
        }
    }

    public void optionalCriteria(String criteria, String name, LocalDate value, Function<LocalDate, LocalDateTime> adjuster) {
        if(value != null) {
            filter(criteria, name, adjuster.apply(value));
        }
    }
    
    public void optionalCriteria(String criteria, String name, LocalTime value) {
        if (value != null) {
            filter(criteria, name, value.toString());
        }
    }

    public void optionalCriteria(String criteria, String name, LocalDateTime value) {
        if(value != null) {
            filter(criteria, name, value);
        }
    }

    public void optionalCriteria(String criteria, String name, Long value) {
        if(value != null) {
            filter(criteria, name, value);
        }
    }
    
    public void optionalContains(List<String> fields, String name, String value) {
        if(value != null && !value.isEmpty()) {
            StringBuilder sb = new StringBuilder(fields.size() > 1 ? "(" : "");
            for(String field : fields) {
                if(sb.length() > 1) {
                    sb.append(" or ");
                }
                sb.append(String.format("upper(%s) like :%s", field, name));
            }
            if(fields.size() > 1) {
                sb.append(")");
            }

            filter(sb.toString(), name, toContains(value));
        }
    }

    public void optionalContains(String field, String name, String value) {
        if(StringUtils.hasText(value)) {
            optionalContains(Collections.singletonList(field), name, value);
        }
    }

    public void requiredCriteria(String criteria, String name, Collection<?> value) {
        filter(criteria, name, value != null && !value.isEmpty() ? value : null);
    }

    public void requiredCriteria(String criteria, String name, EntityConnectionCommand value) {
        filter(criteria, name, value != null ? value.getId() : null);
    }

    public void requiredCriteria(String criteria, String name, Enum<?> value) {
        filter(criteria, name, value != null ? value.name() : null);
    }

    public void requiredCriteria(String criteria, String name, LocalDate value) {
        filter(criteria, name, value);
    }

    public void requiredCriteria(String criteria, String name, LocalDateTime value) {
        filter(criteria, name, value);
    }

    public void requiredCriteria(String criteria, String name, Long value) {
        filter(criteria, name, value);
    }

    public void requiredCriteria(String criteria, String name, String value) {
        filter(criteria, name, StringUtils.hasText(value) ? value : null);
    }

    public void requiredCriteria(String criteria, String name, Boolean value) {
        filter(criteria, name, value);
    }

    public void parameter(String name, Object value) {
        if(value == null) {
            throw new AssertionFailedException("Parameter value is missing");
        }
        parameters.put(Objects.requireNonNull(name, "Parameter name is missing"), value);
    }

    public void filter(String filter) {
        if(where.length() > 0) {
            where.append(" and ");
        }
        where.append(Objects.requireNonNull(filter));
    }

    public void having(String criteria, String name, Object value) {
        parameter(name, value);
        having(criteria);
    }
    
    public void having(String filter) {
        if(having.length() > 0) {
            having.append(" and ");
        }
        having.append(Objects.requireNonNull(filter));
    }

    public Query select(String projection, EntityManager em) {
        return select(projection, em, null);
    }

    public Query select(String projection, EntityManager em, Map<String, Object> additionalParameters) {
        return buildQuery(querySql(projection, true), em, additionalParameters);
    }
    
    public Query select(String projection, EntityManager em, Map<String, Object> additionalParameters, boolean distinct) {
        return buildQuery(querySql(projection, true, distinct), em, additionalParameters);
    }
    
    public Query select(String projection, EntityManager em, boolean distinct) {
        return select(projection, em, null, distinct);
    }

    public Number count(EntityManager em) {
        return count(em, null);
    }
    
    public Number count(EntityManager em, Map<String, Object> additionalParameters) {
        return count("count(*)", em, additionalParameters);
    }

    public Number count(String expression, EntityManager em) {
        return count(expression, em, null);
    }
    
    public Number count(String expression, EntityManager em, Map<String, Object> additionalParameters) {
        return count(expression, null, em, additionalParameters);
    }

    public Number count(String expression, String selectExpression, EntityManager em, Map<String, Object> additionalParameters) {
        String querySql;
        if(StringUtils.hasText(groupBy)) {
            querySql = "select " + Objects.requireNonNull(expression) +" from (" + querySql(selectExpression != null ? selectExpression : "1", false) + ") wrapped_count_query";
        } else {
            querySql = querySql(expression, false);
        }
        return (Number)buildQuery(querySql, em, additionalParameters).getSingleResult();
    }
    
    public String querySql(String projection, boolean ordered) {
        return querySql(projection, ordered, false);
    }

    public String querySql(String projection, boolean ordered, boolean distinct) {
        StringBuilder sql = new StringBuilder();
        if (beforeSelect != null) {
            sql.append(beforeSelect);
        }
        sql.append(sql.length() > 0 ? " select " : "select ");
        if (distinct) {
            sql.append("distinct ");
        }
        sql.append(Objects.requireNonNull(projection));
        sql.append(' ');
        sql.append(from);
        if(where.length() > 0) {
            sql.append(" where ");
            sql.append(where);
        }

        if(StringUtils.hasText(groupBy)) {
            sql.append(" group by ");
            sql.append(groupBy);
        }
        
        if(StringUtils.hasText(having)) {
            sql.append(" having ");
            sql.append(having);
        }

        if(sort != null && ordered) {
            StringBuilder orderBy = new StringBuilder();
            for(Sort.Order order : sort) {
                if(orderBy.length() > 0) {
                    orderBy.append(", ");
                }
                orderBy.append(propertyNameToQueryName(order.getProperty()));
                orderBy.append(order.isAscending() ? "" : " desc");
                NullHandling nullhandling = order.getNullHandling();
                if(NullHandling.NULLS_FIRST.equals(nullhandling)) {
                    orderBy.append(" NULLS FIRST");
                } else if(NullHandling.NULLS_LAST.equals(nullhandling)) {
                    orderBy.append(" NULLS LAST");
                }
            }
            if(orderBy.length() > 0) {
                sql.append(" order by ");
                sql.append(orderBy);
            }
        }
        if (limit > 0L) {
            sql.append(" limit ");
            sql.append(limit);
        }
        return sql.toString();
    }

    public Map<String, Object> queryParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    public void validNowCriteria(String fromField, String thruField) {
        LocalDate now = LocalDate.now();
        requiredCriteria("(" + fromField + " is null or " + fromField + " <= :now)", "now", now);
        filter("(" + thruField + " is null or " + thruField + " >= :now)");
    }

    protected Query buildQuery(String querySql, EntityManager em, Map<String, Object> additionalParameters) {
        Query q = em.createNativeQuery(querySql);
        setQueryParameters(q, additionalParameters);
        return q;
    }

    protected void setQueryParameters(Query q, Map<String, Object> additionalParameters) {
        for(Map.Entry<String, Object> me : parameters.entrySet()) {
            q.setParameter(me.getKey(), convertQueryParameter(me.getValue()));
        }
        if(additionalParameters != null) {
            for(Map.Entry<String, Object> me : additionalParameters.entrySet()) {
                q.setParameter(me.getKey(), convertQueryParameter(me.getValue()));
            }
        }
    }

    protected Object convertQueryParameter(Object value) {
        // jpa native query does not accept LocalDate(Time) objects
        if(value instanceof LocalDate) {
            return parameterAsTimestamp((LocalDate)value);
        }
        if(value instanceof LocalDateTime) {
            return parameterAsTimestamp((LocalDateTime)value);
        }
        return value;
    }

    protected String propertyNameToQueryName(String value) {
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

    private void filter(String filter, String name, Object value) {
        parameter(name, value);
        filter(filter);
    }
}
