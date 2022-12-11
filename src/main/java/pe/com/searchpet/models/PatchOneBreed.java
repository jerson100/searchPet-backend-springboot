package pe.com.searchpet.models;

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
public class PatchOneBreed {
    @Pattern(regexp = "^[a-fA-F\\d]{24}$", message = "El tipos de mascota(idTypePet) debe tener coincider con la expresión ^[a-fA-F\\d]{24}$")
    private String idTypePet;
    @Size(min = 2, max = 40, message = "El nombre de la raza tiene que tener como mínimo 2 carácteres y como máximo 40")
    private String name;
    @Size(min = 2, max = 200, message = "El descripción de la raza tiene que tener como mínimo 2 carácteres y como máximo 200")
    private String description;
    private Set<@Pattern(
            regexp = "^[a-záéíóúñ]{3,28}$",
            message = "Las características como mínimo deben tener 3 carácteres y máximo 28, además solo se admiten los siguientes carácteres a-záéíóúñ",
            flags = {Pattern.Flag.CASE_INSENSITIVE})
            String> characteristics;
}
