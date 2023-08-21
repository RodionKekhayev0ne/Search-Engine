package Main;

import Main.model.Job;
import Main.model.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class SampleController {

    @Autowired
    JobRepo jobRepo;

    @RequestMapping("/")
    public String index(Model model){
        Iterable<Job> jobIterable = jobRepo.findAll();
        ArrayList<Job> tasks = new ArrayList<>();
        for (Job task : jobIterable){
            tasks.add(task);
        }

        model.addAttribute("tasks",tasks)
                .addAttribute("tasksCount", tasks.size());

        return "index";
    }

}
