package pe.com.searchpet.collections;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;
import java.util.List;

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
    @DocumentReference(lazy = false)
    private TypePet typePet;
    private String name;
    private String description;
    private List<String> characteristics;
    private List<String> images;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int status;
    @CreatedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createdAt;
    @LastModifiedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updatedAt;
}
