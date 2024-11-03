package com.proj.facul;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "${info.app.name}",
        description = "{$info.app.description}",
        version = "${info.app.version}"))
public class ProjFaculApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjFaculApplication.class, args);
    }

}
