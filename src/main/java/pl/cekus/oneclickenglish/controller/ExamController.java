package pl.cekus.oneclickenglish.controller;

import org.springframework.web.bind.annotation.*;
import pl.cekus.oneclickenglish.service.exam.DefinitionExamService;
import pl.cekus.oneclickenglish.service.exam.ExampleExamService;
import pl.cekus.oneclickenglish.service.exam.FormExamService;
import pl.cekus.oneclickenglish.service.exam.RandomExamService;
import pl.cekus.oneclickenglish.service.exam.questions.ChoiceTestQuestion;
import pl.cekus.oneclickenglish.service.exam.questions.WrittenTestQuestion;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/exam")
class ExamController {

    private DefinitionExamService definitionExamService;
    private ExampleExamService exampleExamService;
    private FormExamService formExamService;
    private RandomExamService randomExamService;

    ExamController(
            DefinitionExamService definitionExamService,
            ExampleExamService exampleExamService,
            FormExamService formExamService,
            RandomExamService randomExamService
    ) {
        this.definitionExamService = definitionExamService;
        this.exampleExamService = exampleExamService;
        this.formExamService = formExamService;
        this.randomExamService = randomExamService;
    }

    @GetMapping("/definitions")
    public List<WrittenTestQuestion> generateDefinitionsExam() {
        return definitionExamService.generateDefinitionsExam();
    }

    @GetMapping("/examples")
    public List<WrittenTestQuestion> generateExamplesExam() {
        return exampleExamService.generateExamplesExam();
    }

    @GetMapping("/forms")
    public List<ChoiceTestQuestion> generateSingleChoiceExam() {
        return formExamService.generateSingleChoiceExam();
    }

    @GetMapping("/random")
    public List<ChoiceTestQuestion> generateRandomWordsExam() {
        return randomExamService.generateRandomWordsExam();
    }

    @PostMapping("/definitions")
    public List<Boolean> checkDefinitionsExam(@RequestBody Map<String, String> examToCheck) {
        return definitionExamService.checkDefinitionsExam(examToCheck);
    }

    @PostMapping("/examples")
    public List<Boolean> checkExamplesExam(@RequestBody Map<String, String> examToCheck) {
        return exampleExamService.checkExamplesExam(examToCheck);
    }

    @PostMapping("/forms")
    public List<Boolean> checkSingleChoiceExam(@RequestBody List<String> examToCheck) {
        return formExamService.checkSingleChoiceExam(examToCheck);
    }

    @PostMapping("/random")
    public List<Boolean> checkRandomWordsExam(@RequestBody List<String> examToCheck) {
        return randomExamService.checkRandomExam(examToCheck);
    }
}
