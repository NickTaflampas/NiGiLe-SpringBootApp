package cse.nigile.softdevi.model;

public class AlreadyExistsException extends RuntimeException {

	static final long serialVersionUID = -3387516993124229948L;
	
	public AlreadyExistsException() {
        super();
    }

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
