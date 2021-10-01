package pragma.practica.back.foto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FotoServicioApplication {

	public static void main(String[] args) {
		SpringApplication.run(FotoServicioApplication.class, args);
	}

}
