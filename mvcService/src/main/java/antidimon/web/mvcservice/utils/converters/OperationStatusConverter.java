package antidimon.web.mvcservice.utils.converters;

import antidimon.web.mvcservice.models.entities.OperationStatuses;
import org.springframework.stereotype.Component;

@Component
public class OperationStatusConverter {


    public String convert(String status){
        switch (status){
            case "CREATED" -> {
                return "Создано";
            }
            case "CANCELED" -> {
                return "Отменено";
            }
            case "SUCCEEDED" -> {
                return "Выполнено";
            }
            case "WAITING_FOR_CAPTURE" -> {
                return "Ожидает выполнения";
            }
            default -> {
                return "";
            }
        }
    }
}
