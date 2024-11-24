package query.genarate.gettext.entity;

import lombok.Data;
import query.genarate.gettext.config.ID;

@Data
public class CopyData {
    @ID
    private Long id;
    private String userName;
    private String content;
}
