package pl.cekus.oneclickenglish.service.word;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.cekus.oneclickenglish.model.User;
import pl.cekus.oneclickenglish.model.Word;
import pl.cekus.oneclickenglish.repository.WordRepository;
import pl.cekus.oneclickenglish.service.user.UserService;

import java.io.IOException;
import java.util.List;

@Service
public class WordService {
    private final Logger logger = LoggerFactory.getLogger(WordService.class);
    private final WordRepository wordRepository;
    private final UserService userService;
    private final TranslateService translateService;
    private final DefinitionService definitionService;
    private final ExampleService exampleService;
    private final FormService formService;

    WordService(
            WordRepository wordRepository,
            UserService userService,
            TranslateService translateService,
            DefinitionService definitionService,
            ExampleService exampleService,
            FormService formService
    ) {
        this.wordRepository = wordRepository;
        this.userService = userService;
        this.translateService = translateService;
        this.definitionService = definitionService;
        this.exampleService = exampleService;
        this.formService = formService;
    }

    public Word createWord(String enWord) {
        if (wordRepository.findWordByEnWord(enWord) == null) {
            try {
                wordRepository.save(new Word(enWord, translateService.translate(enWord)));
            } catch (Exception e) {
                logger.info("An error occurred while translating the word");
                e.printStackTrace();
            }
        }

        Word result = wordRepository.findWordByEnWord(enWord);

        if (!definitionService.checkExists(result)) {
            definitionService.addDefinitions(result);
            logger.info("assigned a new definitions for word: " + "\"" + result.getEnWord() + "\"");
        } else {
            logger.info("definitions for: " + "\"" + result.getEnWord() + "\"" + " already exists!");
        }

        if (!exampleService.checkExists(result)) {
            exampleService.addExamples(result);
            logger.info("assigned a new examples for word: " + "\"" + result.getEnWord() + "\"");
        } else {
            logger.info("examples for: " + "\"" + result.getEnWord() + "\"" + " already exists!");
        }

        User user = userService.getCurrentLoggedInUser();
        user.addWord(result);
        userService.save(user);

        return result;
    }

    public List<Word> getAllUserWords() {
        return wordRepository.findAllByUserId(userService.getCurrentLoggedInUser().getId());
//        return wordRepository.findAll();
    }

    public void deleteWord(String enWord) {
        Word result = wordRepository.findWordByEnWord(enWord);
        User user = userService.getCurrentLoggedInUser();
        user.removeWord(result);
        userService.save(user);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initSampleWordsToDb() {
        wordRepository.save(new Word("service", "usługa"));
        wordRepository.save(new Word("software", "oprogramowanie"));
        wordRepository.save(new Word("interact", "oddziaływać"));
        wordRepository.save(new Word("various", "różny"));
        wordRepository.save(new Word("equipment", "wyposażenie"));
        wordRepository.save(new Word("retrieved", "odzyskany"));
        wordRepository.save(new Word("during", "podczas"));
        wordRepository.save(new Word("evaluate", "oszacować"));
        wordRepository.save(new Word("according", "według"));
        wordRepository.save(new Word("contrived", "wymyślone"));
        wordRepository.save(new Word("disadvantages", "niedogodności"));
        wordRepository.save(new Word("adjust", "dostosować"));
        wordRepository.save(new Word("mention", "wspominać"));
        wordRepository.save(new Word("underlying", "zasadniczy"));
        wordRepository.save(new Word("wondering", "zdumiewający"));
        wordRepository.save(new Word("messy", "niechlujny"));
        wordRepository.save(new Word("concise", "zwięzły"));
        wordRepository.save(new Word("greedy", "chciwy"));
        wordRepository.save(new Word("serves", "służyć"));
        wordRepository.save(new Word("combination", "połączenie"));
        wordRepository.save(new Word("appear", "zjawić się"));
        wordRepository.save(new Word("implicit", "domniemany"));
        wordRepository.save(new Word("appropriate", "właściwy"));
        wordRepository.save(new Word("confusion", "zamieszanie"));
        wordRepository.save(new Word("announcements", "ogłoszenia"));
        wordRepository.save(new Word("scope", "zakres"));
        wordRepository.save(new Word("indicate", "wskazać"));
        wordRepository.save(new Word("relevant", "istotny"));
        wordRepository.save(new Word("assigning", "przypisywanie"));
    }
}
