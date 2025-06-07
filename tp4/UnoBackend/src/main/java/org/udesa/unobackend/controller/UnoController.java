package org.udesa.unobackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.UUID;

@Controller
public class UnoController {
    @PostMapping("newmatch") public ResponseEntity newMatch(@RequestParam List<String> players ) {
        return ResponseEntity.ok(UUID.randomUUID().toString());
    }
}