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
import pl.cekus.oneclickenglish.model.Definition;
import pl.cekus.oneclickenglish.model.Word;
import pl.cekus.oneclickenglish.repository.DefinitionRepository;
import pl.cekus.oneclickenglish.repository.WordRepository;

import java.util.List;
import java.util.Random;

@Service
public class DefinitionService {
    private static final Logger logger = LoggerFactory.getLogger(DefinitionService.class);
    private final DefinitionRepository definitionRepository;
    private final WordRepository wordRepository;

    DefinitionService(DefinitionRepository definitionRepository, WordRepository wordRepository) {
        this.definitionRepository = definitionRepository;
        this.wordRepository = wordRepository;
    }

    @Value("${words-api-key}")
    private String apiKey;

    public void addDefinitions(Word word) {
        try {
            HttpResponse<String> response = Unirest.get("https://wordsapiv1.p.rapidapi.com/words/" + word.getEnWord() + "/definitions")
                    .header("x-rapidapi-host", "wordsapiv1.p.rapidapi.com")
                    .header("x-rapidapi-key", apiKey)
                    .asString();

            JSONObject obj = new JSONObject(response.getBody());
            try {
                JSONArray arr = obj.getJSONArray("definitions");
                for (int i = 0; i < arr.length(); i++) {
                    String definition = arr.getJSONObject(i).getString("definition");
                    definitionRepository.save(new Definition(definition, word));
                }
            } catch (JSONException e) {
                logger.info("no definition found for the word: " + word.getEnWord());
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    public String getDefinitionOfWord(Word word) throws Exception {
        List<Definition> definitions = definitionRepository.findAllByWord(word);
        Random random = new Random();
        return definitions.get(random.nextInt(definitions.size() -1) +1).getDescription();
    }

    public boolean checkExists(Word word) {
        return definitionRepository.existsByWord(word);
    }
}
