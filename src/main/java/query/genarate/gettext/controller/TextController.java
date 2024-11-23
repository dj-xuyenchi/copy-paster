package query.genarate.gettext.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

@ViewScoped
@ManagedBean
public class TextController {
    private String user;
    private String data;

    public String getName() {
        return user;
    }

    public String getData() {
        return data;
    }
}
