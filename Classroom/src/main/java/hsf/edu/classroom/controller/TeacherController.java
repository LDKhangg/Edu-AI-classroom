package hsf.edu.classroom.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherCrudController crudController;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("teacherName", "Teacher");
        model.addAttribute("classesCount", crudController.getClassesCount());
        model.addAttribute("subjectsCount", crudController.getSubjectsCount());
        model.addAttribute("lessonsCount", crudController.getLessonsCount());
        model.addAttribute("examsCount", crudController.getExamsCount());
        model.addAttribute("studentsCount", crudController.getStudentsCount());
        return "teacher/dashboard";
    }

}
