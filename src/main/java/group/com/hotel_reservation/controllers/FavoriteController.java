package group.com.hotel_reservation.controllers;

import group.com.hotel_reservation.models.dto.favorite.FavoriteDto;
import group.com.hotel_reservation.models.entities.Hotel;
import group.com.hotel_reservation.models.entities.User;
import group.com.hotel_reservation.responses.ApiResponse;
import group.com.hotel_reservation.services.favorite.FavoriteService;
import group.com.hotel_reservation.services.hotel.HotelService;
import group.com.hotel_reservation.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/favorite")
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final UserService userService;
    private final HotelService hotelService;

    public FavoriteController(FavoriteService favoriteService, UserService userService, HotelService hotelService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
        this.hotelService = hotelService;
    }

    @GetMapping("{userId}")
    public ResponseEntity<ApiResponse<List<FavoriteDto>>> getFavoritesByUser(@PathVariable Long userId) {
        try {
            User user = userService.getUser(userId);

            if(user == null) {
                ApiResponse<List<FavoriteDto>> response = new ApiResponse<>("No se encuentra el usuario especificado", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

            } else {
                List<FavoriteDto> favorites = favoriteService.favoritesByUser(user);

                if(favorites.isEmpty()) {
                    ApiResponse<List<FavoriteDto>> response = new ApiResponse<>("No hay favoritos para el usuario especificado", HttpStatus.BAD_REQUEST.toString(), null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }

                ApiResponse<List<FavoriteDto>> response = new ApiResponse<>("Favoritos encontrados", HttpStatus.OK.toString(), favorites);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

        } catch (Exception e) {
            ApiResponse<List<FavoriteDto>> response = new ApiResponse<>(e.getMessage(), HttpStatus.OK.toString(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("{userId}/{hotelId}")
    public ResponseEntity<ApiResponse<Boolean>> existByUserAndHotel(@PathVariable Long userId, @PathVariable Long hotelId) {
        try {
            User user = userService.getUser(userId);
            Hotel hotel = hotelService.getFullHotel(hotelId);

            if(user == null) {
                ApiResponse<Boolean> response = new ApiResponse<>("No se encuentra el usuario especificado", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if(hotel == null) {
                ApiResponse<Boolean> response = new ApiResponse<>("No se encuentra el hotel especificado", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Boolean exists = favoriteService.existsByUserAndHotel(user, hotel);

            if(!exists) {
                ApiResponse<Boolean> response = new ApiResponse<>("No existe la combinacion de user + hotel", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<Boolean> response = new ApiResponse<>("Combinacion encontrada", HttpStatus.OK.toString(), exists);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            ApiResponse<Boolean> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("{userId}/{hotelId}")
    public ResponseEntity<ApiResponse<FavoriteDto>> create(@PathVariable Long userId, @PathVariable Long hotelId) {
        try {
            User user = userService.getUser(userId);
            Hotel hotel = hotelService.getFullHotel(hotelId);

            if(user == null) {
                ApiResponse<FavoriteDto> response = new ApiResponse<>("No se encuentra el usuario especificado", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if(hotel == null) {
                ApiResponse<FavoriteDto> response = new ApiResponse<>("No se encuentra el hotel especificado", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            FavoriteDto create = favoriteService.create(user, hotel);

            if(create == null) {
                ApiResponse<FavoriteDto> response = new ApiResponse<>("No se pudo crear el favorito", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<FavoriteDto> response = new ApiResponse<>("Favorito creado!", HttpStatus.CREATED.toString(), create);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            ApiResponse<FavoriteDto> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("{userId}/{hotelId}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable Long userId, @PathVariable Long hotelId) {
        try {
            User user = userService.getUser(userId);
            Hotel hotel = hotelService.getFullHotel(hotelId);

            if(user == null) {
                ApiResponse<Boolean> response = new ApiResponse<>("No se encuentra el usuario especificado", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if(hotel == null) {
                ApiResponse<Boolean> response = new ApiResponse<>("No se encuentra el hotel especificado", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Boolean delete = favoriteService.delete(user, hotel);

            if(!delete) {
                ApiResponse<Boolean> response = new ApiResponse<>("No se pudo borrar el favorito", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<Boolean> response = new ApiResponse<>("Favorito eliminado!", HttpStatus.OK.toString(), delete);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            ApiResponse<Boolean> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
