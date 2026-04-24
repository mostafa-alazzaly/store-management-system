package com.store.management.system.store_management.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tools.jackson.databind.exc.UnrecognizedPropertyException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse>  handleUserNotFoundException(UserNotFoundException exc, HttpServletRequest request){
        return buildErrorResponse (exc, HttpStatus.NOT_FOUND, exc.getMessage(), request);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public  ResponseEntity <ErrorResponse> handleUsernameAlreadyExists(UsernameAlreadyExistsException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(),request);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity <ErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException exc ,HttpServletRequest request ) {

        return buildErrorResponse(exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity <ErrorResponse> handleRoleNotFound(RoleNotFoundException exc ,HttpServletRequest request ) {

        return buildErrorResponse(exc ,HttpStatus.BAD_REQUEST,exc.getMessage() ,request );
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity <ErrorResponse> handleInvalidPassword(InvalidPasswordException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.BAD_REQUEST, exc.getMessage(), request );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity <ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exc , HttpServletRequest request) {

         List<String> messages = exc.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                 .toList();

         String message = String.join(", " , messages );
        return buildErrorResponse(exc ,HttpStatus.BAD_REQUEST,message ,request );
    }

    @ExceptionHandler(InvalidRoleForAccountTypeException.class)
    public ResponseEntity <ErrorResponse> handleInvalidRoleForAccountType(InvalidRoleForAccountTypeException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.BAD_REQUEST, exc.getMessage(), request );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exc , HttpServletRequest request) {
        String message = "Invalid Request body";

        if (exc.getCause() instanceof UnrecognizedPropertyException e ){

            String field = e.getPropertyName();

            if ("accountType".equals(field)) {
                message = "accountType cannot be updated. Remove it from request.";
            } else {
                message = "Unknown field: " + field;
            }
        }
        return buildErrorResponse(exc, HttpStatus.BAD_REQUEST, message, request);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(InvalidRequestException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.BAD_REQUEST, exc.getMessage(), request );
    }

    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity <ErrorResponse> handleRoleAlreadyExists(RoleAlreadyExistsException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }

    @ExceptionHandler(UserAlreadyAssignedToEmployeeException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyAssignedToEmployee(UserAlreadyAssignedToEmployeeException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeNotFound(EmployeeNotFoundException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.NOT_FOUND, exc.getMessage(), request );
    }

    @ExceptionHandler(InvalidAccountTypeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAccountType(InvalidAccountTypeException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.BAD_REQUEST, exc.getMessage(), request );
    }
    @ExceptionHandler(UserAlreadyAssignedToCustomerException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyAssignedToCustomer(UserAlreadyAssignedToCustomerException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFound(CustomerNotFoundException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.NOT_FOUND, exc.getMessage(), request );
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse (Exception exc ,HttpStatus status ,String massage , HttpServletRequest request){
     ErrorResponse errorResponse = new ErrorResponse();
     errorResponse.setTimestamp(LocalDateTime.now());
     errorResponse.setStatus(status.value());
     errorResponse.setError(status.getReasonPhrase());
     errorResponse.setMessage(massage);
     errorResponse.setPath(request.getRequestURI());
        return new ResponseEntity <>(errorResponse,status);
    }

}
