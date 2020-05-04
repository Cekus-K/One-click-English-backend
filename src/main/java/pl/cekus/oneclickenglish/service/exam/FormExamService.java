package pl.cekus.oneclickenglish.service.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.cekus.oneclickenglish.model.User;
import pl.cekus.oneclickenglish.model.Word;
import pl.cekus.oneclickenglish.repository.WordRepository;
import pl.cekus.oneclickenglish.service.exam.questions.ChoiceTestQuestion;
import pl.cekus.oneclickenglish.service.user.UserService;
import pl.cekus.oneclickenglish.service.word.ExampleService;
import pl.cekus.oneclickenglish.service.word.FormService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FormExamService {
    private final Logger logger = LoggerFactory.getLogger(FormExamService.class);
    private final ExampleService exampleService;
    private final FormService formService;
    private final WordRepository wordRepository;
    private final UserService userService;

    FormExamService(
            ExampleService exampleService,
            FormService formService,
            WordRepository wordRepository,
            UserService userService
    ) {
        this.exampleService = exampleService;
        this.formService = formService;
        this.wordRepository = wordRepository;
        this.userService = userService;
    }

    // Verb selection exam
    public List<ChoiceTestQuestion> generateSingleChoiceExam() {
        List<ChoiceTestQuestion> questions = new ArrayList<>();
        User currentUser = userService.getCurrentLoggedInUser();

//        for (Word word : wordRepository.findAllByUserId(currentUser.getId())) {
        for (Word word : wordRepository.findAll()) {
            List<String> wordForms;
            String example;
            try {
                wordForms = formService.getFormsOfWord(word.getEnWord());
                wordForms.add(word.getEnWord());
                Collections.shuffle(wordForms);
                example = exampleService.getExampleSentence(word.getEnWord());
                if (wordForms.size() == 4) {
                    questions.add(new ChoiceTestQuestion(example.replace(word.getEnWord(), "............."), word.getEnWord(), wordForms));
                }
            } catch (Exception e) {
                logger.info("No forms or examples found for the word: " + word.getEnWord());
            }
        }
        return questions;
    }

    public List<Boolean> checkSingleChoiceExam(List<String> examToCheck) {
        return examToCheck.stream()
                .map(wordRepository::existsByEnWord)
                .collect(Collectors.toList());
    }
}
