package pe.com.searchpet.models.requests.pets;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.mongodb.core.annotation.Collation;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Collation(value = "pets")
public class PutOnePet {
    @NotNull(message = "El nombre(name) de la mascota es requerido")
    @Size(min = 3, max = 30, message = "El nombre de la mascota debe tener como mínimo 3 carácteres y como máximo 30")
    private String name;
    @NotNull(message = "El idUser es requerido")
    @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El id del usuario no tiene el formato correcto: ^[a-fA-F\\d]{24}$")
    private String idUser;
    @NotNull(message = "El idBreed es requerido")
    @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El id de la raza no tiene el formato correcto: ^[a-fA-F\\d]{24}$")
    private String idBreed;
    @NotNull(message = "La description es requerido")
    @Size(min = 3, max =200, message = "La descripción de la mascota debe tener como mínimo 3 carácteres y como máximo 200")
    private String description;
    private Date dateOfBirth;
    @NotNull(message = "El eyeColor es requerido")
    @Size(min = 3, max= 30, message = "El color de ojo de la mascota debe tener como mínimo 3 carácteres y como máximo 30")
    private String eyeColor;
    @NotNull(message = "El hairColor es requerido")
    @Size(min = 3, max= 30, message = "El color de pelo de la mascota debe tener como mínimo 3 carácteres y como máximo 30")
    private String hairColor;
    @NotNull(message = "El tamaño(size) es requerido")
    @Pattern(regexp = "^Pequeño|Mediano|Grande$", message = "El tamaño debe ser Pequeño, Mediano o Grande")
    private String size;
}
