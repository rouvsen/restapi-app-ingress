package az.ingress.lesson4.service;

import az.ingress.lesson4.domain.FruitEntity;
import az.ingress.lesson4.domain.State;
import az.ingress.lesson4.dto.FruitRequestDto;
import az.ingress.lesson4.dto.FruitResponseDto;
import az.ingress.lesson4.repository.FruitRepository;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FruitServiceImpl implements FruitService {

    private final FruitRepository fruitRepository;

    FruitServiceImpl(FruitRepository fruitRepository) {
        System.out.println("Created an instance of fruit class " + this);
        this.fruitRepository = fruitRepository;
    }

    @Override
    public List<FruitResponseDto> list() {
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

    //TODO:1 < Done
    @Override
    public ResponseEntity<FruitResponseDto> get(Long id) {
        try {
            FruitEntity fruitEntity = fruitRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("Invalid Fruit id: %s".formatted(id))
            );
            if (fruitEntity.getStatus() != State.AVAILABLE) {
                throw new IllegalStateException("Fruit is unavailable in warehouse");
            }
            return ResponseEntity.ok(
                    FruitResponseDto.builder()
                            .id(fruitEntity.getId())
                            .name(fruitEntity.getName())
                            .price(fruitEntity.getPrice())
                            .amount(fruitEntity.getAmount())
                            .build()
            );
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.LOCKED).build();
        } catch (Exception ex) {
            throw new RuntimeException("Internal Error: %s".formatted(ex.getCause()));
        }
    }

    @Override
    public FruitResponseDto create(FruitRequestDto fruitDto) {
        FruitEntity fruit = FruitEntity.builder()
                .amount(fruitDto.getAmount())
                .price(fruitDto.getPrice())
                .name(fruitDto.getName())
                .status(State.AVAILABLE)
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

    //TODO:2 < Done
    @Override
    public ResponseEntity<FruitResponseDto> update(Long id, FruitRequestDto fruitDto) {
        try {
            Optional<FruitEntity> byId = fruitRepository.findById(id);
            if(byId.isPresent()) {
                FruitEntity fruitEntity = byId.get();
                if(fruitEntity.getStatus() == State.AVAILABLE) {
                    fruitEntity.setName(fruitDto.getName());
                    fruitEntity.setAmount(fruitDto.getAmount());
                    fruitEntity.setPrice(fruitDto.getPrice());
                    fruitRepository.save(fruitEntity);
                    return ResponseEntity.ok(
                            FruitResponseDto.builder()
                                    .id(fruitEntity.getId())
                                    .name(fruitEntity.getName())
                                    .price(fruitEntity.getPrice())
                                    .amount(fruitEntity.getAmount())
                                    .build()
                    );
                }
                throw new IllegalStateException("Fruit is unavailable in warehouse");
            }
            throw new IllegalArgumentException("Invalid Fruit id: %s".formatted(id));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.LOCKED).build();
        } catch (Exception ex) {
            throw new RuntimeException("Internal Error: %s".formatted(ex.getCause()));
        }
    }

    //TODO:3 < Done
    @Override
    public ResponseEntity<?> delete(Long id) {
        try {
            FruitEntity fruitEntity = fruitRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("Invalid Fruit id: %s".formatted(id))
            );
            if(fruitEntity.getStatus() != State.AVAILABLE) {
                throw new IllegalStateException("Fruit already unavailable");
            }
            fruitEntity.setId(id); //double checking
            fruitEntity.setStatus(State.UNAVAILABLE);
            fruitRepository.save(fruitEntity);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.LOCKED).build();
        } catch (Exception ex) {
            throw new RuntimeException("Internal error: %s".formatted(ex.getCause()));
        }
    }

    //TODO:4 Pagination < Done
    @Override
    public Slice<FruitResponseDto> paginate(Pageable pageable) {
        List<FruitResponseDto> fruits = fruitRepository.findAll()
                .stream().filter(el -> el.getStatus() == State.AVAILABLE)
                .map(el -> FruitResponseDto.builder()
                        .id(el.getId())
                        .name(el.getName())
                        .price(el.getPrice())
                        .amount(el.getAmount())
                        .build())
                .toList();
        return new PageImpl<>(fruits, pageable, fruits.size() > pageable.getPageSize()
                ? pageable.getPageSize() + 1
                : fruits.size()
        );
    }
}
