package pl.cekus.oneclickenglish.service.word;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pl.cekus.oneclickenglish.model.User;
import pl.cekus.oneclickenglish.model.Word;
import pl.cekus.oneclickenglish.repository.WordRepository;
import pl.cekus.oneclickenglish.service.user.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class WordServiceTest {

    @Mock
    private WordRepository wordRepository;
    @Mock
    private UserService userService;
    @Mock
    private TranslateService translateService;
    @Mock
    private DefinitionService definitionService;
    @Mock
    private ExampleService exampleService;
    @Mock
    private FormService formService;
    @Mock
    private User user;
    @InjectMocks
    private WordService wordService;

    @Test
    void shouldCreateWordObjectWhenTheEnglishWordIsCorrect() throws Exception {
        //given
        Word word = new Word("sample", "przyklad");

        //when
        when(wordRepository.findWordByEnWord("sample")).thenReturn(word);
        when(userService.getCurrentLoggedInUser()).thenReturn(user);
        when(definitionService.checkExists(word)).thenReturn(true);
        when(exampleService.checkExists(word)).thenReturn(true);

        //then
        assertThat(wordRepository.findWordByEnWord(word.getEnWord()), equalTo(word));
        assertThat(wordRepository.findWordByEnWord(word.getEnWord()).getPlWord(), equalTo("przyklad"));
        assertThat(wordService.createWord("sample"), equalTo(word));
    }

}
