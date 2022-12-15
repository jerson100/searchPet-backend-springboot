package pe.com.searchpet.models.requests.pets;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.web.multipart.MultipartFile;
import pe.com.searchpet.models.Characteristic;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Collation(value = "pets")
public class CreateOnePet {
    @NotNull(message = "El nombre(name) de la mascota es requerido")
    @Size(min = 3, max = 30, message = "El nombre de la mascota debe tener como mínimo 3 carácteres y como máximo 30")
    private String name;
    @NotNull(message = "El idUser es requerido")
    @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El id del usuario no tiene el formato correcto: ^[a-fA-F\\d]{24}$")
    private String idUser;
    @NotNull(message = "El idBreed es requerido")
    @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El id de la raza no tiene el formato correcto: ^[a-fA-F\\d]{24}$")
    private String idBreed;
    private MultipartFile profile;
    @Size(min = 3, max =200, message = "La descripción de la mascota debe tener como mínimo 3 carácteres y como máximo 200")
    private String description;
    private Date dateOfBirth;
    @Size(min = 3, max= 30, message = "El color de ojo de la mascota debe tener como mínimo 3 carácteres y como máximo 30")
    private String eyeColor;
    @Size(min = 3, max= 30, message = "El color de pelo de la mascota debe tener como mínimo 3 carácteres y como máximo 30")
    private String hairColor;
    @NotNull(message = "El tamaño(size) es requerido")
    @Pattern(regexp = "^Pequeño|Mediano|Grande$", message = "El tamaño debe ser Pequeño, Mediano o Grande")
    private String size;
}
