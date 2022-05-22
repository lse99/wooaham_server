package Wooaham.wooaham_server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StoreDto {
    @JsonProperty
    private String id;
    @JsonProperty
    private Double lat;
    @JsonProperty
    private Double lng;
    @JsonProperty
    private String fac_nam;
    @JsonProperty
    private String fac_tel;
    @JsonProperty
    private String fac_o_add;
    @JsonProperty
    private String cat_nam;
    @JsonProperty
    private String fac_n_add;
}
