package br.com.salonbot.api;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
@RestControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ApiDtos.ErrorResponse> status(ResponseStatusException e){
    return ResponseEntity.status(e.getStatusCode()).body(new ApiDtos.ErrorResponse("REQUEST_ERROR",e.getReason()==null?"Request failed":e.getReason()));
  }
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiDtos.ErrorResponse> validation(MethodArgumentNotValidException e){
    String m=e.getBindingResult().getFieldErrors().stream().findFirst().map(x->x.getField()+": "+x.getDefaultMessage()).orElse("Invalid request");
    return ResponseEntity.badRequest().body(new ApiDtos.ErrorResponse("VALIDATION_ERROR",m));
  }
}
