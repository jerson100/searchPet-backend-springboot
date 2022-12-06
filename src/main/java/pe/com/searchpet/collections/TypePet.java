package pe.com.searchpet.collections;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Document(collection = "typepets")
public class TypePet {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    private String _id;
    @NotNull(message = "El tipo de mascota es requerido")
    @NotBlank(message = "El tipo de mascota no debe contener solo espacios en blanco")
    @Size(min = 2, max = 30, message = "El tipo de mascota debe tener como mínimo 2 carácteres y como máximo 30")
    private String type;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int status;
    @Size(min=2, max = 200, message = "La descripción debe contener como mínimo 2 carácteres y máximo 200 carácteres")
    private String description;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updatedAt;
}
