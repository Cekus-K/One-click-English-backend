package pl.cekus.oneclickenglish.controller;

import org.springframework.web.bind.annotation.*;
import pl.cekus.oneclickenglish.service.exam.ExamService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exam")
class ExamController {
    private final ExamService examService;

    ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping("/definitions")
    public Map<String, String> generateDefinitionsExam() {
        return examService.generateDefinitionsExam();
    }

    @GetMapping("/examples")
    public Map<String, String> generateExamplesExam() {
        return examService.generateExamplesExam();
    }

    @GetMapping("/forms")
    public Map<String, Map<String, List<String>>> generateSingleChoiceExam() {
        return examService.generateSingleChoiceExam();
    }

    @PostMapping("/forms")
    public List<Boolean> checkSingleChoiceExam(@RequestBody List<String> words) {
        return examService.checkSingleChoiceExam(words);
    }
}
