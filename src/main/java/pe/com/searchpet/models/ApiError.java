package pe.com.searchpet.models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ApiError {
    private String message;
    private Map<String, List<String>> errorStack;
    private LocalDateTime date;
}
