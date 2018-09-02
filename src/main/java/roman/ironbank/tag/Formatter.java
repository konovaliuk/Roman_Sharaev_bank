package roman.ironbank.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.text.DecimalFormat;

public class Formatter extends SimpleTagSupport{
    private String format;
    private String number;

    public Formatter() {
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public void doTag() throws JspException, IOException {
        try{
            double amount = Double.parseDouble(number);
            DecimalFormat formater = new DecimalFormat(format);
            String formatedNum = formater.format(amount);
            getJspContext().getOut().write(formatedNum);
        }catch (Exception e){
            e.getStackTrace();
            throw new SkipPageException();
        }
    }
}
