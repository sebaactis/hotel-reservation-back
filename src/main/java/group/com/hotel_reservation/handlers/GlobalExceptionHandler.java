package group.com.hotel_reservation.handlers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // DTO para serializar cada error de campo
    public record FieldErrorDetail(
            String object,        // p.ej. "saveHotelPolicyDto"
            String field,         // p.ej. "description"
            String message,       // p.ej. "la longitud debe estar entre 15 y 200"
            Object rejectedValue, // p.ej. "No se"
            String code           // p.ej. "Size"
    ) {}

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        var servletReq = ((ServletWebRequest) request).getRequest();

        var fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> new FieldErrorDetail(
                        fe.getObjectName(),
                        fe.getField(),
                        fe.getDefaultMessage() == null ? "Inválido" : fe.getDefaultMessage(),
                        fe.getRejectedValue(),
                        fe.getCode()
                ))
                .toList();

        // (Opcional) errores globales (class-level constraints)
        var globalErrors = ex.getBindingResult().getGlobalErrors().stream()
                .map(ge -> Map.of(
                        "object", ge.getObjectName(),
                        "message", ge.getDefaultMessage(),
                        "code", ge.getCode()))
                .toList();

        var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Errores de validación");
        pd.setInstance(URI.create(servletReq.getRequestURI())); // /api/v1/hotelPolicy/43
        pd.setProperty("errors", fieldErrors);                  // ← ARRAY con field + rejectedValue + code
        pd.setProperty("globalErrors", globalErrors);           // ← opcional
        pd.setProperty("timestamp", Instant.now().toString());

        // (Opcional) además, un mapa field -> mensajes para uso rápido en el front
        var errorsMap = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        LinkedHashMap::new,
                        Collectors.mapping(
                                fe -> fe.getDefaultMessage() == null ? "Inválido" : fe.getDefaultMessage(),
                                Collectors.toList()
                        )
                ));
        pd.setProperty("errorMap", errorsMap);

        return ResponseEntity.badRequest().body(pd);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleAny(Exception ex, HttpServletRequest req) {
        var pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado");
        pd.setInstance(URI.create(req.getRequestURI()));
        pd.setProperty("timestamp", Instant.now().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pd);
    }
}
