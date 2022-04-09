package Wooaham.wooaham_server.domain;

import Wooaham.wooaham_server.domain.type.IconType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "icon")
public class Icon {

    @Id
    @Column(name = "icon_id", nullable = false)
    private Long iconId;

    @Column(name = "icon", nullable = false)
    private String iconUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private IconType type;
}
