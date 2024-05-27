package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private JobRepository jobRepository;

    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("title", "MyJobs");

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
	model.addAttribute("title", "Add Job");
        model.addAttribute(new Job());
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                       Errors errors, Model model, @RequestParam int employerId, @RequestParam List<Integer> skills) {

        if (errors.hasErrors()) {
	    model.addAttribute("title", "Add Job");
            return "add";
        }

        Optional<Employer> optionalEmployer = employerRepository.findById(employerId);
        Employer chosenEmployer;
        if (optionalEmployer.isPresent()) {
            chosenEmployer = optionalEmployer.get();
            newJob.setEmployer(chosenEmployer);
        }

        List<Skill> skillObjects = (List<Skill>) skillRepository.findAllById(skills);
        // wanted to wrap this in an if-clause `if (!skillObjects.isEmpty()){}`, but it causes test to fail
        newJob.setSkills(skillObjects);

        jobRepository.save(newJob);

        return "redirect:/list";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {
            Optional<Job> result = jobRepository.findById(jobId);
            if (result.isEmpty()){
                return "redirect:../";
            } else {
                Job job = result.get();
                model.addAttribute("job", job);
            }


            return "view";
    }

}
