package pl.cekus.oneclickenglish.service.exam;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import pl.cekus.oneclickenglish.model.Example;
import pl.cekus.oneclickenglish.model.User;
import pl.cekus.oneclickenglish.model.Word;
import pl.cekus.oneclickenglish.repository.WordRepository;
import pl.cekus.oneclickenglish.service.user.UserService;
import pl.cekus.oneclickenglish.service.word.ExampleService;


class RandomExamServiceTest {
    private Word word1 = new Word("catch", "złapać");
    private Word word2 = new Word("extension", "rozszerzenie");
    private Word word3 = new Word("particular", "specjalny");
    private Word word4 = new Word("sample", "przykład");

    private Example example1 = new Example("he shared his catch with the others", word1);
    private Example example2 = new Example("they applied for an extension of the loan", word2);
    private Example example3 = new Example("she gets special (or particular) satisfaction from her volunteer work", word3);

    @Mock
    private ExampleService exampleService;

    @Mock
    private WordRepository wordRepository;

    @Mock
    private UserService userService;

    @Mock
    private User user;

    @InjectMocks
    private RandomExamService randomExamService;

}
