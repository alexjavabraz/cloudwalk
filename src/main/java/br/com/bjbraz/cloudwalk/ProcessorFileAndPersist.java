package br.com.bjbraz.cloudwalk;

import br.com.bjbraz.cloudwalk.processor.FileProcessor;
import br.com.bjbraz.cloudwalk.processor.InputStreamProcessor;
import br.com.bjbraz.cloudwalk.repository.GamePlayerRepository;
import br.com.bjbraz.cloudwalk.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;

@Component
@Order(value=1)
public class ProcessorFileAndPersist implements CommandLineRunner {

    @Autowired
    GameRepository gameRepository;
    @Autowired
    GamePlayerRepository gamePlayerRepository;

    @Override
    public void run(String... args) throws Exception {
        // Getting File
        FileProcessor fp = new FileProcessor();
        BufferedInputStream bufferedInputStream = fp.start();

        // Processing Games
        InputStreamProcessor.process(bufferedInputStream);

        //Processing Blocks
        InputStreamProcessor.startThreads(gameRepository, gamePlayerRepository);
    }
}
