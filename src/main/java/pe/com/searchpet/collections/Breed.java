package pe.com.searchpet.collections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document(collection = "breeds")
public class Breed {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    private String _id;
    @Field(value = "idTypePet")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ObjectId idTypePet;
    @ReadOnlyProperty
    private TypePet typePet;
    private String name;
    private String description;
    private Set<String> characteristics;
    private Set<String> images;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int status;
    @CreatedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdAt;
    @LastModifiedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updatedAt;
}
