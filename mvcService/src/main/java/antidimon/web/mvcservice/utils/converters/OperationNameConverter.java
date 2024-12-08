package antidimon.web.mvcservice.utils.converters;

import org.springframework.stereotype.Component;

@Component
public class OperationNameConverter {


    public String convert(String name) {
        switch (name) {
            case "SALE" -> {
                return "Покупка";
            }
            case "PURCHASE" -> {
                return "Продажа";
            }
            default -> {
                return "";
            }
        }
    }
}
