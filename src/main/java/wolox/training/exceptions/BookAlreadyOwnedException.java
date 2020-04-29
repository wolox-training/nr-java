package wolox.training.exceptions;

public class BookAlreadyOwnedException extends RuntimeException {

	private static final long serialVersionUID = 3L;

	public BookAlreadyOwnedException() {
        super();
	}
	
	public BookAlreadyOwnedException(final String message) {
        super(message);
	}

}
