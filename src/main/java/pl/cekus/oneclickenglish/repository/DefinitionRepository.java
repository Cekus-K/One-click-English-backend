package pl.cekus.oneclickenglish.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.cekus.oneclickenglish.model.Definition;
import pl.cekus.oneclickenglish.model.Example;
import pl.cekus.oneclickenglish.model.Word;

import java.util.List;

@Repository
public interface DefinitionRepository extends CrudRepository<Definition, Long> {

    List<Definition> findAllByWord(Word word);

}
