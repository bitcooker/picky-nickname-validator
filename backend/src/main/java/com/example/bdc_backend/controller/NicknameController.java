package com.example.bdc_backend.controller;

import org.springframework.web.bind.annotation.*;
import com.example.bdc_backend.model.*;
import com.example.bdc_backend.service.*;

@RestController
@RequestMapping("/nickname")
@CrossOrigin(origins = "*")
public class NicknameController {

    private final NicknameService service;

    public NicknameController(NicknameService service) {
        this.service = service;
    }

    @PostMapping
    public Object setNickname(@RequestBody NicknameRequest request) {
        return service.validate(request.nickname())
                .<Object>map(n -> n)
                .orElseGet(() -> new NicknameResponse(request.nickname()));
    }
}
