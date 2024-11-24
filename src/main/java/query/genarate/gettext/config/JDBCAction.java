package query.genarate.gettext.config;

import java.util.List;

public interface JDBCAction {
    List<Object> getList(String sql);
}
