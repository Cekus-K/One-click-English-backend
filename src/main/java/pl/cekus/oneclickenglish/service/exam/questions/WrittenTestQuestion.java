package pl.cekus.oneclickenglish.service.exam.questions;

import pl.cekus.oneclickenglish.model.Word;

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

}
