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
import java.util.stream.Collectors;

@Service
public class RandomExamService {
    private final Logger logger = LoggerFactory.getLogger(RandomExamService.class);
    private final ExampleService exampleService;
    private final WordRepository wordRepository;
    private final UserService userService;

    RandomExamService(
            ExampleService exampleService,
            WordRepository wordRepository,
            UserService userService
    ) {
        this.exampleService = exampleService;
        this.wordRepository = wordRepository;
        this.userService = userService;
    }

    // <example sentence : list <of 4 words with one correct>>
    public Map<String, List<String>> generateRandomWordsExam() {
        Map<String, List<String>> exam = new HashMap<>();
        User currentUser = userService.getCurrentLoggedInUser();

        Random random = new Random();
        int index = wordRepository.findAll().size() - 1;

        for (Word word: wordRepository.findAllByUserId(currentUser.getId())) {
            List<Word> randomWords = new ArrayList<>();
            String example;
            try {
                randomWords.add(word);
                example = exampleService.getExampleSentence(word.getEnWord());
                while (randomWords.size() < 4) {
                    Word toCheck = wordRepository.findById((long) random.nextInt(index) + 1).orElseThrow(Exception::new);
                    if (!randomWords.contains(toCheck)) {
                        randomWords.add(toCheck);
                    }
                }
                Collections.shuffle(randomWords);
                exam.put(example, randomWords.stream().map(Word::getEnWord).collect(Collectors.toList()));
            } catch (Exception e) {
                logger.info("No examples found for the word: " + word.getEnWord());
            }
        }
        return exam;
    }

    public List<Boolean> checkRandomExam(List<String> examToCheck) {
        return examToCheck.stream()
                .map(wordRepository::existsByEnWord)
                .collect(Collectors.toList());
    }
}