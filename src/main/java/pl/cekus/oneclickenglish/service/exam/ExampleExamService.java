package pl.cekus.oneclickenglish.service.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.cekus.oneclickenglish.model.User;
import pl.cekus.oneclickenglish.model.Word;
import pl.cekus.oneclickenglish.repository.WordRepository;
import pl.cekus.oneclickenglish.service.user.UserService;
import pl.cekus.oneclickenglish.service.word.ExampleService;

import java.util.*;

@Service
public class ExampleExamService {
    private final Logger logger = LoggerFactory.getLogger(ExampleExamService.class);
    private final ExampleService exampleService;
    private final WordRepository wordRepository;
    private final UserService userService;

    ExampleExamService(
            ExampleService exampleService,
            WordRepository wordRepository,
            UserService userService
    ) {
        this.exampleService = exampleService;
        this.wordRepository = wordRepository;
        this.userService = userService;
    }

    // <english word (answer) : example sentence using this english word>
    public Map<String, String> generateExamplesExam() {
        Map<String, String> exam = new HashMap<>();
        User currentUser = userService.getCurrentLoggedInUser();

//        for (Word word : wordRepository.findAllByUserId(currentUser.getId())) {
        for (Word word : wordRepository.findAll()) {
            String exampleSentence;
            try {
                exampleSentence = exampleService.getExampleSentence(word.getEnWord());
                exam.put(word.getEnWord(), exampleSentence);
            } catch (Exception e) {
                logger.info("No example found for the word: " + word.getEnWord());
            }
        }
        return exam;
    }

    public List<Boolean> checkExamplesExam(Map<String, String> examToCheck) {
        List<Boolean> answers = new ArrayList<>();
        for (String enWord : examToCheck.keySet()) {
            answers.add(examToCheck.get(enWord)
                    .contains(enWord));
        }
        return answers;
    }
}
