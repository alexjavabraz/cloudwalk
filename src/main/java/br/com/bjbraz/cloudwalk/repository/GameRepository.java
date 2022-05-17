package br.com.bjbraz.cloudwalk.repository;

import br.com.bjbraz.cloudwalk.entity.Game;
import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Long> {
}
