package pe.com.searchpet.models.requests.typepets;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PatchTypePet {
    @Size(min = 2, max = 30, message = "El tipo de mascota debe tener como mínimo 2 carácteres y como máximo 30")
    private String type;
    private int status;
    @Size(min=2, max = 200, message = "La descripción debe contener como mínimo 2 carácteres y máximo 200 carácteres")
    private String description;
}
