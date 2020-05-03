package pl.cekus.oneclickenglish.service.exam.questions;

import java.util.List;

public class FormQuestion {
    // <example sentence : list <of 4 forms of word with one correct>>
    private String sentence;
    private List<String> forms;

    public FormQuestion(String sentence, List<String> forms) {
        this.sentence = sentence;
        this.forms = forms;
    }

    public String getSentence() {
        return sentence;
    }

    public List<String> getForms() {
        return forms;
    }
}
