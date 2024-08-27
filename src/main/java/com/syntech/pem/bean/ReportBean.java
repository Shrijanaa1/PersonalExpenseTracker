package com.syntech.pem.bean;

import com.syntech.pem.model.Transaction;
import com.syntech.pem.model.TransactionType;
import com.syntech.pem.repository.TransactionRepository;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.pie.PieChartModel;

@Named
@ViewScoped
public class ReportBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Inject
    private TransactionRepository transactionRepository;

    private int selectedMonth;
    private int selectedYear;
    private Map<String, BigDecimal> monthlyReport;
    private Map<String, BigDecimal> yearlyReport;
    

//    private PieChartModel pieModel;
//    private BarChartModel barModel;

    private List<Integer> availableMonths;
    private List<Integer> availableYears;

    @PostConstruct
    public void init() {
        
        //Initialize selected month and year to the current date
        selectedMonth = LocalDate.now().getMonthValue();
        selectedYear = LocalDate.now().getYear();
        
        //Initialize available months (1 to 12) and available years (last 10 years including current)
        availableMonths = IntStream.rangeClosed(1, 12).boxed().collect(Collectors.toList());
        availableYears = IntStream.rangeClosed(selectedYear - 10, selectedYear).boxed().collect(Collectors.toList());
        
        //Generate initial reports
        generateMonthlyReport();
        generateYearlyReport();
    }

    public void generateMonthlyReport() {
        //Fetch transactions for the selected month and year
        List<Transaction> transactions = transactionRepository.findByMonthAndYear(selectedMonth, selectedYear);
        monthlyReport = aggregateTransactionsByCategory(transactions);
//        createPieModel(monthlyReport);
//        createBarModel(monthlyReport);
    }

    public void generateYearlyReport() {
        List<Transaction> transactions = transactionRepository.findByYear(selectedYear);
        yearlyReport = aggregateTransactionsByCategory(transactions);
//        createPieModel(yearlyReport);
//        createBarModel(yearlyReport);
    }

    private Map<String, BigDecimal> aggregateTransactionsByCategory(List<Transaction> transactions) {
        Map<String, BigDecimal> report = new HashMap<>();
        
        //Iterate over transactions and sum the amounts by category
        for (Transaction transaction : transactions) {
            if (transaction.getType() == TransactionType.Expense) {
                String category = transaction.getCategory().name();
                BigDecimal amount = transaction.getAmount();
                
                report.put(category, report.getOrDefault(category, BigDecimal.ZERO).add(amount));
            }
        }
        return report;
    }
    
    

//    private void createPieModel(Map<String, BigDecimal> report) {
//        pieModel = new PieChartModel();
//        for (Map.Entry<String, BigDecimal> entry : report.entrySet()) {
//            pieModel.set(entry.getKey(), entry.getValue());
//        }
//        pieModel.setTitle("Expenses by Category");
//        pieModel.setLegendPosition("w");
//        pieModel.setShowDataLabels(true);
//        pieModel.setDataFormat("value");
//    }
//
//    private void createBarModel(Map<String, BigDecimal> report) {
//        barModel = new BarChartModel();
//        ChartSeries expenses = new ChartSeries();
//        expenses.setLabel("Expenses");
//
//        for (Map.Entry<String, BigDecimal> entry : report.entrySet()) {
//            expenses.set(entry.getKey(), entry.getValue());
//        }
//        barModel.addSeries(expenses);
//        barModel.setTitle("Expenses by Category");
//        barModel.setLegendPosition("ne");
//        barModel.setAnimate(true);
//    }

//    public PieChartModel getPieModel() {
//        return pieModel;
//    }
//
//    public BarChartModel getBarModel() {
//        return barModel;
//    }

    public int getSelectedMonth() {
        return selectedMonth;
    }

    public void setSelectedMonth(int selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    public int getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(int selectedYear) {
        this.selectedYear = selectedYear;
    }

    public Map<String, BigDecimal> getMonthlyReport() {
        return monthlyReport;
    }

    public void setMonthlyReport(Map<String, BigDecimal> monthlyReport) {
        this.monthlyReport = monthlyReport;
    }

    public Map<String, BigDecimal> getYearlyReport() {
        return yearlyReport;
    }

    public void setYearlyReport(Map<String, BigDecimal> yearlyReport) {
        this.yearlyReport = yearlyReport;
    }

    public List<Integer> getAvailableMonths() {
        return availableMonths;
    }

    public void setAvailableMonths(List<Integer> availableMonths) {
        this.availableMonths = availableMonths;
    }

    public List<Integer> getAvailableYears() {
        return availableYears;
    }

    public void setAvailableYears(List<Integer> availableYears) {
        this.availableYears = availableYears;
    }
}
