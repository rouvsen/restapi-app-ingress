package az.ingress.lesson4.service;

import az.ingress.lesson4.dto.FruitRequestDto;
import az.ingress.lesson4.dto.FruitResponseDto;

import java.util.List;

public interface FruitService {

    List<FruitResponseDto> list(Integer from, Integer to);

    FruitRequestDto get(Long id);


    FruitResponseDto create(FruitRequestDto fruitDto);


    FruitRequestDto update(Long id, FruitRequestDto fruitDto);

    void delete(Long id);

}
