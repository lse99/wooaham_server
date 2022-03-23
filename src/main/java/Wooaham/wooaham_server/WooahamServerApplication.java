package Wooaham.wooaham_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
//아직 DB 설정 안 해서 빌드 안 됨 - 설정 후 아래 어노테이션 삭제
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class WooahamServerApplication {

	public static void main(String[] args) {

		SpringApplication.run(WooahamServerApplication.class, args);
	}

}
