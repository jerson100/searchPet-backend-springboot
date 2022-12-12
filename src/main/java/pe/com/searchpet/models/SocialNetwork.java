package pe.com.searchpet.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SocialNetwork {
    private String facebook;
    private String twitter;
    private String instagram;
    private String whatsapp;
}
