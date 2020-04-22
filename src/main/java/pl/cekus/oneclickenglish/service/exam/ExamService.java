package pl.cekus.oneclickenglish.service.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.cekus.oneclickenglish.model.User;
import pl.cekus.oneclickenglish.model.Word;
import pl.cekus.oneclickenglish.repository.WordRepository;
import pl.cekus.oneclickenglish.service.user.UserService;
import pl.cekus.oneclickenglish.service.word.DefinitionService;
import pl.cekus.oneclickenglish.service.word.ExampleService;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExamService {
    private final Logger logger = LoggerFactory.getLogger(ExamService.class);
    private final DefinitionService definitionService;
    private final ExampleService exampleService;
    private final WordRepository wordRepository;
    private final UserService userService;

    ExamService(
            DefinitionService definitionService,
            ExampleService exampleService,
            WordRepository wordRepository,
            UserService userService
    ) {
        this.definitionService = definitionService;
        this.exampleService = exampleService;
        this.wordRepository = wordRepository;
        this.userService = userService;
    }

    public Map<String, String> generateDefinitionsExam() {
        Map<String, String> exam = new HashMap<>();
        User currentUser = userService.getCurrentLoggedInUser();
        for (Word word : wordRepository.findAllByUserId(currentUser.getId())) {
            String definitionOfWord = null;
            try {
                definitionOfWord = definitionService.getDefinitionOfWord(word.getEnWord());
                exam.put(word.getEnWord(), definitionOfWord);
            } catch (Exception e) {
                logger.info("No definition found for the word: " + word.getEnWord());
            }
        }
        return exam;
    }

    public Map<String, String> generateExamplesExam() {
        Map<String, String> exam = new HashMap<>();
        User currentUser = userService.getCurrentLoggedInUser();
        for (Word word : wordRepository.findAllByUserId(currentUser.getId())) {
            String exampleSentence = null;
            try {
                exampleSentence = exampleService.getExampleSentence(word.getEnWord());
                exam.put(word.getEnWord(), exampleSentence);
            } catch (Exception e) {
                logger.info("No example found for the word: " + word.getEnWord());
            }
        }
        return exam;
    }
}
