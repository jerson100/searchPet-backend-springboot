package pe.com.searchpet.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Characteristic {
    private String size;
    private String eyeColor;
    private String hairColor;
}
