package Wooaham.wooaham_server.dto;

import Wooaham.wooaham_server.domain.Icon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class IconDto {

    private Long iconId;

    private String iconUrl;

    public static IconDto from(Icon icon){
        return new IconDto(
                icon.getIconId(),
                icon.getIconUrl()
        );
    }

}
