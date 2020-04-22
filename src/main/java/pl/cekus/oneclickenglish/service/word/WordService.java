package pl.cekus.oneclickenglish.service.word;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.cekus.oneclickenglish.model.User;
import pl.cekus.oneclickenglish.model.Word;
import pl.cekus.oneclickenglish.repository.WordRepository;
import pl.cekus.oneclickenglish.service.user.UserService;

import java.io.IOException;
import java.util.List;

@Service
public class WordService {
    private static final Logger logger = LoggerFactory.getLogger(WordService.class);
    private WordRepository wordRepository;
    private UserService userService;
    private TranslateService translateService;

    WordService(WordRepository wordRepository, UserService userService, TranslateService translateService) {
        this.wordRepository = wordRepository;
        this.userService = userService;
        this.translateService = translateService;
    }

    public Word createWord(String enWord) {
        if (wordRepository.findWordByEnWord(enWord) == null) {
            try {
                wordRepository.save(new Word(enWord, translateService.translate(enWord)));
            } catch (IOException e) {
                logger.info("An error occurred while translating the word");
                e.printStackTrace();
            }
        }
        Word result = wordRepository.findWordByEnWord(enWord);
        User user = userService.getCurrentLoggedInUser();
        user.addWord(result);
        userService.save(user);
        return result;
    }

    public List<Word> getAllWords() {
        User user = userService.getCurrentLoggedInUser();
        return wordRepository.findAllByUserId(user.getId());
    }

    public void deleteWord(String enWord) {
        Word result = wordRepository.findWordByEnWord(enWord);
        User user = userService.getCurrentLoggedInUser();
        user.removeWord(result);
        userService.save(user);
    }

}
