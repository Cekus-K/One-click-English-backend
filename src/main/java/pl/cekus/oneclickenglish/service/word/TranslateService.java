package pl.cekus.oneclickenglish.service.word;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Service
class TranslateService {
    @Value("${translator-api-key}")
    private String apiKey;

    private String request(String URL) throws IOException {
        java.net.URL url = new URL(URL);
        URLConnection urlConn = url.openConnection();
        urlConn.addRequestProperty("User-Agent", "Mozilla");

        InputStream inStream = urlConn.getInputStream();

        String recieved = new BufferedReader(new InputStreamReader(inStream)).readLine();

        inStream.close();
        return recieved;
    }

    String translate(String text) throws IOException {
        String response = request("https://translate.yandex.net/api/v1.5/tr.json/translate?key="
                + apiKey + "&text=" + text + "&lang=en-pl");
        return response.substring(response.indexOf("text") + 8, response.length() - 3);
    }
}
