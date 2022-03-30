package Wooaham.wooaham_server.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Alarm {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private Integer hour;
    private Integer minute;
    private Integer daysOfWeek;
    private Boolean enabled;
    private Boolean before10min;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

}
