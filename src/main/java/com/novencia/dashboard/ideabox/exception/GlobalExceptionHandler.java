package com.novencia.dashboard.ideabox.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice  
@RestController 
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	public static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler({ SequenceException.class})
	@ResponseBody
	public ResponseEntity<?> handlePreconditionFailed(Exception e){
		return errorResponse(e, HttpStatus.PRECONDITION_FAILED);
	}
	
	@ExceptionHandler({ CustomException.class})
	@ResponseBody
	public ResponseEntity<?> handleBadRequest(Exception e){
		return errorResponse(e, HttpStatus.BAD_REQUEST);
	}	
	
	
	@ExceptionHandler({ IdNoFoundException.class})
	@ResponseBody
	public ResponseEntity<?> handleConflict(Exception e){
		return errorResponse(e, HttpStatus.CONFLICT);
	}
	
	
	
	protected ResponseEntity<String> errorResponse(Throwable throwable, HttpStatus status) {
		logger.error("error caught: " + throwable.getMessage(), throwable);
		return new ResponseEntity<String>(throwable.getMessage(), status);
	}	
	
}
