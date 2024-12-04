package antidimon.web.mvcservice.controllers;

import antidimon.web.mvcservice.models.inputDTO.user.PhoneDTO;
import antidimon.web.mvcservice.models.outputDTO.briefcase.BriefcaseOutputDTO;
import antidimon.web.mvcservice.models.outputDTO.briefcase.BriefcaseStockOutputDTO;
import antidimon.web.mvcservice.models.outputDTO.stock.PriceOutputDTO;
import antidimon.web.mvcservice.models.outputDTO.stock.StockOutputDTO;
import antidimon.web.mvcservice.services.JwtService;
import antidimon.web.mvcservice.services.RestTemplateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/stocks")
@AllArgsConstructor
public class StockController {

    private final RestTemplateService restTemplateService;
    private final JwtService jwtService;

    @GetMapping
    public String stockInfo(HttpServletRequest request, Model model, @RequestParam("name") String name) {
        String phoneNumber = jwtService.getPhoneNumber(request.getCookies());

        List<BriefcaseStockOutputDTO> briefcaseStocks = restTemplateService.sendToGetUserStocks(phoneNumber, name);
        StockOutputDTO stock =  restTemplateService.sendToGetStock(name);
        List<PriceOutputDTO> prices = stock.getPrices();
        List<BriefcaseOutputDTO> briefcases = restTemplateService.sendToGetUserBriefcases(new PhoneDTO(phoneNumber));

        model.addAttribute("stock", stock);
        model.addAttribute("prices", prices);
        model.addAttribute("briefcaseStocks", briefcaseStocks);
        model.addAttribute("briefcases", briefcases);

        return "stock";

    }

    @GetMapping("/search")
    public String search(Model model, List<StockOutputDTO> stocks) {
        model.addAttribute("stocks", stocks);
        return "search";
    }

    @PostMapping("/search")
    public String doSearch(@RequestParam(value = "query", required = false) String name,
                    @RequestParam(value = "filter", required = false) String filter,
                    @RequestParam(value = "from", required = false) Double from,
                    @RequestParam(value = "to", required = false) Double to, Model model) {
        if (from == null) from = (double) 0;
        if (to == null) to = (double) 999999999;
        List<StockOutputDTO> stocks = restTemplateService.sendToGetStocks(name, filter, from, to);
        model.addAttribute("stocks", stocks);
        return "search";
    }

    @PostMapping("/sell")
    public String sellStock(HttpServletRequest request, @RequestParam("briefcaseName") String briefcaseName, @RequestParam("stockName") String stockName,
                            @RequestParam("amount") int amount, Model model) {

        String phoneNumber = jwtService.getPhoneNumber(request.getCookies());
        if (!restTemplateService.sendToSellStocks(phoneNumber, briefcaseName, stockName, amount)){
            model.addAttribute("amountError", true);
            return stockInfo(request, model, stockName);
        }
        model.addAttribute("successOperation", true);
        return stockInfo(request, model, stockName);
    }

    @PostMapping("/buy")
    public String buyStock(HttpServletRequest request, @RequestParam("briefcaseName") String briefcaseName, @RequestParam("stockName") String stockName,
                           @RequestParam("amount") int amount, Model model){
        String phoneNumber = jwtService.getPhoneNumber(request.getCookies());
        if (!restTemplateService.sendToBuyStocks(phoneNumber, briefcaseName, stockName, amount)){
            model.addAttribute("amountError", true);
            return stockInfo(request, model, stockName);
        }
        model.addAttribute("successOperation", true);
        return stockInfo(request, model, stockName);
    }
}