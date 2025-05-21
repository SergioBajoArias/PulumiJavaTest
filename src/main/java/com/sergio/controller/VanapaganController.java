package com.sergio.controller;

import com.sergio.dto.VanapaganResponse;
import com.sergio.service.VanapaganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vanapagan")
public class VanapaganController {

    private VanapaganService vanapaganService;

    @Autowired
    public VanapaganController(VanapaganService vanapaganService) {
        this.vanapaganService = vanapaganService;
    }

    @GetMapping("/about")
    public VanapaganResponse about() {
        return vanapaganService.getAbout();
    }
}
