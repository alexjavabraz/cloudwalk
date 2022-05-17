package br.com.bjbraz.cloudwalk;

import br.com.bjbraz.cloudwalk.repository.GamePlayerRepository;
import br.com.bjbraz.cloudwalk.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=2)
public class GenerateReport implements CommandLineRunner {

    @Autowired
    GameRepository gameRepository;
    @Autowired
    GamePlayerRepository gamePlayerRepository;

    @Override
    public void run(String... args) throws Exception {

        // Generate Report 1
        gameRepository.findAll().forEach(System.out::println);

        // Generate Report 2
    }

}
