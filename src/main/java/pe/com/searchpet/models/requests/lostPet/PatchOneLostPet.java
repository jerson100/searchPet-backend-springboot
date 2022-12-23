package pe.com.searchpet.models.requests.lostPet;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Setter
@Getter
public class PatchOneLostPet {
    @Size(min = 1, message = "Al menos debe ingresar el id de una mascota perdida")
    private Set<@Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El id Pet debe tener coincider con la expresión ^[a-fA-F\\d]{24}$") String> pets;
    private double latitude;
    private double longitude;
    @Size(min = 3, max = 200, message = "La descripción debe tener como mínimo 3 carácteres y máximo 200")
    private String description;
}
