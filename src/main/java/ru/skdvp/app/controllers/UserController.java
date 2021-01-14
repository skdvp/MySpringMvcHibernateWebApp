package ru.skdvp.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.skdvp.app.model.User;
import ru.skdvp.app.service.UserService;

@Controller
@RequestMapping("/user_list")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showAllUsers(Model model) {
        // GET /user_list всех людей из DAO и передаём на отображение в представление
        model.addAttribute("key", userService.showAllUsers());

        return "/user_list/user_list";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") long id, Model model) {
        // GET /user_list/:id получим одного человека по id из DAO и передаём на отображение в представление
        model.addAttribute("key", userService.showUser(id));
        return "/user_list/show_user";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("key") User user) {
        // GET /user_list/new запрос HTML формы для создания записи
        return "/user_list/new_user";
    }

    @PostMapping()
    public String saveUser(@ModelAttribute("key") User user) {
        // POST /user_list создаём новую запись
        userService.saveUser(user);
        return "redirect:/user_list";
    }

    @GetMapping("/{id}/edit")
    // GET /user_list/:id/edit HTML форма редактирование записи
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("keyEdit", userService.showUser(id));
        return "/user_list/edit";
    }

    @PatchMapping("/{id}")
    // PATCH /user_list/:id HTML форма обновления записи
    public String updateUser(@PathVariable("id") long id, @ModelAttribute("keyEdit") User updateUser) {
        userService.updateUser(id, updateUser);
        return "redirect:/user_list";
    }

    @DeleteMapping("/{id}")
    // DELETE user_list/:id HTML форма удаления записи
    public String delete(@PathVariable("id") long id) {
        userService.removeUserById(id);
        return "redirect:/user_list";
    }

}
