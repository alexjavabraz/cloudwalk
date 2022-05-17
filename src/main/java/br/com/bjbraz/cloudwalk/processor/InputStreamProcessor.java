package br.com.bjbraz.cloudwalk.processor;

import br.com.bjbraz.cloudwalk.dto.BlockProcessor;
import br.com.bjbraz.cloudwalk.repository.GamePlayerRepository;
import br.com.bjbraz.cloudwalk.repository.GameRepository;
import br.com.bjbraz.cloudwalk.util.FileProcessorUtil;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class InputStreamProcessor {
    private static final String AT_LINE = "-at_line-";

    private static final String GAME = "GAME-";
    private GameRepository gameRepository;
    private GamePlayerRepository gamePlayerRepository;
    private static List<GameProcessor> processorList = new ArrayList<>();

    public static void process(InputStream in){
        try{
            ThreadGroup group = new ThreadGroup("ThreadGroupGame");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            long currentLine = 0;
            long gameNumber = 0;
            String line = null;
            BlockProcessor bp = null;

            while((line = reader.readLine()) != null){

                if(line.contains(FileProcessorUtil.BEGIN)){
                    gameNumber++;
                    bp = new BlockProcessor(new ArrayList<>(), GAME+gameNumber+AT_LINE+currentLine);
                }else if(line.contains(FileProcessorUtil.END)){
                    processorList.add(new GameProcessor(group, bp));
                }else if(line.contains(FileProcessorUtil.KILL)){
                    bp.getLines().add(line);
                }

                currentLine++;
            }

            log.info("Total lines processed: {} ", currentLine);

        }catch (Exception e){
            log.error("Error processing InputStream {}", e.getMessage());
        }

    }

    public static void startThreads(GameRepository gameRepository, GamePlayerRepository gamePlayerRepository) {
        processorList.stream().forEach((item) -> {
            item.prepare(gameRepository, gamePlayerRepository);
            item.run();
        });
    }
}
