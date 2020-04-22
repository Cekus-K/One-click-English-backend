package pl.cekus.oneclickenglish.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.cekus.oneclickenglish.model.Form;
import pl.cekus.oneclickenglish.model.Word;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormRepository extends CrudRepository<Form, Long> {

    List<Form> findAllByWord(Word word);

    Optional<Form> findFirstByWord(Word word);

}
