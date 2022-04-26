package fourstacks.vanguard.demo.domain.goal.service;


import fourstacks.vanguard.demo.domain.goal.exceptions.GoalNotFoundException;
import fourstacks.vanguard.demo.domain.goal.model.Goal;
import fourstacks.vanguard.demo.domain.goal.repo.GoalRepo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
public class GoalServiceImpl implements GoalService {
    private static Logger logger = LoggerFactory.getLogger(GoalServiceImpl.class);
    private GoalRepo goalRepo;

    @Autowired
    public GoalServiceImpl(GoalRepo goalRepo){
        this.goalRepo = goalRepo;
    }

    @Override
    public Goal create(Goal goal) {
        calculateProgress(goal);
        return goalRepo.save(goal);
    }

    private static void calculateProgress(Goal goal){
        Double amountAlreadySaved = goal.getAmountAlreadySaved();
        Double targetSavingsAmount = goal.getTargetSavingsAmount();
        Double decimal = (amountAlreadySaved / targetSavingsAmount) * 100;
        goal.setProgressPercentage(decimal);
        Double amountLeft = targetSavingsAmount - amountAlreadySaved;
        goal.setAmountLeftUntilGoal(amountLeft);
        logger.info("ProgressBar complete for goal");
    }

    @Override
    public Goal getById(Long id) throws GoalNotFoundException {
        Optional<Goal> goalOptional = goalRepo.findById(id);
        if(goalOptional.isEmpty())
            throw new GoalNotFoundException("Goal does not exist");
        return goalOptional.get();
    }

    @Override
    public Goal update(Goal goal) throws GoalNotFoundException {
        Long id = goal.getId();
        calculateProgress(goal);
        Optional<Goal> goalOptional= goalRepo.findById(id);
        if (goalOptional.isEmpty())
            throw new GoalNotFoundException("Goal not found");
        return goalRepo.save(goal);
    }

    @Override
    public void delete(Long id) throws GoalNotFoundException {
        Optional<Goal> goalOptional = goalRepo.findById(id);
        if(goalOptional.isEmpty())
            throw new GoalNotFoundException("Goal not found");
        goalRepo.delete(goalOptional.get());
    }

    @Override
    public Iterable<Goal> findAll() throws GoalNotFoundException {
        return goalRepo.findAll();
    }

}
