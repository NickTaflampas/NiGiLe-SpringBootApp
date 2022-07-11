package cse.nigile.softdevi.model;

public class DoesNotExistException extends RuntimeException {

	static final long serialVersionUID = -3387516993124229948L;
	
	public DoesNotExistException() {
        super();
    }

    public DoesNotExistException(String message) {
        super(message);
    }

    public DoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public DoesNotExistException(Throwable cause) {
        super(cause);
    }
}
