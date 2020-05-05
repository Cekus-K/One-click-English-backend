package pl.cekus.oneclickenglish.service.word;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
class TranslateService {

    String translate(String word) throws Exception {
        HttpResponse<String> response = Unirest.get("https://api.mymemory.translated.net/get?q=" + word + "&langpair=en%7Cpl")
                .asString();

        org.json.JSONObject obj = new JSONObject(response.getBody());
        String translatedWord = ((String) obj.getJSONObject("responseData").get("translatedText")).toLowerCase();
        if (!translatedWord.equals(word) && translatedWord.length() > 0) {
            return translatedWord.toLowerCase();
        }
        throw new Exception("Invalid word entered or translation not found.");
    }
}
