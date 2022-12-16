package pe.com.searchpet.models.requests.lostPet;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Setter
@Getter
public class CreateOneLostPet {
    @NotNull(message = "Las mascotas(pets) son requeridas")
    @Pattern(regexp = "^[a-fA-F\\d]{24}(?:,[a-fA-F\\d]{24}){0,9}$", message = "Los id de las mascotas deben estar separados por comas, como mínimo 1 y máximo 10")
    private String pets;
    /*Set<@Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El id Pet debe tener coincider con la expresión ^[a-fA-F\\d]{24}$") String> pets;*/
    @NotNull(message = "El idUser es requerido")
    @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El idUser debe tener coincider con la expresión ^[a-fA-F\\d]{24}$")
    private String idUser;
    private List<MultipartFile> images;
    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    private double latitude;
    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    private double longitude;
    @NotNull(message = "La descripción es requerida")
    @Size(min = 3, max = 200, message = "La descripción debe tener como mínimo 3 carácteres y máximo 200")
    private String description;
}
