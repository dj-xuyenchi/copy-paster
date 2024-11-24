package query.genarate.gettext.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import query.genarate.gettext.config.JDBC;
import query.genarate.gettext.config.Repository;
import query.genarate.gettext.entity.CopyData;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
@Getter
@Setter
public class TextController {
    private String user = "";
    private String data;
    private String message;
    private String sendTo = "";
    Repository<CopyData> copy;
    JDBC config = new JDBC();
    CopyData copyData;

    @PostConstruct
    public void init() {
        copy = new Repository<>(CopyData.class, config.getJ(), config.getN());
    }

    public void get() {
        try {
            String queryGetCopy = copy.getQueryable()
                    .where("user_name = :userName").getQuery().toString();
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("userName", user.toUpperCase());
            copyData = copy.getObjectByQuery(queryGetCopy, parameterSource);
            if (copyData == null) {
                return;
            }
            message = copyData.getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send() {
        try {
            String querySendCopy = copy.getQueryable()
                    .where("user_name = :userName").getQuery().toString();
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("userName", sendTo.toUpperCase());
            CopyData sendTo = copy.getObjectByQuery(querySendCopy, parameterSource);
            if (sendTo == null) {
                return;
            }
            sendTo.setContent(message);
            copy.updateByObj(sendTo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onValueChange() {
        try {
            System.out.println("user - >" + user);
            String queryGetCopy = copy.getQueryable()
                    .where("user_name = :userName")
                    .getQuery()
                    .toString();
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("userName", user.toUpperCase());
            copyData = copy.getObjectByQuery(queryGetCopy, parameterSource);
            if (copyData == null) {
                System.out.println("null");
                return;
            }
            System.out.println("Messs -> "+message);
            copyData.setContent(message);
            copyData = copy.updateByObj(copyData);
            message = copyData.getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
