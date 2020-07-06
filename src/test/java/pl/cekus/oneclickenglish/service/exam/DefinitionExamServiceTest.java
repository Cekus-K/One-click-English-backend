package pl.cekus.oneclickenglish.service.exam;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.cekus.oneclickenglish.model.Definition;
import pl.cekus.oneclickenglish.model.User;
import pl.cekus.oneclickenglish.model.Word;
import pl.cekus.oneclickenglish.repository.WordRepository;
import pl.cekus.oneclickenglish.service.exam.questions.WrittenTestQuestion;
import pl.cekus.oneclickenglish.service.user.UserService;
import pl.cekus.oneclickenglish.service.word.DefinitionService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefinitionExamServiceTest {

    private Word word1 = new Word("hurry", "pośpiech");
    private Word word2 = new Word("available", "dostępny");
    private Word word3 = new Word("rabbit", "królik");
    private Word word4 = new Word("sample", "przykład");

    private Definition definition1 = new Definition("move very fast", word1);
    private Definition definition2 = new Definition("convenient for use or disposal", word2);
    private Definition definition3 = new Definition("hunt rabbit", word3);

    @Mock
    private DefinitionService definitionService;

    @Mock
    private WordRepository wordRepository;

    @Mock
    private UserService userService;

    private User user = new User();

    @InjectMocks
    private DefinitionExamService definitionExamService;

    @Test
    void shouldReplaceWordWithDotsWhenTheWordAppearsInTheDefinition() throws Exception {
        //given
        given(userService.getCurrentLoggedInUser()).willReturn(user);
        given(wordRepository.findAllByUserId(user.getId())).willReturn(prepareUserWordsList());
        given(definitionService.getDefinitionOfWord(word1))
                .willReturn(prepareDefinitions().get(0).getDescription().replace(word1.getEnWord(), "............."));
        given(definitionService.getDefinitionOfWord(word2))
                .willReturn(prepareDefinitions().get(1).getDescription().replace(word2.getEnWord(), "............."));
        given(definitionService.getDefinitionOfWord(word3))
                .willReturn(prepareDefinitions().get(2).getDescription().replace(word3.getEnWord(), "............."));

        //when
        List<WrittenTestQuestion> questions = definitionExamService.generateDefinitionsExam();

        //then
        assertThat(questions, hasSize(3));
        assertThat(questions.get(0).getSentence(), not(containsString(".............")));
        assertThat(questions.get(0).getSentence(), not(containsString(word1.getEnWord())));
        assertThat(questions.get(1).getSentence(), not(containsString(word2.getEnWord())));
        assertThat(questions.get(2).getSentence(), containsString("............."));
        assertThat(questions.get(2).getSentence(), not(containsString(word3.getEnWord())));
    }

    @Test
    void shouldGenerateDefinitionsExamWhenWordsHaveDefinitions() throws Exception {
        //given
        given(userService.getCurrentLoggedInUser()).willReturn(user);
        given(wordRepository.findAllByUserId(user.getId())).willReturn(prepareUserWordsList());
        given(definitionService.getDefinitionOfWord(word1)).willReturn(definition1.getDescription());
        given(definitionService.getDefinitionOfWord(word2)).willReturn(definition2.getDescription());
        given(definitionService.getDefinitionOfWord(word3)).willReturn(definition3.getDescription());

        //when
        List<WrittenTestQuestion> questions = definitionExamService.generateDefinitionsExam();

        //then
        assertThat(questions, hasSize(3));
        assertThat(questions.get(1), equalTo(new WrittenTestQuestion(definition2.getDescription(), word2)));
        assertThat(questions.get(0).getSentence(), containsString("move very fast"));
    }

    @Test
    void shouldNotGenerateWrittenTestQuestionForWordWithoutDefinition() throws Exception {
        //given
        String definitionWord4 = definitionService.getDefinitionOfWord(word4);
        given(userService.getCurrentLoggedInUser()).willReturn(user);
        given(wordRepository.findAllByUserId(user.getId())).willReturn(prepareUserWordsList());
        given(definitionService.getDefinitionOfWord(word1)).willReturn(prepareDefinitions().get(0).getDescription());
        given(definitionService.getDefinitionOfWord(word2)).willReturn(prepareDefinitions().get(1).getDescription());
        given(definitionService.getDefinitionOfWord(word3)).willReturn(prepareDefinitions().get(2).getDescription());
        given(definitionService.getDefinitionOfWord(word4)).willReturn(definitionWord4);

        //when
        List<WrittenTestQuestion> questions = definitionExamService.generateDefinitionsExam();

        //then
        assertThat(definitionWord4, nullValue());
        assertThat(questions, hasSize(3));
        assertThat(questions, not(contains(word4)));
        assertThat(questions.get(1), equalTo(new WrittenTestQuestion(definition2.getDescription(), word2)));
        assertThat(questions.get(0).getSentence(), containsString("move very fast"));
    }

    private List<Word> prepareUserWordsList() {
        return Arrays.asList(word1, word2, word3, word4);
    }

    private List<Definition> prepareDefinitions() {
        return Arrays.asList(definition1, definition2, definition3);
    }
}
