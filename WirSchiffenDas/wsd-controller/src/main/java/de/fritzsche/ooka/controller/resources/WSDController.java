package de.fritzsche.ooka.controller.controller;

import de.fritzsche.ooka.controller.models.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

public class WSDController {

    @PostMapping("/configuration")
    public void sendConfiguration(@RequestBody Configuration config) {

    }
}
