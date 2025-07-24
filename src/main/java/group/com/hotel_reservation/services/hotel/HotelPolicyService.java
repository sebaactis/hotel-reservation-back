package group.com.hotel_reservation.services.hotel;

import group.com.hotel_reservation.mappers.HotelPolicyMapping;
import group.com.hotel_reservation.models.dto.hotelPolicies.HotelPolicyDto;
import group.com.hotel_reservation.models.dto.hotelPolicies.SaveHotelPolicyDto;
import group.com.hotel_reservation.models.entities.Hotel;
import group.com.hotel_reservation.models.entities.HotelPolicy;
import group.com.hotel_reservation.persistence.repositories.hotel.HotelPolicyRepository;
import group.com.hotel_reservation.persistence.repositories.hotel.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelPolicyService {

    private final HotelRepository hotelRepository;
    private final HotelPolicyRepository hotelPolicyRepository;

    public HotelPolicyService(HotelRepository hotelRepository, HotelPolicyRepository hotelPolicyRepository) {
        this.hotelPolicyRepository = hotelPolicyRepository;
        this.hotelRepository = hotelRepository;
    }

    public List<HotelPolicyDto> getPoliciesByHotel(Long hotelId) {
        List<HotelPolicy> policies = hotelPolicyRepository.findByHotelId(hotelId);
        return policies.stream()
                .map(HotelPolicyMapping::hotelPolicyToDto)
                .toList();
    }

    public HotelPolicyDto createPolicy(Long hotelId, SaveHotelPolicyDto dto) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado"));
        HotelPolicy policy = new HotelPolicy();
        policy.setTitle(dto.getTitle());
        policy.setDescription(dto.getDescription());
        policy.setHotel(hotel);
        policy = hotelPolicyRepository.save(policy);
        return HotelPolicyMapping.hotelPolicyToDto(policy);
    }

    public HotelPolicyDto updatePolicy(Long policyId, SaveHotelPolicyDto dto) {
        HotelPolicy policy = hotelPolicyRepository.findById(policyId)
                .orElseThrow(() -> new EntityNotFoundException("Política no encontrada"));

        if(dto.getTitle() != null) {
            policy.setTitle(dto.getTitle());
        }

        if(dto.getDescription() != null) {
            policy.setDescription(dto.getDescription());
        }

        policy = hotelPolicyRepository.save(policy);
        return HotelPolicyMapping.hotelPolicyToDto(policy);
    }

    public Boolean deletePolicy(Long policyId) {
        if (!hotelPolicyRepository.existsById(policyId))
            throw new EntityNotFoundException("Política no encontrada");
        hotelPolicyRepository.deleteById(policyId);
        return true;
    }
}
