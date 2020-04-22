package pl.cekus.oneclickenglish.service.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.cekus.oneclickenglish.model.User;
import pl.cekus.oneclickenglish.model.Word;
import pl.cekus.oneclickenglish.repository.DefinitionRepository;
import pl.cekus.oneclickenglish.repository.WordRepository;
import pl.cekus.oneclickenglish.service.user.UserService;
import pl.cekus.oneclickenglish.service.word.DefinitionService;
import pl.cekus.oneclickenglish.service.word.ExampleService;
import pl.cekus.oneclickenglish.service.word.FormService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExamService {
    private final Logger logger = LoggerFactory.getLogger(ExamService.class);
    private final DefinitionService definitionService;
    private final DefinitionRepository definitionRepository;
    private final ExampleService exampleService;
    private final FormService formService;
    private final WordRepository wordRepository;
    private final UserService userService;

    ExamService(
            DefinitionService definitionService,
            DefinitionRepository definitionRepository,
            ExampleService exampleService,
            FormService formService,
            WordRepository wordRepository,
            UserService userService
    ) {
        this.definitionService = definitionService;
        this.definitionRepository = definitionRepository;
        this.exampleService = exampleService;
        this.formService = formService;
        this.wordRepository = wordRepository;
        this.userService = userService;
    }

    // <english word (answer) : definition of this english word>
    public Map<String, String> generateDefinitionsExam() {
        Map<String, String> exam = new HashMap<>();
        User currentUser = userService.getCurrentLoggedInUser();
        for (Word word : wordRepository.findAllByUserId(currentUser.getId())) {
            String definitionOfWord;
            try {
                definitionOfWord = definitionService.getDefinitionOfWord(word.getEnWord());
                exam.put(word.getEnWord(), definitionOfWord);
            } catch (Exception e) {
                logger.info("No definition found for the word: " + word.getEnWord());
            }
        }
        return exam;
    }

    // <english word (answer) : example sentence using this english word>
    public Map<String, String> generateExamplesExam() {
        Map<String, String> exam = new HashMap<>();
        User currentUser = userService.getCurrentLoggedInUser();
        for (Word word : wordRepository.findAllByUserId(currentUser.getId())) {
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

    // <example sentence : <correct form of word (answer) : other forms of word>>
    public Map<String, List<String>> generateSingleChoiceExam() {
        Map<String, List<String>> exam = new HashMap<>();

        // fixme: use words from the user after improve getting data from external API

        // User currentUser = userService.getCurrentLoggedInUser();
        // for (Word word: wordRepository.findAllByUserId(currentUser.getId())) {

        for (Word word : wordRepository.findAll()) {
            List<String> wordForms;
            String example;
            try {
                wordForms = formService.getFormsOfWord(word.getEnWord());
                wordForms.add(word.getEnWord());
                Collections.shuffle(wordForms);
                example = exampleService.getExampleSentence(word.getEnWord());
                exam.put(example, wordForms);
            } catch (Exception e) {
                logger.info("No forms or examples found for the word: " + word.getEnWord());
            }
        }
        return exam;
    }

    public List<Boolean> checkDefinitionsExam(Map<String, String> examToCheck) {
        List<Boolean> answers = new ArrayList<>();
        for (String enWord: examToCheck.keySet()) {
            answers.add(definitionRepository
                    .existsByDescriptionAndWord(examToCheck.get(enWord), wordRepository.findWordByEnWord(enWord)));
        }
        return answers;
    }

    public List<Boolean> checkExamplesExam(Map<String, String> examToCheck) {
        List<Boolean> answers = new ArrayList<>();
        for (String enWord: examToCheck.keySet()) {
            answers.add(examToCheck.get(enWord)
                    .contains(enWord));
        }
        return answers;
    }

    public List<Boolean> checkSingleChoiceExam(List<String> examToCheck) {
        return examToCheck.stream()
                .map(wordRepository::existsByEnWord)
                .collect(Collectors.toList());
    }
}
