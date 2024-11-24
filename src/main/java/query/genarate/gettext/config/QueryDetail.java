package query.genarate.gettext.config;

import lombok.Data;

@Data
public class QueryDetail {
    private StringBuilder join;
    private StringBuilder where;
    private StringBuilder groupBy;
    private StringBuilder having;
    private StringBuilder orderBy;
    private StringBuilder select;
    private StringBuilder from;

    public QueryDetail() {
        select = new StringBuilder("   SELECT  \n*");
        groupBy = new StringBuilder("\n   GROUP BY \n");
        orderBy = new StringBuilder("   ORDER BY \n");
        having = new StringBuilder("\n   Having \n");
    }
}
