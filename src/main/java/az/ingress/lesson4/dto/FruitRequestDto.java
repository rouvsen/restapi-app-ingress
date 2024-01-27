package az.ingress.lesson4.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FruitRequestDto {

    @NotBlank
    private String name;

    @NotNull
    private String amount;

    @NotNull
    private Double price;
}
