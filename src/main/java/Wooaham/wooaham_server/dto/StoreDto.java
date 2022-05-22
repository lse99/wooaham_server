package Wooaham.wooaham_server.dto;

import Wooaham.wooaham_server.domain.SchoolInfo;
import Wooaham.wooaham_server.domain.StoreInfo;
import Wooaham.wooaham_server.domain.type.UserType;
import Wooaham.wooaham_server.domain.user.Student;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


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

    @Getter
    @Builder
    public static class Simple {
        private String storeId;
        private Double lat;
        private Double lng;

        public static Simple from(StoreInfo storeInfo) {
            return Simple.builder()
                    .storeId(storeInfo.getId())
                    .lat(storeInfo.getLat())
                    .lng(storeInfo.getLng())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Detail {
        private String storeId;
        private Double lat; // 경도
        private Double lng; // 위도
        private String name; // 시설명
        private String phoneNum; // 시설전화번호
        private String oldAddress; // 시설구주소
        private String newAddress; // 시설새주소
        private String categoryName; // 시설분류명

        public static Detail from(StoreInfo storeInfo) {
            return Detail.builder()
                    .storeId(storeInfo.getId())
                    .lat(storeInfo.getLat())
                    .lng(storeInfo.getLng())
                    .name(storeInfo.getFac_nam())
                    .phoneNum(storeInfo.getFac_tel())
                    .oldAddress(storeInfo.getFac_o_add())
                    .newAddress(storeInfo.getFac_n_add())
                    .categoryName(storeInfo.getCat_nam())
                    .build();
        }
    }
}
