package pl.cekus.oneclickenglish.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pl.cekus.oneclickenglish.model.Word;
import pl.cekus.oneclickenglish.service.word.WordService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/words")
class WordController {
    private final Logger logger = LoggerFactory.getLogger(WordController.class);
    private final WordService wordService;

    WordController(WordService wordService) {
        this.wordService = wordService;
    }


    @GetMapping("/list")
    List<Word> readAll() {
        logger.info("reading all user words");
        return wordService.getAllUserWords();
    }

    @PostMapping("/add")
    Word create(@RequestBody Word toSave) {
        String wordToSave = toSave.getEnWord().strip().toLowerCase();
        if (wordToSave.length() > 0 && wordToSave.length() < 20) {
            logger.info("assigned the word to the user: " + "\"" + wordToSave + "\"");
            return wordService.createWord(wordToSave);
        } else {
            logger.info("invalid word");
            return null;
        }
    }

    @PostMapping("/remove")
    void deleteWordFromUser(@RequestBody Word toRemove) {
        wordService.deleteWord(toRemove.getEnWord());
    }
}
