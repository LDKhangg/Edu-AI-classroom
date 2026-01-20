package hsf.edu.classroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/teacher")
public class TeacherCrudController {

    // Simple in-memory stores
    private final Map<Long, Map<String, Object>> classes = new LinkedHashMap<>();
    private final Map<Long, Map<String, Object>> subjects = new LinkedHashMap<>();
    private final Map<Long, Map<String, Object>> lessons = new LinkedHashMap<>();
    private final Map<Long, Map<String, Object>> exams = new LinkedHashMap<>();
    private final Map<Long, Map<String, Object>> students = new LinkedHashMap<>();

    private final AtomicLong idGen = new AtomicLong(1);

    public TeacherCrudController() {
        // seed some data
        seed(classes, "Class A", 20);
        seed(subjects, "Mathematics");
        seed(lessons, "Intro to AI", "Class A");
        seed(exams, "Midterm AI", "Class A");
        seedStudent("Alice", "Class A");
        seedStudent("Bob", "Class A");
    }

    private void seed(Map<Long, Map<String, Object>> store, String name, Object extra) {
        long id = idGen.getAndIncrement();
        Map<String, Object> m = new HashMap<>();
        m.put("id", id);
        m.put("name", name);
        if (extra instanceof Integer) m.put("size", extra);
        else if (extra != null) m.put("className", extra);
        store.put(id, m);
    }

    private void seedStudent(String name, String className) {
        long id = idGen.getAndIncrement();
        Map<String, Object> m = new HashMap<>();
        m.put("id", id);
        m.put("name", name);
        m.put("className", className);
        students.put(id, m);
    }

    private void seed(Map<Long, Map<String, Object>> store, String name) {
        seed(store, name, null);
    }

    // Expose counts for dashboard
    public int getClassesCount() { return classes.size(); }
    public int getSubjectsCount() { return subjects.size(); }
    public int getLessonsCount() { return lessons.size(); }
    public int getExamsCount() { return exams.size(); }
    public int getStudentsCount() { return students.size(); }

    // Classes
    @GetMapping("/class/list")
    public String classList(Model model) {
        model.addAttribute("classes", classes.values());
        return "teacher/class/list";
    }

    @GetMapping("/class/form")
    public String classForm(@RequestParam(required = false) Long id, Model model) {
        if (id != null) model.addAttribute("class", classes.get(id));
        return "teacher/class/form";
    }

    @PostMapping("/class/save")
    public String classSave(@RequestParam Map<String, String> params) {
        Long id = params.containsKey("id") && !params.get("id").isEmpty() ? Long.valueOf(params.get("id")) : null;
        if (id == null) id = idGen.getAndIncrement();
        Map<String, Object> m = new HashMap<>();
        m.put("id", id);
        m.put("name", params.getOrDefault("name", ""));
        m.put("size", Integer.parseInt(params.getOrDefault("size", "0")));
        classes.put(id, m);
        return "redirect:/teacher/class/list";
    }

    @GetMapping("/class/detail/{id}")
    public String classDetail(@PathVariable Long id, Model model) {
        model.addAttribute("class", classes.get(id));
        return "teacher/class/detail";
    }

    // Subjects
    @GetMapping("/subject/list")
    public String subjectList(Model model) {
        model.addAttribute("subjects", subjects.values());
        return "teacher/subject/list";
    }

    @GetMapping("/subject/form")
    public String subjectForm(@RequestParam(required = false) Long id, Model model) {
        if (id != null) model.addAttribute("subject", subjects.get(id));
        return "teacher/subject/form";
    }

    @PostMapping("/subject/save")
    public String subjectSave(@RequestParam Map<String, String> params) {
        Long id = params.containsKey("id") && !params.get("id").isEmpty() ? Long.valueOf(params.get("id")) : idGen.getAndIncrement();
        Map<String, Object> m = new HashMap<>();
        m.put("id", id);
        m.put("name", params.getOrDefault("name", ""));
        subjects.put(id, m);
        return "redirect:/teacher/subject/list";
    }

    @GetMapping("/subject/detail/{id}")
    public String subjectDetail(@PathVariable Long id, Model model) {
        model.addAttribute("subject", subjects.get(id));
        return "teacher/subject/detail";
    }

    // Lessons
    @GetMapping("/lesson/list")
    public String lessonList(Model model) {
        model.addAttribute("lessons", lessons.values());
        return "teacher/lesson/list";
    }

    @GetMapping("/lesson/form")
    public String lessonForm(@RequestParam(required = false) Long id, Model model) {
        if (id != null) model.addAttribute("lesson", lessons.get(id));
        model.addAttribute("classes", classes.values());
        return "teacher/lesson/form";
    }

    @PostMapping("/lesson/save")
    public String lessonSave(@RequestParam Map<String, String> params) {
        Long id = params.containsKey("id") && !params.get("id").isEmpty() ? Long.valueOf(params.get("id")) : idGen.getAndIncrement();
        Map<String, Object> m = new HashMap<>();
        m.put("id", id);
        m.put("title", params.getOrDefault("title", ""));
        m.put("className", params.getOrDefault("className", ""));
        lessons.put(id, m);
        return "redirect:/teacher/lesson/list";
    }

    @GetMapping("/lesson/detail/{id}")
    public String lessonDetail(@PathVariable Long id, Model model) {
        model.addAttribute("lesson", lessons.get(id));
        return "teacher/lesson/detail";
    }

    // Exams
    @GetMapping("/exam/list")
    public String examList(Model model) {
        model.addAttribute("exams", exams.values());
        return "teacher/exam/list";
    }

    @GetMapping("/exam/form")
    public String examForm(@RequestParam(required = false) Long id, Model model) {
        if (id != null) model.addAttribute("exam", exams.get(id));
        model.addAttribute("classes", classes.values());
        return "teacher/exam/form";
    }

    @PostMapping("/exam/save")
    public String examSave(@RequestParam Map<String, String> params) {
        Long id = params.containsKey("id") && !params.get("id").isEmpty() ? Long.valueOf(params.get("id")) : idGen.getAndIncrement();
        Map<String, Object> m = new HashMap<>();
        m.put("id", id);
        m.put("title", params.getOrDefault("title", ""));
        m.put("className", params.getOrDefault("className", ""));
        exams.put(id, m);
        return "redirect:/teacher/exam/list";
    }

    @GetMapping("/exam/detail/{id}")
    public String examDetail(@PathVariable Long id, Model model) {
        model.addAttribute("exam", exams.get(id));
        return "teacher/exam/detail";
    }

    // Students
    @GetMapping("/student/list")
    public String studentList(Model model) {
        model.addAttribute("students", students.values());
        return "teacher/student/list";
    }

    @GetMapping("/student/form")
    public String studentForm(@RequestParam(required = false) Long id, Model model) {
        if (id != null) model.addAttribute("student", students.get(id));
        model.addAttribute("classes", classes.values());
        return "teacher/student/form";
    }

    @PostMapping("/student/save")
    public String studentSave(@RequestParam Map<String, String> params) {
        Long id = params.containsKey("id") && !params.get("id").isEmpty() ? Long.valueOf(params.get("id")) : idGen.getAndIncrement();
        Map<String, Object> m = new HashMap<>();
        m.put("id", id);
        m.put("name", params.getOrDefault("name", ""));
        m.put("className", params.getOrDefault("className", ""));
        students.put(id, m);
        return "redirect:/teacher/student/list";
    }

    @GetMapping("/student/detail/{id}")
    public String studentDetail(@PathVariable Long id, Model model) {
        model.addAttribute("student", students.get(id));
        return "teacher/student/detail";
    }

}
