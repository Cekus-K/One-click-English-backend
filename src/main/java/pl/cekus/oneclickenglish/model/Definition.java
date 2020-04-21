package pl.cekus.oneclickenglish.model;

import javax.persistence.*;

@Entity
@Table(name = "word_definitions")
public class Definition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @ManyToOne
    @JoinColumn(name = "word_id")
    private Word word;

    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }
}
