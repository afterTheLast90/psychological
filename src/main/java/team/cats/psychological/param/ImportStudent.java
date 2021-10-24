package team.cats.psychological.param;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

/**
 * @author wmgx
 * @create 2021-10-24-1:05
 **/
@Data
@Accessors(chain = true)
public class ImportStudent {
    private Long classId;
    List<HashMap<String, Object>> data;
//    private MultipartFile file;
}
