package pl.cekus.oneclickenglish.service.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.cekus.oneclickenglish.model.User;
import pl.cekus.oneclickenglish.model.Word;
import pl.cekus.oneclickenglish.repository.WordRepository;
import pl.cekus.oneclickenglish.service.exam.questions.WrittenTestQuestion;
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

    // Exam with examples sentences and word inside
    public List<WrittenTestQuestion> generateExamplesExam() {
        List<WrittenTestQuestion> questions = new ArrayList<>();
        User currentUser = userService.getCurrentLoggedInUser();

        for (Word word : wordRepository.findAllByUserId(currentUser.getId())) {
            String exampleSentence;
            try {
                exampleSentence = exampleService.getExampleSentence(word);
                questions.add(new WrittenTestQuestion(exampleSentence.replace(word.getEnWord(), ".........."), word));
            } catch (Exception e) {
                logger.info("No example found for the word: " + word.getEnWord());
            }
        }
        return questions;
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
