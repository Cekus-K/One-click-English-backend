package pl.cekus.oneclickenglish.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.cekus.oneclickenglish.model.Definition;
import pl.cekus.oneclickenglish.model.Word;

import java.util.List;
import java.util.Optional;

@Repository
public interface DefinitionRepository extends CrudRepository<Definition, Long> {

    List<Definition> findAllByWord(Word word);

    Optional<Definition> findFirstByWord(Word word);

    boolean existsByDescriptionAndWord(String description, Word word);

    boolean existsByWord(Word word);
}
