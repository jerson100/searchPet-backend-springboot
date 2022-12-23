package pe.com.searchpet.collections;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.Document;
import pe.com.searchpet.models.Characteristic;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Document(value = "pets")
public class Pet {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    private String _id;
    private String name;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ObjectId idUser;
    @ReadOnlyProperty
    private User user;
    @ReadOnlyProperty
    private Breed breed;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ObjectId idBreed;
    private String urlImageProfile;
    private String description;
    private Set<String> images;
    private Date dateOfBirth;
    private Characteristic characteristics;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int status;
    @CreatedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdAt;
    @LastModifiedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updateAt;
}
