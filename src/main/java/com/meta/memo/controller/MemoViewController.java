package com.meta.memo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemoViewController {
    @GetMapping("/mymemo")
    public String home() {
        return "index";

    }
}
