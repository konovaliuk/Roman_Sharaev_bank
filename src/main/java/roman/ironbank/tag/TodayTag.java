package roman.ironbank.tag;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TodayTag extends SimpleTagSupport {
    private String mFormat;

    public void setmFormat(String mFormat) {
        this.mFormat = mFormat;
    }

    public void doTag() throws JspException {
        try{
            Date today = new Date();
            SimpleDateFormat dateFormatter = new SimpleDateFormat(mFormat);
            getJspContext().getOut().write(dateFormatter.format(today));
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }

    }
}
