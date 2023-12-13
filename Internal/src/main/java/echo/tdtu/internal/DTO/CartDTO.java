package echo.tdtu.internal.DTO;

import echo.tdtu.internal.Model.DetailProduct;
import lombok.Data;

@Data
public class CartDTO {
    private int productId;
    private int color;
    private String image;
    private int quantity;
    private int ram;
    private int rom;
    private String name;
    private long price;

    public void transfer(DetailProduct productDetail,int quantity){
        this.productId = productDetail.getId();
        this.color = productDetail.getColor();
        this.ram = productDetail.getRam();
        this.rom = productDetail.getRom();
        this.image = productDetail.getImage();
        this.price = productDetail.getProduct().getPrice();
        this.name = productDetail.getProduct().getName();
        this.quantity = quantity;
    }
}
