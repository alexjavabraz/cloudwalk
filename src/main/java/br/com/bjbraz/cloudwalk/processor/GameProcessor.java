package br.com.bjbraz.cloudwalk.processor;

import br.com.bjbraz.cloudwalk.dto.BlockProcessor;
import br.com.bjbraz.cloudwalk.entity.Game;
import br.com.bjbraz.cloudwalk.entity.GamePlayer;
import br.com.bjbraz.cloudwalk.repository.GamePlayerRepository;
import br.com.bjbraz.cloudwalk.repository.GameRepository;
import br.com.bjbraz.cloudwalk.util.FileProcessorUtil;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
public class GameProcessor extends Thread {
    private BlockProcessor filePart;

    private GameRepository gameRepository;

    private Game currentGame;

    private GamePlayerRepository gamePlayerRepository;

    private Map<String, Long> killedByWorld = new HashMap<>();

    public GameProcessor(ThreadGroup tg, BlockProcessor bp){
        super(tg, bp.getStartedLine());
        this.filePart = bp;
        log.info("Starting a thread {}", bp.getStartedLine());
    }

    public void prepare(GameRepository gameRepository, GamePlayerRepository gamePlayerRepository){
        this.gameRepository = gameRepository;
        this.gamePlayerRepository = gamePlayerRepository;
    }

    @Override
    public void run() {
        log.info("Starting Thread {}", getName());

        if(this.gameRepository == null || this.gamePlayerRepository == null){
            throw new RuntimeException("Call prepare method before run");
        }

        createNewGame();
        createPlayersAndKills();
        log.info("Finishing Thread {} ", getName());
    }

    private void createPlayersAndKills() {
        long killCount = 0;
        filePart.getLines().stream().forEach((item) -> {
            processLine(item);
        });

        adjustKillCount();
    }

    private void adjustKillCount() {

       killedByWorld.keySet().stream().forEach(item -> {
           GamePlayer gamePlayer = null;

           if(currentGame.getPlayers().contains(item)){
               gamePlayer = currentGame.getPlayers().stream().filter( player -> player.getName().equals(item) ).collect(Collectors.toList()).get(0);
           }else{
               gamePlayer = new GamePlayer();
               gamePlayer.setGame(currentGame);
               gamePlayer.setName(item);
           }
           gamePlayer.setKills(gamePlayer.getKills() - killedByWorld.get(item));
           gamePlayerRepository.save(gamePlayer);
       });

    }

    private void createNewGame() {
        try {
            currentGame = new Game();
            currentGame.setName(getName());
            currentGame = gameRepository.save(currentGame);
        } catch (Exception e) {
            log.error("Error saving Game {} ", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Optional<GamePlayer> processLine(String line) {
        if(!line.contains(FileProcessorUtil.KILL)){
            return Optional.empty();
        }

        final String playerName = FileProcessorUtil.getTokenAtPoint(5, line);
        currentGame.setKillCount(currentGame.getKillCount()+1);

        if(FileProcessorUtil.WORLD.equals(playerName)){
            final String playerKilledByWorld = FileProcessorUtil.getTokenAtPoint(7, line);
            currentGame = gameRepository.save(currentGame);
            if(killedByWorld.containsKey(playerKilledByWorld)){
                killedByWorld.put(playerKilledByWorld, killedByWorld.get(playerKilledByWorld)+1L);
            }else{
                killedByWorld.put(playerKilledByWorld, 1L);
            }
            return Optional.empty();
        }

        Optional<GamePlayer> optPlayer = searchPlayer(playerName);
        GamePlayer player = null;

        if(optPlayer.isPresent()) {
            player = optPlayer.get();
        }else {
            player = new GamePlayer();
            player.setGame(currentGame);
            player.setName(playerName);
        }

        player.setKills(player.getKills()+1);

        return Optional.of(createOrUpdatePlayer(player));
    }

    private GamePlayer createOrUpdatePlayer(GamePlayer player) {
        player = gamePlayerRepository.save(player);
        currentGame.getPlayers().add(player);
        currentGame = gameRepository.save(currentGame);
        return player;
    }

    private Optional<GamePlayer> searchPlayer(String playerName) {
        return currentGame.getPlayers().stream().filter(c -> c.getName().equals(playerName)).findFirst();
    }

}
