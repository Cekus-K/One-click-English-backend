package pl.cekus.oneclickenglish.service.word;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.cekus.oneclickenglish.model.Form;
import pl.cekus.oneclickenglish.model.Word;
import pl.cekus.oneclickenglish.repository.FormRepository;
import pl.cekus.oneclickenglish.repository.WordRepository;

@Service
class FormService {
    private final FormRepository formRepository;
    private final WordRepository wordRepository;

    FormService(FormRepository formRepository, WordRepository wordRepository) {
        this.formRepository = formRepository;
        this.wordRepository = wordRepository;
    }

    @Value("${words-api-key}")
    private String apiKey;

    @EventListener(ApplicationReadyEvent.class)
    public void initDb() {
        wordRepository.save(new Word("walk", "iść"));
        formRepository.save(new Form("walked", wordRepository.findWordByEnWord("walk")));
        formRepository.save(new Form("walking", wordRepository.findWordByEnWord("walk")));
        formRepository.save(new Form("walks", wordRepository.findWordByEnWord("walk")));
    }
}
