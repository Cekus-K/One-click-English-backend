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

    List<Word> findAll();

    Word findWordByEnWord(String enWord);

    Optional<Word> findById(Long id);

    boolean existsById(Long id);

    boolean existsByEnWord(String enWord);
}
