package pl.cekus.oneclickenglish.service.word;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.cekus.oneclickenglish.model.Definition;
import pl.cekus.oneclickenglish.model.Example;
import pl.cekus.oneclickenglish.model.Form;
import pl.cekus.oneclickenglish.model.Word;
import pl.cekus.oneclickenglish.repository.ExampleRepository;
import pl.cekus.oneclickenglish.repository.FormRepository;
import pl.cekus.oneclickenglish.repository.WordRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormService {
    private final FormRepository formRepository;
    private final WordRepository wordRepository;
    private final ExampleRepository exampleRepository;

    FormService(
            FormRepository formRepository,
            WordRepository wordRepository,
            ExampleRepository exampleRepository
    ) {
        this.formRepository = formRepository;
        this.wordRepository = wordRepository;
        this.exampleRepository = exampleRepository;
    }

    public List<String> getFormsOfWord(String enWord) {
        return formRepository
                .findAllByWord(wordRepository.findWordByEnWord(enWord))
                .stream()
                .map(Form::getWordForm)
                .collect(Collectors.toList());
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initDb() {
        wordRepository.save(new Word("walk", "iść"));
        exampleRepository.save(new Example("He could walk on his hands, carrying a plate on one foot.",
                wordRepository.findWordByEnWord("walk")));
        formRepository.save(new Form("walked", wordRepository.findWordByEnWord("walk")));
        formRepository.save(new Form("walking", wordRepository.findWordByEnWord("walk")));
        formRepository.save(new Form("walks", wordRepository.findWordByEnWord("walk")));

        wordRepository.save(new Word("begin", "zaczynać"));
        exampleRepository.save(new Example("Ada could not even begin to comprehend what Adam had been thinking.",
                wordRepository.findWordByEnWord("begin")));
        formRepository.save(new Form("began", wordRepository.findWordByEnWord("begin")));
        formRepository.save(new Form("begun", wordRepository.findWordByEnWord("begin")));
        formRepository.save(new Form("beginning", wordRepository.findWordByEnWord("begin")));

        wordRepository.save(new Word("burn", "spalić"));
        exampleRepository.save(new Example("Exercise does help to burn up calories.",
                wordRepository.findWordByEnWord("burn")));
        formRepository.save(new Form("burned", wordRepository.findWordByEnWord("burn")));
        formRepository.save(new Form("burnt", wordRepository.findWordByEnWord("burn")));
        formRepository.save(new Form("burning", wordRepository.findWordByEnWord("burn")));

        wordRepository.save(new Word("do", "robić"));
        exampleRepository.save(new Example("There was nothing he could do anyway at the moment.",
                wordRepository.findWordByEnWord("do")));
        formRepository.save(new Form("did", wordRepository.findWordByEnWord("do")));
        formRepository.save(new Form("done", wordRepository.findWordByEnWord("do")));
        formRepository.save(new Form("doing", wordRepository.findWordByEnWord("do")));

        wordRepository.save(new Word("fly", "latać"));
        exampleRepository.save(new Example("It was not the most suitable of places to fly an eagle.",
                wordRepository.findWordByEnWord("fly")));
        formRepository.save(new Form("flew", wordRepository.findWordByEnWord("fly")));
        formRepository.save(new Form("flown", wordRepository.findWordByEnWord("fly")));
        formRepository.save(new Form("flying", wordRepository.findWordByEnWord("fly")));

        wordRepository.save(new Word("ride", "jeździć"));
        exampleRepository.save(new Example("Diana went to watch him ride his horse.",
                wordRepository.findWordByEnWord("ride")));
        formRepository.save(new Form("rode", wordRepository.findWordByEnWord("ride")));
        formRepository.save(new Form("ridden", wordRepository.findWordByEnWord("ride")));
        formRepository.save(new Form("riding", wordRepository.findWordByEnWord("ride")));
    }
}
