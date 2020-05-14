package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid ID")
public class BookIdMismatchException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BookIdMismatchException(final String message) {
		super(message);
	}

	public BookIdMismatchException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
