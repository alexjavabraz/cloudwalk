package br.com.bjbraz.cloudwalk.repository;

import br.com.bjbraz.cloudwalk.entity.GamePlayer;
import org.springframework.data.repository.CrudRepository;

public interface GamePlayerRepository extends CrudRepository<GamePlayer, Long> {
}
