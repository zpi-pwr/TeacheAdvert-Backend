package ppztw.AdvertBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ppztw.AdvertBoard.Config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class AdvertBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvertBoardApplication.class, args);
	}

}
