package pl.cekus.oneclickenglish.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "words")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String enWord;
    private String plWord;
    @ManyToMany(mappedBy = "words")
    private Set<User> users = new HashSet<>();

    public Word() {
    }

    public Word(String enWord, String plWord) {
        this.enWord = enWord;
        this.plWord = plWord;
    }

    Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public String getEnWord() {
        return enWord;
    }

    void setEnWord(String enWord) {
        this.enWord = enWord;
    }

    public String getPlWord() {
        return plWord;
    }

    void setPlWord(String plWord) {
        this.plWord = plWord;
    }

    Set<User> getUsers() {
        return users;
    }

    void setUsers(Set<User> users) {
        this.users = users;
    }
}
