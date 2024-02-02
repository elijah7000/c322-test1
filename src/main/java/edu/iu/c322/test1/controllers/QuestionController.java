package edu.iu.c322.test1.controllers;

import edu.iu.c322.test1.model.Question;
import edu.iu.c322.test1.repository.FileRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Component;

@Component

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final FileRepository fileRepository;

    public QuestionController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @PostMapping
    public ResponseEntity<Boolean> addQuestion(@RequestBody Question question) {
        try {
            boolean added = fileRepository.add(question);
            return ResponseEntity.status(HttpStatus.CREATED).body(added);
        } catch (IOException e) {
            throw new RuntimeException("Failed to add question", e);
        }
    }

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            List<Question> questions = fileRepository.findAll();
            return ResponseEntity.ok(questions);
        } catch (IOException e) {
            throw new RuntimeException("Failed to retrieve questions", e);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Question>> findQuestions(@RequestParam(required = false) String answer) {
        try {
            List<Question> questions = fileRepository.find(answer);
            return ResponseEntity.ok(questions);
        } catch (IOException e) {
            throw new RuntimeException("Failed to search questions", e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable int id) {
        try {
            Question question = fileRepository.get(id);
            if (question != null) {
                return ResponseEntity.ok(question);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to retrieve question", e);
        }
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<Boolean> updateImage(@PathVariable int id,
                                               @RequestParam("file") MultipartFile file) {
        try {
            boolean updated = fileRepository.updateImage(id, file);
            return ResponseEntity.ok(updated);
        } catch (IOException e) {
            throw new RuntimeException("Failed to update image", e);
        }
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {
        try {
            byte[] image = fileRepository.getImage(id);
            if (image != null && image.length > 0) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_PNG);
                return new ResponseEntity<>(image, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to retrieve image", e);
        }
    }
}
