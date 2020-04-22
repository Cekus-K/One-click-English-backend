package pl.cekus.oneclickenglish.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.cekus.oneclickenglish.model.Definition;
import pl.cekus.oneclickenglish.model.Form;
import pl.cekus.oneclickenglish.model.Word;

import java.util.List;

@Repository
public interface FormRepository extends CrudRepository<Form, Long> {

    List<Form> findAllByWord(Word word);

    Form findFirstByWord(Word word);

}
