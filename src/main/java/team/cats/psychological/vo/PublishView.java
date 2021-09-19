package team.cats.psychological.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import team.cats.psychological.entity.Publish;

@Data
@Accessors(chain = true)
public class PublishView {
    private Publish publish;
    private String strangeName;
    private String questionnaireName;
    private String publisherName;
}
