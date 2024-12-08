package antidimon.web.mvcservice.services;

import antidimon.web.mvcservice.models.outputDTO.briefcase.BriefcaseStockOutputDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BriefcaseStockService {


    public HashMap<String, HashMap<String, ArrayList<Double>>> sortAllStocks(List<BriefcaseStockOutputDTO> briefcaseStocks) {
        HashMap<String, HashMap<String, ArrayList<Double>>> map = new HashMap<>();
        briefcaseStocks.forEach(briefcaseStock -> {
            String briefcase = briefcaseStock.getBriefcaseName();
            if (!map.containsKey(briefcase)) {
                HashMap<String, ArrayList<Double>> stockMap = new HashMap<>();
                ArrayList<Double> list = new ArrayList<>();
                list.add((double) briefcaseStock.getStocksAmount());
                list.add(briefcaseStock.getRevenue());
                stockMap.put(briefcaseStock.getStock().getName(), list);
                map.put(briefcase, stockMap);
            } else {
                Map<String, ArrayList<Double>> stockMap = map.get(briefcase);
                if (stockMap.containsKey(briefcaseStock.getStock().getName())) {
                    ArrayList<Double> list = stockMap.get(briefcaseStock.getStock().getName());
                    list.set(0, list.get(0) + (double)briefcaseStock.getStocksAmount());
                    list.set(1, list.get(1) + briefcaseStock.getRevenue());
                }else{
                    ArrayList<Double> list = new ArrayList<>();
                    list.add((double) briefcaseStock.getStocksAmount());
                    list.add(briefcaseStock.getRevenue());
                    stockMap.put(briefcaseStock.getStock().getName(), list);
                }

            }
        });
        return map;
    }


    public HashMap<String, ArrayList<Double>> sortStocks(List<BriefcaseStockOutputDTO> briefcaseStocks) {
        HashMap<String, ArrayList<Double>> map = new HashMap<>();
        briefcaseStocks.forEach(briefcaseStock -> {
            String briefcase = briefcaseStock.getBriefcaseName();
            if (!map.containsKey(briefcase)) {
                ArrayList<Double> list = new ArrayList<>();
                list.add((double) briefcaseStock.getStocksAmount());
                list.add(briefcaseStock.getRevenue());
                map.put(briefcase, list);
            } else {
                ArrayList<Double> list = map.get(briefcase);
                list.set(0, list.get(0) + (double)briefcaseStock.getStocksAmount());
                list.set(1, list.get(1) + briefcaseStock.getRevenue());
            }
        });
        return map;
    }

}
