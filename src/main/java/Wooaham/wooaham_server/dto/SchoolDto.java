package Wooaham.wooaham_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SchoolDto {
    private String officeCode;
    private String schoolCode;
    private String schoolName;
}
