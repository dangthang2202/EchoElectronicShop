package echo.tdtu.internal.DTO;

import java.util.ArrayList;
import java.util.List;


public class FilterDTO {
    public List<String> ram;
    public List<String> color;
    public List<String> rom;
    public String search;
    public int pageNum;
    public int categoryId;

    public FilterDTO() {
        this.pageNum = 0;
        this.ram = new ArrayList<>();
        this.color = new ArrayList<>();
        this.rom = new ArrayList<>();
        this.search = "";
        this.categoryId = 0;
    }
}
