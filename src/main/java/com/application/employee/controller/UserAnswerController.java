package com.application.employee.controller;

import com.application.employee.entity.Questions;
import com.application.employee.entity.UserAnswers;
import com.application.employee.entity.UserAnswersDto;
import com.application.employee.entity.Users;
import com.application.employee.repository.QuestionRepository;
import com.application.employee.repository.UserAnswerRepository;
import com.application.employee.repository.UserRepository;
import com.application.employee.service.UserAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-answers")
@CrossOrigin(origins = "http://localhost:3000")
public class UserAnswerController {
    private final UserAnswerService userAnswerService;
    
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private QuestionRepository questionRepository;
    

    @Autowired
    private UserAnswerRepository userAnswerRepository;
    
    
    public UserAnswerController(UserAnswerService userAnswerService) {
        this.userAnswerService = userAnswerService;
    }


    @PostMapping
    public UserAnswers createUserAnswer(@RequestBody UserAnswersDto userAnswerDto) {
        Users user = userRepository.findById(userAnswerDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Questions question = questionRepository.findById(userAnswerDto.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));

        UserAnswers userAnswer = new UserAnswers();
        userAnswer.setUsers(user);
        userAnswer.setQuestions(question);
        userAnswer.setSelectedOption(userAnswerDto.getSelectedOption());

        return userAnswerRepository.save(userAnswer);
    }

    
    @GetMapping("/{id}")
    public UserAnswers getUserAnswerById(@PathVariable Long id) {
        return userAnswerService.getUserAnswerById(id);
    }

    @PutMapping("/{id}")
    public UserAnswers updateUserAnswer(@PathVariable Long id, @RequestBody UserAnswers userAnswer) {
        return userAnswerService.updateUserAnswer(id, userAnswer);
    }

    @DeleteMapping("/{id}")
    public void deleteUserAnswer(@PathVariable Long id) {
        userAnswerService.deleteUserAnswer(id);
    }

    @GetMapping
    public List<UserAnswers> getAllUserAnswers() {
        return userAnswerService.getAllUserAnswers();
    }
}
