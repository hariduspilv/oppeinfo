package ee.hitsa.ois.config;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQL94Dialect;

public class CustomPostgreSQL94Dialect extends PostgreSQL94Dialect {
    
    public CustomPostgreSQL94Dialect() {
        super();
        registerHibernateType(Types.ARRAY, "array");
        registerColumnType(Types.ARRAY, "integer[]");
    }
}
