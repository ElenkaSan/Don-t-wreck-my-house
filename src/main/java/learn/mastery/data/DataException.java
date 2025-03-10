package learn.mastery.data;

//DataAccessException is a checked exception since it extends Exception.
public class DataException extends RuntimeException {
    public DataException(String message) {
        super(message);
    }

    public DataException(Throwable cause) {
        super(cause);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }
}
