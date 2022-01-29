package com.l2hyunwoo.kotlinspringbootdemo.web

import com.l2hyunwoo.kotlinspringbootdemo.config.auth.dto.SessionUser
import com.l2hyunwoo.kotlinspringbootdemo.service.posts.PostsService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import javax.servlet.http.HttpSession

@Controller
class IndexController(
    private val postsService: PostsService,
    private val httpSession: HttpSession
) {
    @GetMapping("/")
    fun index(model: Model): String {
        model.addAttribute("posts", postsService.findAllDesc())
        (httpSession.getAttribute("user") as? SessionUser?)?.let { model.addAttribute("userName", it.name) }
        return "index"
    }

    @GetMapping("/posts/save")
    fun postsSave() = "posts-save"

    @GetMapping("/posts/update/{id}")
    fun postsUpdate(@PathVariable id: Long, model: Model): String {
        model.addAttribute("post", postsService.findById(id))
        return "posts-update"
    }
}