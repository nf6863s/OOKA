package resources;

import models.OilSystemOption;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OilSystemController {

    @RequestMapping("/status")
    public String getStatus() {
        return "200";
    }

    @PostMapping("/analyze")
    public String analyzeComponent(@RequestBody OilSystemOption option) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Test";
    }

}
