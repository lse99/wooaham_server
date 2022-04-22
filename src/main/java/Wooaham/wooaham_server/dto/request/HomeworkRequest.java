package Wooaham.wooaham_server.dto.request;

import Wooaham.wooaham_server.domain.BaseException;
import Wooaham.wooaham_server.domain.Homework;
import Wooaham.wooaham_server.domain.type.ErrorCode;
import Wooaham.wooaham_server.domain.user.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HomeworkRequest {
    private String title;
    private Boolean checked;
    private String type;

    public Homework toHomework(Student user){
        if (type == "SCHOOL") {
            return Homework.createSchoolHomework(user, this.title, this.checked);
        } else if (type == "ACADEMY") {
            return Homework.createAcademyHomework(user, this.title, this.checked);
        } else
            throw new BaseException(ErrorCode.NOTFOUND_HOMEWORKTYPE);
    }
}
