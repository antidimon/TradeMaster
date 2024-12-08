package antidimon.web.mvcservice.controllers;

import antidimon.web.mvcservice.models.outputDTO.briefcase.BriefcaseOutputDTO;
import antidimon.web.mvcservice.models.outputDTO.briefcase.BriefcaseStockOutputDTO;
import antidimon.web.mvcservice.models.outputDTO.stock.PriceOutputDTO;
import antidimon.web.mvcservice.models.outputDTO.stock.StockOutputDTO;
import antidimon.web.mvcservice.services.BriefcaseStockService;
import antidimon.web.mvcservice.services.ExcelService;
import antidimon.web.mvcservice.services.JwtService;
import antidimon.web.mvcservice.services.RestTemplateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/stocks")
@AllArgsConstructor
public class StockController {

    private final RestTemplateService restTemplateService;
    private final JwtService jwtService;
    private final BriefcaseStockService briefcaseStockService;
    private final ExcelService excelService;

    @GetMapping
    public String stockInfo(HttpServletRequest request, Model model, @RequestParam("name") String name) {
        String phoneNumber = jwtService.getPhoneNumber(request.getCookies());

        StockOutputDTO stock =  restTemplateService.sendToGetStock(name);
        List<PriceOutputDTO> prices = stock.getPrices();
        List<BriefcaseStockOutputDTO> briefcaseStocks = restTemplateService.sendToGetUserStocks(phoneNumber, name);
        List<BriefcaseOutputDTO> briefcases = restTemplateService.sendToGetUserBriefcases(phoneNumber);

        Map<String, ArrayList<Double>> map = briefcaseStockService.sortStocks(briefcaseStocks);

        model.addAttribute("stock", stock);
        model.addAttribute("prices", prices);
        model.addAttribute("briefcaseStocks", briefcaseStocks);
        model.addAttribute("groupedStocks", map);
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
                            @RequestParam("amount") int amount, @RequestParam("stocksPerLot") int stocksPerLot, Model model) {

        String phoneNumber = jwtService.getPhoneNumber(request.getCookies());
        if (amount <= 0) {
            model.addAttribute("sellError", "Amount must be greater than 0");
            return stockInfo(request, model, stockName);
        }
        if (!restTemplateService.sendToSellStocks(phoneNumber, briefcaseName, stockName, amount, stocksPerLot)){
            model.addAttribute("sellAmountError", true);
            return stockInfo(request, model, stockName);
        }
        return stockInfo(request, model, stockName);
    }

    @PostMapping("/buy")
    public String buyStock(HttpServletRequest request, @RequestParam("briefcaseName") String briefcaseName, @RequestParam("stockName") String stockName,
                           @RequestParam("amount") int amount, @RequestParam("stocksPerLot") int stocksPerLot, Model model){
        String phoneNumber = jwtService.getPhoneNumber(request.getCookies());
        if (amount <= 0) {
            model.addAttribute("buyError", "Amount must be greater than 0");
            return stockInfo(request, model, stockName);
        }
        if (!restTemplateService.sendToBuyStocks(phoneNumber, briefcaseName, stockName, amount, stocksPerLot)){
            model.addAttribute("buyAmountError", true);
            return stockInfo(request, model, stockName);
        }
        return stockInfo(request, model, stockName);
    }

    @GetMapping("/getExcel")
    public ResponseEntity<ByteArrayResource> getExcelStock(@RequestParam("name") String stockName) throws IOException {

        List<StockOutputDTO> stocks = restTemplateService.sendToGetStockData(stockName);
        ByteArrayOutputStream outputStream = excelService.generateStockExcel(stocks);

        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=stock_" + stockName + ".xlsx");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(outputStream.size())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}