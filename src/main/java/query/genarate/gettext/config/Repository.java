package query.genarate.gettext.config;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Repository<T> implements ISQLAction<T> {
    Class<T> c;
    String tableName;
    String idField;
    List<String> fieldNamesSQL;
    List<String> fieldNames;
    JdbcTemplate j;
    NamedParameterJdbcTemplate n;

    public Repository(Class<T> c, JdbcTemplate j, NamedParameterJdbcTemplate n) {
        this.j = j;
        this.n = n;
        tableName = convertCamelToSnake(c.getSimpleName());
        this.c = c;
        fieldNames = new ArrayList<>();
        fieldNamesSQL = new ArrayList<>();
        int countId = 0;
        for (Field field : c.getDeclaredFields()) {
            if (field.isAnnotationPresent(ID.class)) {
                idField = convertCamelToSnake(field.getName());
                countId++;
            }
            fieldNamesSQL.add(convertCamelToSnake(field.getName()));
            fieldNames.add(field.getName());
        }
        if (countId == 0) {
            throw new RuntimeException("Class khong khai bao khoa chinh!");
        }
        if (countId > 1) {
            throw new RuntimeException("Class co nhieu hon 1 khoa chinh!");
        }

    }


    @Override
    public T getById(String id) {
        String query = "select * from " + tableName +
                " where " + idField + " = :id";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        try {
            return n.queryForObject(query, parameterSource, new BeanPropertyRowMapper<>(c));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public T updateByObj(T t) throws Exception {
        StringBuilder query = new StringBuilder("UPDATE " + tableName + "\n set ");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        for (int i = 0; i < fieldNames.size(); i++) {
            if (fieldNamesSQL.get(i).equals(idField)) {
                continue;
            }
            Field nameField = c.getDeclaredField(fieldNames.get(i));
            nameField.setAccessible(true);
            String value = String.valueOf(nameField.get(t));
            query.append(fieldNamesSQL.get(i))
                    .append(" = :")
                    .append(nameField.getName())
                    .append(" ,\n");
            parameterSource.addValue(nameField.getName(), value);
        }
        // xoa dau , cuoi
        query.deleteCharAt(query.length() - 2);
        Field nameField = c.getDeclaredField(idField);
        nameField.setAccessible(true);
        String value = String.valueOf(nameField.get(t));
        query.append(" WHERE ")
                .append(idField)
                .append(" = :")
                .append(idField);
        parameterSource.addValue(idField, value);
        System.out.println(query.toString());
        try {
            int i = n.update(query.toString(), parameterSource);
            System.out.println("res -> " + i);
            return getById(value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(String id) {
        // return "delete from " + tableName + " where " + idField + " = :id";
    }

    @Override
    public T getObjectByQuery(String query, MapSqlParameterSource parameterSource) {
        try {
            return n.queryForObject(query, parameterSource, new BeanPropertyRowMapper<>(c));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Queryable getQueryable() {
        StringBuilder initQuery = new StringBuilder();
        initQuery
                .append("   FROM  ")
                .append(tableName)
                .append(" ");
        QueryDetail queryDetail = new QueryDetail();
        queryDetail.setFrom(initQuery);
        return new Queryable(queryDetail);
    }


    public static String convertCamelToSnake(String camelCase) {
        return camelCase
                .replaceAll("([a-z])([A-Z])", "$1_$2")
                .toLowerCase();
    }
}
