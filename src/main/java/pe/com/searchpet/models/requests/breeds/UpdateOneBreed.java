package pe.com.searchpet.models.requests.breeds;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class UpdateOneBreed {
    @NotNull(message = "El tipo de mascota(idTypePet) es requerido")
    @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El tipos de mascota(idTypePet) debe tener coincider con la expresión ^[a-fA-F\\d]{24}$")
    private String idTypePet;
    @NotNull(message = "El nombre de la raza es requerido")
    @NotBlank(message = "El nombre de la raza no puede contener solo espacios en blanco")
    @Size(min = 2, max = 40, message = "El nombre de la raza tiene que tener como mínimo 2 carácteres y como máximo 40")
    private String name;
    @NotNull(message = "La descripción es requerido")
    @Size(min = 2, max = 200, message = "El descripción de la raza tiene que tener como mínimo 2 carácteres y como máximo 200")
    private String description;
    @NotNull(message = "Las características es requerido")
    private Set<@Pattern(
            regexp = "^[a-záéíóúñ]{3,28}$",
            message = "Las características como mínimo deben tener 3 carácteres y máximo 28, además solo se admiten los siguientes carácteres a-záéíóúñ",
            flags = {Pattern.Flag.CASE_INSENSITIVE})
            String> characteristics;
}
