package app.udala.pos.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NotFoundHandler {
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
	public ErrorMessageDto handle(ResourceNotFoundException exception) {
		return new ErrorMessageDto(404, exception.getMessage());
	}
}
