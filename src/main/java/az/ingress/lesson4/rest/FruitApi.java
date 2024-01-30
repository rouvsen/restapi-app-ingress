package az.ingress.lesson4.rest;

import az.ingress.lesson4.dto.FruitRequestDto;
import az.ingress.lesson4.dto.FruitResponseDto;
import az.ingress.lesson4.service.FruitService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public List<FruitResponseDto> list() {
        return fruitService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FruitResponseDto> get(@PathVariable Long id) {
        return fruitService.get(id);
    }

    @GetMapping("/pagination") //path: http://localhost:9090/v1/fruits/pagination?size=3&page=0
    public Slice<FruitResponseDto> get(Pageable pageable) {
        return fruitService.paginate(pageable);
    }

    @PostMapping
    public FruitResponseDto create(@Validated @RequestBody FruitRequestDto fruitDto) {
        return fruitService.create(fruitDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FruitResponseDto> update(
            @PathVariable Long id,
            @Validated @RequestBody FruitRequestDto fruitDto) {
        return fruitService.update(id, fruitDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return fruitService.delete(id);
    }
}
