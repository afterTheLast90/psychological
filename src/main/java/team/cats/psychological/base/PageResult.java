package team.cats.psychological.base;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class PageResult<T> {
    private Integer pageNum;
    private Integer pageSize;
    private Long total;
    private Integer pages;
    private List<T> data;

    public PageResult(List<T> data) {
        PageInfo page = new PageInfo(data);
        this.pages=page.getPages();
        this.pageSize=page.getPageSize();
        this.total = page.getTotal();
        this.pageNum = page.getPageNum();
        this.data = data;
    }
}
