package pl.cekus.oneclickenglish.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
