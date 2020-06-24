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
import static org.mockito.Mockito.doNothing;
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

    @Mock
    private User user;

    @InjectMocks
    private DefinitionExamService definitionExamService;

    @Test
    void shouldReplaceWordWithDotsWhenTheWordAppearsInTheDefinition() throws Exception {
        //given
        given(userService.getCurrentLoggedInUser()).willReturn(user);

        //when
        when(wordRepository.findAllByUserId(user.getId())).thenReturn(prepareUserWordsList());
        when(definitionService.getDefinitionOfWord(word1))
                .thenReturn(prepareDefinitions().get(0).getDescription().replace(word1.getEnWord(), "............."));
        when(definitionService.getDefinitionOfWord(word2))
                .thenReturn(prepareDefinitions().get(1).getDescription().replace(word2.getEnWord(), "............."));
        when(definitionService.getDefinitionOfWord(word3))
                .thenReturn(prepareDefinitions().get(2).getDescription().replace(word3.getEnWord(), "............."));

        //then
        assertThat(definitionExamService.generateDefinitionsExam(), hasSize(3));
        assertThat(definitionExamService.generateDefinitionsExam().get(0).getSentence(), not(containsString(".............")));
        assertThat(definitionExamService.generateDefinitionsExam().get(0).getSentence(), not(containsString(word1.getEnWord())));
        assertThat(definitionExamService.generateDefinitionsExam().get(1).getSentence(), not(containsString(word2.getEnWord())));
        assertThat(definitionExamService.generateDefinitionsExam().get(2).getSentence(), containsString("............."));
        assertThat(definitionExamService.generateDefinitionsExam().get(2).getSentence(), not(containsString(word3.getEnWord())));
    }

    @Test
    void shouldGenerateDefinitionsExamWhenWordsHaveDefinitions() throws Exception {
        //given
        given(userService.getCurrentLoggedInUser()).willReturn(user);

        //when
        when(wordRepository.findAllByUserId(user.getId())).thenReturn(prepareUserWordsList());
        when(definitionService.getDefinitionOfWord(word1)).thenReturn(prepareDefinitions().get(0).getDescription());
        when(definitionService.getDefinitionOfWord(word2)).thenReturn(prepareDefinitions().get(1).getDescription());
        when(definitionService.getDefinitionOfWord(word3)).thenReturn(prepareDefinitions().get(2).getDescription());

        //then
        assertThat(definitionExamService.generateDefinitionsExam(), hasSize(3));
        assertThat(definitionExamService.generateDefinitionsExam().get(1), equalTo(new WrittenTestQuestion(definition2.getDescription(), word2)));
        assertThat(definitionExamService.generateDefinitionsExam().get(0).getSentence(), containsString("move very fast"));
    }

    @Test
    void shouldNotGenerateWrittenTestQuestionForWordWithoutDefinition() throws Exception {
        //given
        given(userService.getCurrentLoggedInUser()).willReturn(user);
        String definitionWord4 = definitionService.getDefinitionOfWord(word4);


        //when
        when(wordRepository.findAllByUserId(user.getId())).thenReturn(prepareUserWordsList());
        when(definitionService.getDefinitionOfWord(word1)).thenReturn(prepareDefinitions().get(0).getDescription());
        when(definitionService.getDefinitionOfWord(word2)).thenReturn(prepareDefinitions().get(1).getDescription());
        when(definitionService.getDefinitionOfWord(word3)).thenReturn(prepareDefinitions().get(2).getDescription());
        when(definitionService.getDefinitionOfWord(word4)).thenReturn(definitionWord4);

        //then
        assertThat(definitionWord4, nullValue());
        assertThat(definitionExamService.generateDefinitionsExam(), hasSize(3));
        assertThat(definitionExamService.generateDefinitionsExam().get(1), equalTo(new WrittenTestQuestion(definition2.getDescription(), word2)));
        assertThat(definitionExamService.generateDefinitionsExam().get(0).getSentence(), containsString("move very fast"));
    }

//    @Test
//    void shouldNotReturnDefinitionIfGetsADefinitionOfWordThatHasNoDefinition() throws Exception {
//        //given
//        String definition = definitionService.getDefinitionOfWord(word4);
//
//        //when + then
//        assertThat(definition, nullValue());
//    }

    private List<Word> prepareUserWordsList() {
        return Arrays.asList(word1, word2, word3, word4);
    }

    private List<Definition> prepareDefinitions() {
        return Arrays.asList(definition1, definition2, definition3);
    }
}
