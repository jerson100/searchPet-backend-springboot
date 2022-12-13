package pe.com.searchpet.models.requests.typepets;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PutTypePet {
    @NotNull(message = "El tipo de mascota es requerida")
    @NotBlank(message = "El tipo de mascota no debe contener solo espacios en blanco")
    @Size(min = 2, max = 30, message = "El tipo de mascota debe tener como mínimo 2 carácteres y como máximo 30")
    private String type;
    private int status;
    @NotNull(message = "La descripción es requerida")
    @Size(min=2, max = 200, message = "La descripción debe contener como mínimo 2 carácteres y máximo 200 carácteres")
    private String description;
}
