package br.com.bjbraz.cloudwalk.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Game {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    private long killCount = 0;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    private List<GamePlayer> players = new ArrayList<>();

    @Override
    public String toString() {
        return "\""+name+"\": {\n" +
                "\"total_kills\": "+killCount+",\n" +
                "\"players\": ["+preparePlayerNames()+"],\n" +
                "\"kills\": {"+prepareKills()+"}\n" +
                "}";
    }

    private String prepareKills(){
        StringBuilder kills = new StringBuilder();

        try {
            for(GamePlayer gp : players){
                kills.append(",\""+gp.getName()+"\"" + ":" + gp.getKills());
            }
            return kills.toString().replaceFirst(",","");
        }catch (Exception e){}
        return "";
    }

    private String preparePlayerNames() {
        try {
            StringBuilder playerNames = new StringBuilder();

            for (GamePlayer gp : players) {
                playerNames.append(",\"" + gp.getName() + "\"");
            }
            return playerNames.toString().replaceFirst(",","");
        }catch (Exception e){}
        return "";
    }
}
