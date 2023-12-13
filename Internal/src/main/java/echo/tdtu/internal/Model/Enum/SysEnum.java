package echo.tdtu.internal.Model.Enum;

import lombok.Getter;

public class SysEnum {

    @Getter
    public enum Color {
        Yellow("Màu vàng"),
        Green("Màu xanh"),
        Red("Màu đỏ");

        private final String abbreviation;

        Color(String abbreviation) {
            this.abbreviation = abbreviation;
        }
    }

    @Getter
    public enum Ram {
        None("No"),
        VeryHight("64GB"),
        Hight("32GB"),
        Medium("16GB"),
        Low("8GB"),
        VeryLow("4GB");

        private final String abbreviation;

        Ram(String abbreviation) {
            this.abbreviation = abbreviation;
        }
    }

    @Getter
    public enum Rom {
        None("No"),
        VeryHight("256GB"),
        Hight("128GB"),
        Medium("64GB"),
        Low("32GB"),
        VeryLow("16GB");

        private final String abbreviation;

        Rom(String abbreviation) {
            this.abbreviation = abbreviation;
        }
    }
    @Getter
    public enum Status {
        Ready("Chuẩn bị"),
        paid("Đã thanh toán"),
        unpaid("Chưa thanh toán");

        private final String abbreviation;

        Status(String abbreviation) {
            this.abbreviation = abbreviation;
        }
    }
}