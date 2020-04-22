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
    public Map<String, List<String>> generateSingleChoiceExam() {
        return examService.generateSingleChoiceExam();
    }

    @PostMapping("/definitions")
    public List<Boolean> checkDefinitionsExam(@RequestBody Map<String, String> examToCheck) {
        return examService.checkDefinitionsExam(examToCheck);
    }

    @PostMapping("/examples")
    public List<Boolean> checkExamplesExam(@RequestBody Map<String, String> examToCheck) {
        return examService.checkExamplesExam(examToCheck);
    }

    @PostMapping("/forms")
    public List<Boolean> checkSingleChoiceExam(@RequestBody List<String> examToCheck) {
        return examService.checkSingleChoiceExam(examToCheck);
    }
}
