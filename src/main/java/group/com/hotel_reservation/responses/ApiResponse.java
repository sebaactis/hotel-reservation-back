package group.com.hotel_reservation.responses;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private String message;
    private String statusCode;
    private T entity;

    public ApiResponse(String message, String statusCode, T entity) {
        this.message = message;
        this.statusCode = statusCode;
        this.entity = entity;
    }
}
