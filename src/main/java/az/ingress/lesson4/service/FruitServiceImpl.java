package az.ingress.lesson4.service;

import az.ingress.lesson4.domain.FruitEntity;
import az.ingress.lesson4.dto.FruitRequestDto;
import az.ingress.lesson4.dto.FruitResponseDto;
import az.ingress.lesson4.repository.FruitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FruitServiceImpl implements FruitService {

    private final FruitRepository fruitRepository;

    FruitServiceImpl(FruitRepository fruitRepository) {
        System.out.println("Created an instance of fruit class " + this);
        this.fruitRepository = fruitRepository;
    }

    @Override
    public List<FruitResponseDto> list(Integer from, Integer to) {

        return fruitRepository.findAll()
                .stream()
                .map(fruitEntity -> FruitResponseDto
                        .builder()
                        .price(fruitEntity.getPrice())
                        .name(fruitEntity.getName())
                        .amount(fruitEntity.getAmount())
                        .id(fruitEntity.getId())
                        .build())
                .toList();
    }

    @Override
    public FruitRequestDto get(Long id) {
        return null;
    }

    @Override
    public FruitResponseDto create(FruitRequestDto fruitDto) {
        FruitEntity fruit = FruitEntity.builder()
                .amount(fruitDto.getAmount())
                .price(fruitDto.getPrice())
                .name(fruitDto.getName())
                .build();
        FruitEntity fruit1 = fruitRepository.save(fruit);
        return FruitResponseDto
                .builder()
                .id(fruit1.getId())
                .name(fruit1.getName())
                .amount(fruit1.getAmount())
                .price(fruit1.getPrice())
                .build();
    }

    @Override
    public FruitRequestDto update(Long id, FruitRequestDto fruitDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
