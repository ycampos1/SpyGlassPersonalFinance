package fourstacks.vanguard.demo.domain.goal.controller;

import fourstacks.vanguard.demo.domain.goal.exceptions.GoalNotFoundException;
import fourstacks.vanguard.demo.domain.goal.model.Goal;
import fourstacks.vanguard.demo.domain.goal.service.GoalService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goal")
@Slf4j
public class GoalController {

    private GoalService goalService;


    @Autowired
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping("")
    public ResponseEntity<Goal> create(@RequestBody Goal goal) {
        goal = goalService.create(goal);
        return new ResponseEntity<>(goal, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Goal> requestUser(@PathVariable Long id) throws GoalNotFoundException {
        Goal response = goalService.getById(id);
        log.info(response.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Goal>> getAll() throws GoalNotFoundException {
        Iterable<Goal> all = goalService.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<?> updateGoal(@RequestBody Goal goal) {
        try {
            Goal updatedGoal = goalService.update(goal);
            ResponseEntity response = new ResponseEntity(updatedGoal, HttpStatus.OK);
            return response;
        } catch (GoalNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoal(@PathVariable Long id) {
        try {
            goalService.delete(id);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        } catch (GoalNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
}
