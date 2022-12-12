package pe.com.searchpet.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EStatus {
    INACTIVE(0),
    ACTIVE(1),
    LOCKED(2);
    private int status;
}
