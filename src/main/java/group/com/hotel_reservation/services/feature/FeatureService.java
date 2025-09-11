package group.com.hotel_reservation.services.feature;

import group.com.hotel_reservation.models.dto.feature.FeatureCreateDto;
import group.com.hotel_reservation.models.dto.feature.FeatureUpdateDto;
import group.com.hotel_reservation.models.entities.Feature;
import group.com.hotel_reservation.models.entities.Hotel;
import group.com.hotel_reservation.persistence.repositories.feature.FeatureRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeatureService {
    private final FeatureRepository repository;

    public FeatureService(FeatureRepository repository) {
        this.repository = repository;
    }

    public Page<Feature> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Feature getOne(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("No existe esa feature"));
    }

    public Feature create(FeatureCreateDto featureCreateDto) {
        if(repository.existsByNameIgnoreCase(featureCreateDto.getName())) throw new IllegalArgumentException("Ya existe una feature con ese nombre");

        Feature feature = new Feature();
        feature.setName(featureCreateDto.getName());
        feature.setIcon(featureCreateDto.getIcon());
        return repository.save(feature);
    }

    public Feature update(Long id, FeatureUpdateDto featureUpdateDto) {
        Feature feature = getOne(id);


        Optional.ofNullable(featureUpdateDto.getName()).ifPresent(feature::setName);
        Optional.ofNullable(featureUpdateDto.getIcon()).ifPresent(feature::setIcon);

        return repository.save(feature);
    }

    public Boolean delete(Long id) {

        Feature feature = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Feature no encontrada con ID" + id));

        repository.delete(feature);

        return true;
    }
}
