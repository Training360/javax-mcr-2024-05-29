package training360.employeessb3clientdemo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
@Slf4j
@AllArgsConstructor
public class EmployeesClientApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(EmployeesClientApplication.class, args);
	}

	private WebClient.Builder webClientBuilder;

	@Override
	public void run(String... args) {
		log.info("Application run");

		var webClient = webClientBuilder.baseUrl("http://localhost:8080").build();

		var factory = HttpServiceProxyFactory
				.builderFor(WebClientAdapter.create(webClient)).build();
		var client = factory.createClient(EmployeeService.class);

		log.info("Response: {}", client.listEmployees());
	}
}
