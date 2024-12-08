package antidimon.web.mvcservice.controllers;


import antidimon.web.mvcservice.models.outputDTO.briefcase.BriefcaseOutputDTO;
import antidimon.web.mvcservice.models.outputDTO.briefcase.BriefcaseStockOutputDTO;
import antidimon.web.mvcservice.security.MyUser;
import antidimon.web.mvcservice.services.AuthService;
import antidimon.web.mvcservice.services.BriefcaseStockService;
import antidimon.web.mvcservice.services.JwtService;
import antidimon.web.mvcservice.services.RestTemplateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@Slf4j
public class MainController {

    private final JwtService jwtService;
    private final AuthService authService;
    private final RestTemplateService restTemplateService;
    private final BriefcaseStockService briefcaseStockService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model, @RequestParam(required = false, name = "unhide") boolean flag) {
        String phoneNumber = jwtService.getPhoneNumber(request.getCookies());
        MyUser user = authService.findByPhoneNumber(phoneNumber);

        model.addAttribute("user", user);
        if (flag) model.addAttribute("unhide", true);

        List<BriefcaseOutputDTO> briefcases = restTemplateService.sendToGetUserBriefcases(phoneNumber);
        List<BriefcaseStockOutputDTO> briefcaseStocks = restTemplateService.sendToGetUserStocks(phoneNumber, "");

        HashMap<String, HashMap<String, ArrayList<Double>>> map = briefcaseStockService.sortAllStocks(briefcaseStocks);

        model.addAttribute("briefcases", briefcases);
        model.addAttribute("groupedStocks", map);

        HashMap<String, String> stocksUrls = new HashMap<>();
        HashMap<String, BriefcaseStockOutputDTO> stocks = new HashMap<>();
        briefcaseStocks.forEach(stock -> {
            String stockName = stock.getStock().getName();
            if (!stocksUrls.containsKey(stockName)) {
                stocksUrls.put(stockName, stock.getStock().getUrl());
                stocks.put(stock.getStock().getName(), stock);
            }
        });

        model.addAttribute("stocks", stocks);
        model.addAttribute("stocksUrls", stocksUrls);
        return "main";
    }

    @GetMapping("/news")
    public String getNews() {
        return "news";
    }
}
