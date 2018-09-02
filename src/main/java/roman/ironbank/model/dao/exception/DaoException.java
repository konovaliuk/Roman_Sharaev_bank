package roman.ironbank.model.dao.exception;

public class DaoException extends Exception{
    public DaoException() {
    }

    public DaoException(String message){super(message); }

    public DaoException(String message, Throwable cause){super(message, cause);}

    @Override
    public String getMessage() {
        return "DaoException";
    }
}
