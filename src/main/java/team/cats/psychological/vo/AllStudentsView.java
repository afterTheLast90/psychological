package team.cats.psychological.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import team.cats.psychological.entity.StudentsClass;
import team.cats.psychological.service.StudentsClassService;
@Data
@Accessors(chain = true)
public class AllStudentsView {
    private StudentsClass studentsClass;
    private String studentName;
}
