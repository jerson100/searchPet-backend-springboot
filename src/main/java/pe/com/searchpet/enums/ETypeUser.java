package pe.com.searchpet.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ETypeUser {
    NORMAL("normal"),
    ADMINISTRATOR("administrator"),
    MODERATOR("moderator");
    private String typeUser;
}
