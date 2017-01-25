package ee.hitsa.ois.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public abstract class JpaQueryUtil {

    public static <T, E> Page<T> query(Class<T> resultClass, Class<E> entityClass, Specification<E> specification, Pageable pageable, EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> dq = cb.createQuery(resultClass);
        Root<E> root = dq.from(entityClass);
        Predicate filter = specification.toPredicate(root, dq, cb);
        if(filter != null) {
            dq = dq.where(filter);
        }

        List<Order> order = toOrderBy(cb, pageable.getSort(), s -> root.get(s));
        TypedQuery<T> tq = em.createQuery(dq.orderBy(order));
        return pagingResult(tq, pageable, () -> countQuery(entityClass, em, filter));
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
}
