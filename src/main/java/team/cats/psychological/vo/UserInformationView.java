package team.cats.psychological.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import team.cats.psychological.entity.Users;

import java.util.List;

@Data
@Accessors(chain = true)
public class UserInformationView {
    private Users users;
    private List<String> className;
}
