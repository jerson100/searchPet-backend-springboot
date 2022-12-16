package pe.com.searchpet.collections;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Setter
@Getter
@Document(collection = "lostpets")
public class LostPet {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    private String _id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<ObjectId> idPets;
    @ReadOnlyProperty
    private Set<Pet> pets;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ObjectId idUser;
    @ReadOnlyProperty
    private User user;
    private Set<String> images;
    private GeoJsonPoint location;
    private String description;
    private boolean located;
    @CreatedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdAt;
    @LastModifiedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updatedAt;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int status;
}
