package team.cats.psychological.param;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class ReleaseParams {
    @NotNull(message = "问题Id不能为空")
    private Long questionnaireId;
    @NotNull(message = "Id不能为空")
    private Long id;
    @NotNull(message = "类型不能为空")
    private Integer publishType;
    private LocalDateTime releaseTime;
    private LocalDateTime deadLine;
}
