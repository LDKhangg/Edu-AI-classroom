package hsf.edu.classroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentViewController {

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("title", "Bảng điều khiển - Edu AI Học sinh");
        model.addAttribute("activePage", "dashboard");
        model.addAttribute("content", "student/dashboard :: dashboard-content");
        return "layout/student-layout";
    }

    @GetMapping("/assignments")
    public String assignments(Model model) {
        model.addAttribute("title", "Bài tập về nhà - Edu AI");
        model.addAttribute("activePage", "assignments");
        model.addAttribute("content", "student/assignments :: assignments-content");
        return "layout/student-layout";
    }

    @GetMapping("/lessons")
    public String lessons(Model model) {
        model.addAttribute("title", "Bài giảng - Edu AI");
        model.addAttribute("activePage", "lessons");
        // Tạm thời dùng dashboard content cho bài giảng vì chưa làm trang riêng
        model.addAttribute("content", "student/dashboard :: dashboard-content");
        return "layout/student-layout";
    }

    @GetMapping("/ai-tutor")
    public String aiTutor(Model model) {
        model.addAttribute("title", "Trợ lý AI - Edu AI");
        model.addAttribute("activePage", "ai-tutor");
        model.addAttribute("content", "student/assignments :: assignments-content");
        return "layout/student-layout";
    }
}
