package com.nadiannis.cmd.config;

import com.nadiannis.cmd.AllStartupExampleBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
* This code will tell Spring what Beans (instances) to be created.
* Here, we make the instantiation of class AllStartupExampleBean into a Bean,
* so that it will be run when all Beans created at the start of the app.
* That's why we see methods of AllStartupExampleBean called at the start of the app.
* */

@Configuration
public class SpringStartupConfig {

    @Bean(initMethod = "initMethod", destroyMethod = "closeDbConnection")
    public AllStartupExampleBean allStartupExampleBean() {
        return new AllStartupExampleBean();
    }

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            System.out.println("Sample database initialized.");
        };
    }

}
