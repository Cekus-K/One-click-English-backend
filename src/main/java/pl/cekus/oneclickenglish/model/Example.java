package pl.cekus.oneclickenglish.model;

import javax.persistence.*;

@Entity
@Table(name = "word_examples")
public class Example {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sentence;
    @ManyToOne
    @JoinColumn(name = "word_id")
    private Word word;

    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }
}
