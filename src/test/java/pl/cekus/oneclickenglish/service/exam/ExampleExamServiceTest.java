package pl.cekus.oneclickenglish.service.exam;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.cekus.oneclickenglish.model.Definition;
import pl.cekus.oneclickenglish.model.Example;
import pl.cekus.oneclickenglish.model.User;
import pl.cekus.oneclickenglish.model.Word;
import pl.cekus.oneclickenglish.repository.WordRepository;
import pl.cekus.oneclickenglish.service.exam.questions.WrittenTestQuestion;
import pl.cekus.oneclickenglish.service.user.UserService;
import pl.cekus.oneclickenglish.service.word.DefinitionService;
import pl.cekus.oneclickenglish.service.word.ExampleService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExampleExamServiceTest {

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
    private ExampleExamService exampleExamService;

    @Test
    void shouldReplaceWordWithDotsInSentenceWhenDuringGeneratingExam() throws Exception {
        //given
        given(userService.getCurrentLoggedInUser()).willReturn(user);

        //when
        when(wordRepository.findAllByUserId(user.getId())).thenReturn(prepareUserWordsList());
        when(exampleService.getExampleSentence(word1))
                .thenReturn(prepareExamples().get(0).getSentence().replace(word1.getEnWord(), "............."));
        when(exampleService.getExampleSentence(word2))
                .thenReturn(prepareExamples().get(1).getSentence().replace(word2.getEnWord(), "............."));
        when(exampleService.getExampleSentence(word3))
                .thenReturn(prepareExamples().get(2).getSentence().replace(word3.getEnWord(), "............."));

        //then
        assertThat(exampleExamService.generateExamplesExam(), hasSize(3));
        assertThat(exampleExamService.generateExamplesExam().get(0).getSentence(), containsString("............."));
        assertThat(exampleExamService.generateExamplesExam().get(1).getSentence(), not(containsString(word2.getEnWord())));
    }

    @Test
    void shouldGenerateExampleExamWhenWordsHaveExamples() throws Exception {
        //given
        given(userService.getCurrentLoggedInUser()).willReturn(user);

        //when
        when(wordRepository.findAllByUserId(user.getId())).thenReturn(prepareUserWordsList());
        when(exampleService.getExampleSentence(word1)).thenReturn(prepareExamples().get(0).getSentence());
        when(exampleService.getExampleSentence(word2)).thenReturn(prepareExamples().get(1).getSentence());
        when(exampleService.getExampleSentence(word3)).thenReturn(prepareExamples().get(2).getSentence());

        //then
        assertThat(exampleExamService.generateExamplesExam(), hasSize(3));
        assertThat(exampleExamService.generateExamplesExam().get(1),
                equalTo(new WrittenTestQuestion(example2.getSentence().replace(word2.getEnWord(), "............."), word2)));
        assertThat(exampleExamService.generateExamplesExam().get(0).getSentence(),
                containsString("he shared his ............. with the others"));
    }

    @Test
    void shouldNotGenerateWrittenTestQuestionForWordWithoutExample() throws Exception {
        //given
        given(userService.getCurrentLoggedInUser()).willReturn(user);
        String definitionWord4 = exampleService.getExampleSentence(word4);


        //when
        when(wordRepository.findAllByUserId(user.getId())).thenReturn(prepareUserWordsList());
        when(exampleService.getExampleSentence(word1)).thenReturn(prepareExamples().get(0).getSentence());
        when(exampleService.getExampleSentence(word2)).thenReturn(prepareExamples().get(1).getSentence());
        when(exampleService.getExampleSentence(word3)).thenReturn(prepareExamples().get(2).getSentence());
        when(exampleService.getExampleSentence(word4)).thenReturn(definitionWord4);

        //then
        assertThat(definitionWord4, nullValue());
        assertThat(exampleExamService.generateExamplesExam(), hasSize(3));
        assertThat(exampleExamService.generateExamplesExam().get(1),
                equalTo(new WrittenTestQuestion(example2.getSentence().replace(word2.getEnWord(), "............."), word2)));
        assertThat(exampleExamService.generateExamplesExam().get(0).getSentence(),
                containsString("he shared his ............. with the others"));
    }

    private List<Word> prepareUserWordsList() {
        return Arrays.asList(word1, word2, word3, word4);
    }

    private List<Example> prepareExamples() {
        return Arrays.asList(example1, example2, example3);
    }
}
