package az.ingress.lesson4.service;

import az.ingress.lesson4.dto.FruitRequestDto;
import az.ingress.lesson4.dto.FruitResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FruitService {

    List<FruitResponseDto> list();

    ResponseEntity<FruitResponseDto> get(Long id);


    FruitResponseDto create(FruitRequestDto fruitDto);


    ResponseEntity<FruitResponseDto> update(Long id, FruitRequestDto fruitDto);

    ResponseEntity<?> delete(Long id);

    //TODO:4 Pagination
    Slice<FruitResponseDto> paginate(Pageable pageable);
}
