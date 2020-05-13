package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid ID")
public class UserIdMismatchException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserIdMismatchException(final String message) {
		super(message);
	}

	public UserIdMismatchException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
