package pl.cekus.oneclickenglish.model;

import javax.persistence.*;

@Entity
@Table(name = "word_forms")
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String wordForm;
    @ManyToOne
    @JoinColumn(name = "word_id")
    private Word word;

    public Form() {
    }

    public Form(String wordForm, Word word) {
        this.wordForm = wordForm;
        this.word = word;
    }

    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public String getWordForm() {
        return wordForm;
    }

    public void setWordForm(String wordForm) {
        this.wordForm = wordForm;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }
}