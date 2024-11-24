package query.genarate.gettext.config;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.List;

public interface ISQLAction<T> {
    T getById(String id);

    T updateByObj(T t) throws Exception;

    void deleteById(String id);

    T getObjectByQuery(String query, MapSqlParameterSource parameterSource);

    Queryable getQueryable();
}

