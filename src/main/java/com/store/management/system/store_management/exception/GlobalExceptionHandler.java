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
    @ExceptionHandler(TaxNumberAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleTaxNumberAlreadyExists(TaxNumberAlreadyExistsException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }

    @ExceptionHandler(SupplierNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSupplierNotFound(SupplierNotFoundException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.NOT_FOUND, exc.getMessage(), request );
    }

    @ExceptionHandler(CategoryNameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNameAlreadyExists(CategoryNameAlreadyExistsException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }

    @ExceptionHandler(ParentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleParentNotFound(ParentNotFoundException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.NOT_FOUND, exc.getMessage(), request );
    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFound(CategoryNotFoundException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.NOT_FOUND, exc.getMessage(), request );
    }

    @ExceptionHandler(CategoryCannotBeParentOfItselfException.class)
    public ResponseEntity<ErrorResponse> handleCategoryCannotBeParentOfItself(CategoryCannotBeParentOfItselfException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.BAD_REQUEST, exc.getMessage(), request );
    }

    @ExceptionHandler(BarcodeAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleBarcodeAlreadyExists(BarcodeAlreadyExistsException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }

    @ExceptionHandler(ProductCodeAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleProductCodeAlreadyExists(ProductCodeAlreadyExistsException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.NOT_FOUND, exc.getMessage(), request );
    }

    @ExceptionHandler(ProductSupplierNotFountException.class)
    public ResponseEntity <ErrorResponse> handleProductSupplierNotFount(ProductSupplierNotFountException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.NOT_FOUND, exc.getMessage(), request );
    }

    @ExceptionHandler(ProductSupplierCodeAlreadyExistsException.class)
    public ResponseEntity <ErrorResponse> handleProductSupplierCodeAlreadyExists(ProductSupplierCodeAlreadyExistsException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }

    @ExceptionHandler(ProductSupplierAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleProductSupplierAlreadyExists(ProductSupplierAlreadyExistsException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }

    @ExceptionHandler(ProductSupplierAlreadyDeactivatedException.class)
    public ResponseEntity<ErrorResponse> handleProductSupplierDeactivated(ProductSupplierAlreadyDeactivatedException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }

    @ExceptionHandler(SupplierAlreadyDeactivatedException.class)
    public ResponseEntity<ErrorResponse>  handleSupplierAlreadyDeactivated(SupplierAlreadyDeactivatedException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }
    @ExceptionHandler(ProductAlreadyDeactivatedException.class)
    public ResponseEntity<ErrorResponse> handleProductAlreadyDeactivated(ProductAlreadyDeactivatedException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }

    @ExceptionHandler(CannotDeleteActiveProductSupplierException.class)
    public ResponseEntity<ErrorResponse> handleCannotDeleteActiveProductSupplier(CannotDeleteActiveProductSupplierException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }

    @ExceptionHandler(CannotDeleteActiveSupplierException.class)
    public ResponseEntity<ErrorResponse> handleCannotDeleteActiveSupplier(CannotDeleteActiveSupplierException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }

    @ExceptionHandler(CannotDeleteActiveProductException.class)
    public ResponseEntity<ErrorResponse> handleCannotDeleteActiveProduct(CannotDeleteActiveProductException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }

    @ExceptionHandler(ProductAlreadyActivatedException.class)
    public ResponseEntity<ErrorResponse> handleProductAlreadyActivated(ProductAlreadyActivatedException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleOperationNotAllowed(OperationNotAllowedException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.FORBIDDEN, exc.getMessage(), request );
    }

    @ExceptionHandler(BarcodeCannotBeEmpty.class)
    public ResponseEntity<ErrorResponse> handleBarcodeCannotBeEmpty(BarcodeCannotBeEmpty exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.BAD_REQUEST, exc.getMessage(), request );
    }

    @ExceptionHandler(SupplierAlreadyActivatedException.class)
    public ResponseEntity<ErrorResponse> handleSupplierAlreadyActivated(SupplierAlreadyActivatedException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }

    @ExceptionHandler(ProductSupplierAlreadyActivatedException.class)
    public ResponseEntity<ErrorResponse> handleProductSupplierAlreadyActivated(ProductSupplierAlreadyActivatedException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }
    @ExceptionHandler(CategoryAlreadyDeactivatedException.class)
    public ResponseEntity<ErrorResponse> handleCategoryAlreadyDeactivated(CategoryAlreadyDeactivatedException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }

    @ExceptionHandler(CategoryAlreadyActivatedException.class)
    public ResponseEntity<ErrorResponse> handleCategoryAlreadyActivated(CategoryAlreadyActivatedException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }

    @ExceptionHandler(SupplierIdRequiredForInTransactionException.class)
    public ResponseEntity<ErrorResponse> handSupplierIdRequiredForInTransaction(SupplierIdRequiredForInTransactionException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }

    @ExceptionHandler(SupplierIdOnlyRequiredForInOrSupplierReturnException.class)
    public ResponseEntity<ErrorResponse> handSupplierIdOnlyRequiredForInTransaction(SupplierIdOnlyRequiredForInOrSupplierReturnException exc, HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }

    @ExceptionHandler(InventoryTransactionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInventoryTransactionNotFound(InventoryTransactionNotFoundException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.NOT_FOUND, exc.getMessage(), request );
    }

    @ExceptionHandler(QuantityMustBePositiveException.class)
    public ResponseEntity<ErrorResponse> handleQuantityMustBePositiveException(QuantityMustBePositiveException exc , HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.BAD_REQUEST, exc.getMessage(), request );
    }

    @ExceptionHandler(NotEnoughStockQuantityException.class)
    public ResponseEntity<ErrorResponse> handleNotEnoughStockQuantityException(NotEnoughStockQuantityException exc, HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.BAD_REQUEST, exc.getMessage(), request );
    }

    @ExceptionHandler(InventoryTransactionItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInventoryTransactionItemNotFound(InventoryTransactionItemNotFoundException exc, HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.NOT_FOUND, exc.getMessage(), request );
    }

    @ExceptionHandler(AdjustmentQuantityCannotBeZeroException.class)
    public ResponseEntity<ErrorResponse> handAdjustmentQuantityCannotBeZeroException(AdjustmentQuantityCannotBeZeroException exc, HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.BAD_REQUEST, exc.getMessage(), request );
    }

    @ExceptionHandler(InventoryTransactionAlreadyReversedException.class)
    public ResponseEntity<ErrorResponse> handleInventoryTransactionAlreadyReversed(InventoryTransactionAlreadyReversedException exc, HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.CONFLICT, exc.getMessage(), request );
    }

    @ExceptionHandler(CannotReverseReversedTransactionException.class)
    public ResponseEntity<ErrorResponse> handleCannotReverseReversedTransactionException(CannotReverseReversedTransactionException exc, HttpServletRequest request) {
        return buildErrorResponse (exc, HttpStatus.BAD_REQUEST, exc.getMessage(), request );
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
