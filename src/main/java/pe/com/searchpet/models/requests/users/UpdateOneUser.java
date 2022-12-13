package pe.com.searchpet.models.requests.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.format.annotation.DateTimeFormat;
import pe.com.searchpet.models.SocialNetwork;

import java.util.Date;

@Builder
@ToString
@Getter
@Setter
public class UpdateOneUser {
    @NotNull(message = "El nombre es requerido")
    @Size(min = 2, max = 30, message = "El nombre debe tener como mínimo 2 carácteres y máximo 30")
    private String name;
    @NotNull(message = "El apellido paterno es requerido")
    @Size(min = 2, max = 30, message = "El apellido paterno debe tener como mínimo 2 carácteres y máximo 30")
    private String paternalSurname;
    @NotNull(message = "El apellido materno es requerido")
    @Size(min = 2, max = 30, message = "El apellido materno debe tener como mínimo 2 carácteres y máximo 30")
    private String maternalSurname;
    @NotNull(message = "La fecha de cumpleaños es requerido")
    @DateTimeFormat(pattern = "DD/MM/YYYY")
    private Date birthday;
    @NotNull(message = "La username es requerido")
    @Pattern(regexp = "^[a-z\\d]{6,20}$", flags = {Pattern.Flag.CASE_INSENSITIVE}, message = "El username debe tener como mínimo 6 carácteres(números y letras) y máximo 20")
    private String username;
    @NotNull(message = "El email es requerido")
    @Email(message = "El email debe ser válido")
    private String email;
    @NotNull(message = "La dirección es requerido")
    @Size(min = 4, max = 70, message = "La dirección debe tener como mínimo 4 carácteres y máximo 70")
    private String address;
    @NotNull(message = "La localización es requerida")
    private GeoJsonPoint location;
    @NotNull(message = "La redes sociales son requerido es requerido")
    private SocialNetwork socialNetWorks;
}
