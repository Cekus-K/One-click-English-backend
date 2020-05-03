package pl.cekus.oneclickenglish.service.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.cekus.oneclickenglish.model.User;
import pl.cekus.oneclickenglish.model.Word;
import pl.cekus.oneclickenglish.repository.WordRepository;
import pl.cekus.oneclickenglish.service.user.UserService;
import pl.cekus.oneclickenglish.service.word.DefinitionService;

import java.util.*;

@Service
public class DefinitionExamService {
    private final Logger logger = LoggerFactory.getLogger(DefinitionExamService.class);
    private final DefinitionService definitionService;
    private final WordRepository wordRepository;
    private final UserService userService;

    DefinitionExamService(
            DefinitionService definitionService,
            WordRepository wordRepository,
            UserService userService
    ) {
        this.definitionService = definitionService;
        this.wordRepository = wordRepository;
        this.userService = userService;
    }

    // <english word (answer) : definition of this english word>
    public Map<String, String> generateDefinitionsExam() {
        Map<String, String> exam = new HashMap<>();
        User currentUser = userService.getCurrentLoggedInUser();

//        for (Word word: wordRepository.findAllByUserId(currentUser.getId())) {
        for (Word word : wordRepository.findAll()) {
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

    public List<Boolean> checkDefinitionsExam(Map<String, String> examToCheck) {
        return definitionService.checkExistsByDescAndWord(examToCheck);
    }
}
