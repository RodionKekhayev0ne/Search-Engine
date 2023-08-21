package Main;

import Main.model.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import Main.model.Job;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
//@Validated
@RequestMapping
public class Controller {

    @Autowired
    private JobRepo jobRepo;


    @PostMapping("/tasks/")
    public int add(String task) {
        Job job = new Job(task);
        jobRepo.save(job);
        return job.getId();
    }

    @DeleteMapping("/tasks/{id}")
    public int delete(@PathVariable int id) {
        jobRepo.deleteById(id);
        return 0;
    }

    @DeleteMapping("/tasks/")
    public int deleteAll() {
        jobRepo.deleteAll();
        return 0;
    }

    @PutMapping("/tasks")
    public ResponseEntity change(@RequestParam Integer id, @RequestParam String task) {
        Optional<Job> optionalJob = jobRepo.findById(id);

        if (optionalJob.isPresent()) {
            optionalJob.get().setTask(task);
            return new ResponseEntity(optionalJob.get(),HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity get(@PathVariable Integer id) {

      Optional<Job> optionalJob = jobRepo.findById(id);

        if (optionalJob.isPresent()) {

            return new ResponseEntity(optionalJob.get().getTask(),HttpStatus.OK);

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("/tasks/")
    public List<Job> list()
    {
        Iterable<Job> iterableJob = jobRepo.findAll();
        List<Job> jobList = new ArrayList<>();
        for(Job job : iterableJob){
            jobList.add(job);
        }
        return jobList;
    }


}
