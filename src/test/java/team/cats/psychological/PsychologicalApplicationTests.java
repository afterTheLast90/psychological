package team.cats.psychological;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.cats.psychological.entity.Users;
import team.cats.psychological.mapper.UsersMapper;

import java.util.List;

@SpringBootTest
class PsychologicalApplicationTests {

    @Autowired
    private UsersMapper usersMapper;

    @Test
    void contextLoads() {
        List<Users> usersMappers = usersMapper.selectList(null);
        for (Users user : usersMappers) {
            System.out.println(user);
        }
    }

}
