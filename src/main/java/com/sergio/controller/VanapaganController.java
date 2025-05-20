package com.sergio.controller;

import com.sergio.dto.UpCommand;
import com.sergio.service.VanapaganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vanapagan")
public class VanapaganController {

    private VanapaganService vanapaganService;

    @Autowired
    public VanapaganController(VanapaganService vanapaganService) {
        this.vanapaganService = vanapaganService;
    }

    @GetMapping("/about")
    public void about() {
        vanapaganService.getAbout();
    }
}
