package pe.com.searchpet.collections;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;
import pe.com.searchpet.models.SocialNetwork;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Document(value = "users")
@Builder
public class User {
    @Id
    private String _id;
    private String name;
    private String paternalSurname;
    private String maternalSurname;
    private Date birthday;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    private String address;
    private GeoJsonPoint location;
    //private ObjectId idDistrict;
    private SocialNetwork socialNetWorks;
    private String urlImageProfile;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int status;
    private String typeUser;
    private String accountType;
    @CreatedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdAt;
    @LastModifiedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updatedAt;
}
