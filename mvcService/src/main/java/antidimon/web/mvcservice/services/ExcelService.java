package antidimon.web.mvcservice.services;

import antidimon.web.mvcservice.models.outputDTO.operation.OperationDetails;
import antidimon.web.mvcservice.models.outputDTO.operation.OperationDetailsProjection;
import antidimon.web.mvcservice.models.outputDTO.stock.PriceOutputDTO;
import antidimon.web.mvcservice.models.outputDTO.stock.StockOutputDTO;
import antidimon.web.mvcservice.utils.converters.OperationNameConverter;
import antidimon.web.mvcservice.utils.converters.OperationStatusConverter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExcelService {
    private final OperationStatusConverter operationStatusConverter;
    private final OperationNameConverter operationNameConverter;

    public ExcelService(OperationStatusConverter operationStatusConverter, OperationNameConverter operationNameConverter) {
        this.operationStatusConverter = operationStatusConverter;
        this.operationNameConverter = operationNameConverter;
    }

    public ByteArrayOutputStream generateExcelOperationsDetails(List<OperationDetails> operationsDetails) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Данные операций");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID операции");
        headerRow.createCell(1).setCellValue("ФИО");
        headerRow.createCell(2).setCellValue("Паспортные данные");
        headerRow.createCell(3).setCellValue("Номер телефона");
        headerRow.createCell(4).setCellValue("Название портфеля");
        headerRow.createCell(5).setCellValue("Статус операции");
        headerRow.createCell(6).setCellValue("Название операции");
        headerRow.createCell(7).setCellValue("Название акции");
        headerRow.createCell(8).setCellValue("Количество лотов");
        headerRow.createCell(9).setCellValue("Цена акции");
        headerRow.createCell(10).setCellValue("Акций в 1 лоте");
        headerRow.createCell(11).setCellValue("Дата операции");
        headerRow.createCell(12).setCellValue("Итоговая сумма");

        int rowNum = 1;
        for (OperationDetails details : operationsDetails) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(details.getOperationId());
            row.createCell(1).setCellValue(details.getFio());
            row.createCell(2).setCellValue(details.getPassportDetails());
            row.createCell(3).setCellValue(details.getPhoneNumber());
            row.createCell(4).setCellValue(details.getBriefcaseName());
            row.createCell(5).setCellValue(operationStatusConverter.convert(details.getOperationStatus()));
            row.createCell(6).setCellValue(operationNameConverter.convert(details.getOperationName()));
            row.createCell(7).setCellValue(details.getStockName());
            row.createCell(8).setCellValue(details.getStocksAmount());
            row.createCell(9).setCellValue(details.getStockPrice());
            row.createCell(10).setCellValue(details.getStocksPerLot());
            row.createCell(11).setCellValue(details.getOperationTime());
            row.createCell(12).setCellValue(details.getTotalAmount());
        }

        for (int i = 0; i < 13; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream;
    }

    public ByteArrayOutputStream generateStockExcel(List<StockOutputDTO> stocks) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Данные об акции");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Цена");
        headerRow.createCell(1).setCellValue("Акций в 1 лоте");
        headerRow.createCell(2).setCellValue("Дата");

        int rowNum = 1;
        for (StockOutputDTO stock : stocks) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(stock.getPrice());
            row.createCell(1).setCellValue(stock.getStocksPerLot());
            row.createCell(2).setCellValue(stock.getGettedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }

        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFChart chart = drawing.createChart(drawing.createAnchor(0, 0, 0, 0, 5, 5, 15, 30));
        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.TOP_RIGHT);

        XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        bottomAxis.setTitle("Время");
        XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
        leftAxis.setTitle("Цена");

        XDDFDataSource<String> time = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(1, rowNum - 1, 2, 2));
        XDDFNumericalDataSource<Double> values = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, rowNum - 1, 0, 0));

        XDDFLineChartData data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, leftAxis);
        XDDFLineChartData.Series series = (XDDFLineChartData.Series) data.addSeries(time, values);
        series.setTitle("Цена акции", null);
        chart.plot(data);

        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream;
    }
}
