package az.ingress.lesson4.rest;

import az.ingress.lesson4.dto.FruitRequestDto;
import az.ingress.lesson4.dto.FruitResponseDto;
import az.ingress.lesson4.service.FruitService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/v1/fruits")
public class FruitApi {

    private final FruitService fruitService;


    public FruitApi(FruitService fruitService) {
        System.out.println("Injected instance of fruit service " + fruitService);
        this.fruitService = fruitService;
    }

    @GetMapping
    public List<FruitResponseDto> list(@RequestParam(value = "from", required = false) Integer from,
                                       @RequestParam(value = "to", required = false) Integer to) {
        return fruitService.list(from, to);
    }

    @GetMapping("/{id}")
    public FruitRequestDto get(@PathVariable Long id) {
        return fruitService.get(id);
    }

    @PostMapping
    public FruitResponseDto create(@Validated @RequestBody FruitRequestDto fruitDto) {
        return fruitService.create(fruitDto);
    }

    @PutMapping("/{id}")
    public FruitRequestDto update(@PathVariable Long id,
                                  @Validated @RequestBody FruitRequestDto fruitDto) {
        return fruitService.update(id, fruitDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        fruitService.delete(id);
    }
}
