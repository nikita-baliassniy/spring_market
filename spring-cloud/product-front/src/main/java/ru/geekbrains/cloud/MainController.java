package ru.geekbrains.cloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import ru.geekbrains.dto.common.ProductDto;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "demoFallback")
    @GetMapping("/")
    public String demo(Model model) {
        ResponseEntity restExchange = restTemplate.exchange("http://product-server/products", HttpMethod.GET, null, List.class);
        List<ProductDto> dtos = (List<ProductDto>) restExchange.getBody();
        model.addAttribute("products", dtos);
        return "index";
    }

    public String demoFallback(Model model) {
        return "Nothing to show";
    }
}
