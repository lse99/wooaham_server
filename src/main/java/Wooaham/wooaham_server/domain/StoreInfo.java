package Wooaham.wooaham_server.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "store")
public class StoreInfo {

    @Id
    private String id;
    private Double lat;
    private Double lng;
    private String fac_nam;
    private String fac_tel;
    private String fac_o_add;
    private String cat_nam;
    private String fac_n_add;
}
