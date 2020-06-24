package pl.cekus.oneclickenglish.service.exam.questions;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChoiceTestQuestion that = (ChoiceTestQuestion) o;
        return Objects.equals(sentence, that.sentence) &&
                Objects.equals(answer, that.answer) &&
                Objects.equals(forms, that.forms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sentence, answer, forms);
    }
}
