package pl.cekus.oneclickenglish.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.cekus.oneclickenglish.model.Word;
import pl.cekus.oneclickenglish.service.word.WordService;

import java.util.List;

@Controller
@RequestMapping("/words")
class WordController {
    private final Logger logger = LoggerFactory.getLogger(WordController.class);
    private final WordService wordService;

    WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping
    public String getWordsPage(){
        return "words";
    }

    @GetMapping("/list")
    @ResponseBody
    List<Word> readAll() {
        logger.info("reading all user words");
        return wordService.getAllUserWords();
    }

    @PostMapping("/add")
    @ResponseBody
    Word create(@RequestBody Word toSave) {
        logger.info("created new word");
        return wordService.createWord(toSave.getEnWord());
    }

    @PostMapping("/{enWord}")
    @ResponseBody
    void deleteWordFromUser(@PathVariable String enWord) {
        wordService.deleteWord(enWord);
    }
}
