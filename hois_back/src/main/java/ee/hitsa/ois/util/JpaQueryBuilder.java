package ee.hitsa.ois.util;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class JpaQueryBuilder<T> extends JpaNativeQueryBuilder {

    private final Class<T> resultClass;
    private final String alias;

    public JpaQueryBuilder(Class<T> resultClass, String alias) {
        super("from " + resultClass.getSimpleName()+ " " + alias);

        this.resultClass = resultClass;
        this.alias = alias;
    }

    @Override
    public JpaQueryBuilder<T> sort(Sort sortFields) {
        super.sort(sortFields);
        return this;
    }

    @Override
    public JpaQueryBuilder<T> sort(String... sortFields) {
        return sort(new Sort(sortFields));
    }

    @Override
    public JpaQueryBuilder<T> sort(Pageable pageable) {
        return sort(pageable != null ? pageable.getSort() : null);
    }

    public TypedQuery<T> select(EntityManager em) {
        return buildQuery(alias, em, true, null);
    }

    @Override
    public Number count(String expression, EntityManager em) {
        return query(expression, em, false, null, Number.class).getSingleResult();
    }

    @Override
    protected TypedQuery<T> buildQuery(String projection, EntityManager em, boolean ordered, Map<String, Object> additionalParameters) {
        return query(projection, em, ordered, additionalParameters, resultClass);
    }

    @Override
    protected String propertyNameToQueryName(String value) {
        return value;
    }

    private <Q> TypedQuery<Q> query(String projection, EntityManager em, boolean ordered, Map<String, Object> additionalParameters, Class<Q> resultType) {
        TypedQuery<Q> q = em.createQuery(querySql(projection, ordered), resultType);
        setQueryParameters(q, additionalParameters);
        return q;
    }
}
