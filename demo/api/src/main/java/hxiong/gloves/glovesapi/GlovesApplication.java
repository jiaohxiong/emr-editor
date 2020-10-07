package hxiong.gloves.glovesapi;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@MapperScan("hxiong.gloves.glovesapi.mapper")
@SpringBootApplication
public class GlovesApplication {

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
		scannerConfigurer.setBasePackage("hxiong.gloves.glovesapi.mapper");
		return scannerConfigurer;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(GlovesApplication.class, args);
	}

}
