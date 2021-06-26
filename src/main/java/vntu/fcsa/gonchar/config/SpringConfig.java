package vntu.fcsa.gonchar.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("vntu.fcsa.gonchar")
@PropertySource("classpath:cashRegister.properties")
public class SpringConfig {
}
