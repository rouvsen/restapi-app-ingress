package az.ingress.lesson4.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FruitResponseDto {

    private Long id;

    private String name;

    private String amount;

    private Double price;
}
