package pe.com.searchpet.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum EAccountType {
    GOOGLE("google"),
    FACEBOOK("facebook"),
    TWITTER("twitter"),
    INSTAGRAM("instagram"),
    NORMAL("normal");
    private String accountType;
}
