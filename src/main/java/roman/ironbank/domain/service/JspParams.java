package roman.ironbank.domain.service;

import java.util.HashMap;

public class JspParams {
    private HashMap<String, String> params = new HashMap<>();

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }
}
