package echo.tdtu.electronicshopenduser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"echo.tdtu.internal.*", "echo.tdtu.electronicshopenduser.*"})
@EnableJpaRepositories(value = "echo.tdtu.internal.Repository")
@EntityScan(value = "echo.tdtu.internal.Model")
public class ElectronicShopEndUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectronicShopEndUserApplication.class, args);
    }

}
