package pl.cekus.oneclickenglish.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.cekus.oneclickenglish.model.Word;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordRepository extends CrudRepository<Word, Long> {

    @Query("from Word w join w.users u where u.id=:userId")
    List<Word> findAllByUserId(Long userId);

    Word findWordByEnWord(String enWord);

    Word findWordById(Long id);

    boolean existsById(Long id);
}