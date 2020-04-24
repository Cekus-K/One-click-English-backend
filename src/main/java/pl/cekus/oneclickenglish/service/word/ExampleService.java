package pl.cekus.oneclickenglish.service.word;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.cekus.oneclickenglish.model.Example;
import pl.cekus.oneclickenglish.model.Word;
import pl.cekus.oneclickenglish.repository.ExampleRepository;
import pl.cekus.oneclickenglish.repository.WordRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExampleService {
    private final Logger logger = LoggerFactory.getLogger(ExampleService.class);
    private final ExampleRepository exampleRepository;
    private final WordRepository wordRepository;

    ExampleService(ExampleRepository exampleRepository, WordRepository wordRepository) {
        this.exampleRepository = exampleRepository;
        this.wordRepository = wordRepository;
    }

    @Value("${words-api-key}")
    private String apiKey;

    public void addExamples(Word word) {
        try {
            HttpResponse<String> response = Unirest.get("https://wordsapiv1.p.rapidapi.com/words/" + word.getEnWord() + "/examples")
                    .header("x-rapidapi-host", "wordsapiv1.p.rapidapi.com")
                    .header("x-rapidapi-key", apiKey)
                    .asString();

            JSONObject obj = new JSONObject(response.getBody());

            try {
                JSONArray arr = obj.getJSONArray("examples");

                for (int i = 0; i < arr.length(); i++) {
                    String example = (String) arr.get(i);
                    exampleRepository.save(new Example(example, word));
                }
            } catch (JSONException e) {
                logger.info("No example found for the word: " + word.getEnWord());
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    public List<String> getExampleSentences(String enWord) {
        return exampleRepository
                .findAllByWord(wordRepository.findWordByEnWord(enWord))
                .stream()
                .map(Example::getSentence)
                .collect(Collectors.toList());
    }

    public String getExampleSentence(String enWord) throws Exception {
        return exampleRepository
                .findFirstByWord(wordRepository.findWordByEnWord(enWord))
                .orElseThrow(Exception::new)
                .getSentence();
    }

    public boolean checkExists(Word word) {
        return exampleRepository.existsByWord(word);
    }
}
