package pl.cekus.oneclickenglish.service.exam.questions;

import pl.cekus.oneclickenglish.model.Word;

import java.util.Objects;

public class WrittenTestQuestion {
    private String sentence;
    private Word answer;

    public WrittenTestQuestion(String sentence, Word answer) {
        this.sentence = sentence;
        this.answer = answer;
    }

    public String getSentence() {
        return sentence;
    }

    public Word getAnswer() {
        return answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WrittenTestQuestion that = (WrittenTestQuestion) o;
        return Objects.equals(sentence, that.sentence) &&
                Objects.equals(answer, that.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sentence, answer);
    }
}
