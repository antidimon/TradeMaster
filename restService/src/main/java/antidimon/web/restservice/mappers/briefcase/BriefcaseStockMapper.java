package antidimon.web.restservice.mappers.briefcase;

import antidimon.web.restservice.mappers.stock.StockMapper;
import antidimon.web.restservice.models.dto.output.briefcases.BriefcaseStockOutputDTO;
import antidimon.web.restservice.models.entities.briefcase.BriefcaseStock;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BriefcaseStockMapper {

    private StockMapper stockMapper;

    private final ModelMapper modelMapper = new ModelMapper();


    public BriefcaseStockOutputDTO toOutputDTO(BriefcaseStock briefcaseStock) {
        BriefcaseStockOutputDTO output = modelMapper.map(briefcaseStock, BriefcaseStockOutputDTO.class);
        output.setStock(stockMapper.toOutputDTO(briefcaseStock.getStock()));
        output.setBriefcaseName(briefcaseStock.getBriefcase().getBriefcaseName());
        return output;
    }
}
