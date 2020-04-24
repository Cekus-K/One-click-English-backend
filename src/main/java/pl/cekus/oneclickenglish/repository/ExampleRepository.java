package pl.cekus.oneclickenglish.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.cekus.oneclickenglish.model.Example;
import pl.cekus.oneclickenglish.model.Word;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExampleRepository extends CrudRepository<Example, Long> {

    List<Example> findAllByWord(Word word);

    Optional<Example> findFirstByWord(Word word);

    boolean existsByWord(Word word);
}
