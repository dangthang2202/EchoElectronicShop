package echo.tdtu.internal.DTO;

import lombok.Data;

@Data
public class SummaryDTO {
    private long price;
    private int shipFee;
    private String voucherName;
}
