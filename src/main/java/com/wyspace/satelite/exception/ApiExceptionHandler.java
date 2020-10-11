package com.wyspace.satelite.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
	
	@ExceptionHandler(value = {BandwithLimitExceededException.class})
	public ResponseEntity<Object> handleBandwithLimitExceededException(BandwithLimitExceededException e){
		HttpStatus notFound = HttpStatus.BANDWIDTH_LIMIT_EXCEEDED;
		ApiException apiException = new ApiException(
				e.getMessage(),
                e, 
				notFound, 
				ZonedDateTime.now(ZoneId.of("Z"))
		);
		return new ResponseEntity<>(apiException, notFound);
	}
	
	@ExceptionHandler(value = {GenaralException.class})
	public ResponseEntity<Object> handleGenaralException(GenaralException e){
		HttpStatus notFound = HttpStatus.BANDWIDTH_LIMIT_EXCEEDED;
		ApiException apiException = new ApiException(
				e.getMessage(),
                e, 
				notFound, 
				ZonedDateTime.now(ZoneId.of("Z"))
		);
		return new ResponseEntity<>(apiException, notFound);
	}

}
