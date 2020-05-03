package pl.cekus.oneclickenglish.service.exam.questions;

import java.util.List;

public class ChoiceTestQuestion {
    private String sentence;
    private String answer;
    private List<String> forms;

    public ChoiceTestQuestion(String sentence, String answer, List<String> forms) {
        this.sentence = sentence;
        this.answer = answer;
        this.forms = forms;
    }

    public String getSentence() {
        return sentence;
    }

    public String getAnswer() {
        return answer;
    }

    public List<String> getForms() {
        return forms;
    }
}
