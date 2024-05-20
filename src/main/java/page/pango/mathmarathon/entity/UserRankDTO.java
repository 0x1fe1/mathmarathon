package page.pango.mathmarathon.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;

@Getter
@Setter
public class UserRankDTO {
    @NonNull
    private String name;
    @NonNull
    private Long rank;

    public UserRankDTO(String name, Long rank) {
        this.name = name;
        this.rank = rank;
    }
}
