package com.syntech.pem.bean;

import com.syntech.pem.model.Transaction;
import com.syntech.pem.model.TransactionType;
import com.syntech.pem.repository.TransactionRepository;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartModel;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.pie.PieChartDataSet;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class ReportBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private TransactionRepository transactionRepository;

    private int selectedMonth;
    private int selectedYear;
    private PieChartModel monthlyChartModel;
    private BarChartModel yearlyChartModel;
    
    //For Drop Down
    private List<Integer> availableMonths;
    private List<Integer> availableYears;

    @PostConstruct
    public void init() {
        selectedMonth = LocalDate.now().getMonthValue();
        selectedYear = LocalDate.now().getYear();
        
        availableMonths = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        availableYears = new ArrayList<>();
        for (int year = LocalDate.now().getYear() - 10; year <= LocalDate.now().getYear(); year++) {
            availableYears.add(year);
        }
        
        generateMonthlyReport();
//        generateYearlyReport();
    }

    public void generateMonthlyReport() {
        monthlyChartModel = new PieChartModel();
        ChartData data = new ChartData();

        List<Transaction> transactions = transactionRepository.findByMonthAndYear(selectedMonth, selectedYear);
        Map<String, BigDecimal> report = aggregateTransactionsByCategory(transactions);

        List<Number> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        PieChartDataSet dataSet = new PieChartDataSet();
        for (Map.Entry<String, BigDecimal> entry : report.entrySet()) {
            labels.add(entry.getKey());
            values.add(entry.getValue().doubleValue());
        }

        dataSet.setData(values);
        data.addChartDataSet(dataSet);
        data.setLabels(labels);

        monthlyChartModel.setData(data);
    }

//    public void generateYearlyReport() {
//        yearlyChartModel = new BarChartModel();
//        ChartData data = new ChartData();
//
//        List<Transaction> transactions = transactionRepository.findByYear(selectedYear);
//        Map<String, BigDecimal> report = aggregateTransactionsByCategory(transactions);
//
//        List<Number> values = new ArrayList<>();
//        List<String> labels = new ArrayList<>();
//        BarChartDataSet dataSet = new BarChartDataSet();
//        for (Map.Entry<String, BigDecimal> entry : report.entrySet()) {
//            labels.add(entry.getKey());
//            values.add(entry.getValue().doubleValue());
//        }
//
//        dataSet.setData(values);
//        dataSet.setBackgroundColor("rgba(75,192,192,0.2)");
//        dataSet.setBorderColor("rgba(75,192,192,1)");
//
//        data.addChartDataSet(dataSet);
//        data.setLabels(labels);
//
//        yearlyChartModel.setData(data);
//    }

    private Map<String, BigDecimal> aggregateTransactionsByCategory(List<Transaction> transactions) {
        Map<String, BigDecimal> report = new HashMap<>();
        for (Transaction transaction : transactions) {
            if (transaction.getType() == TransactionType.Expense) {
                String category = transaction.getCategory().name();
                BigDecimal amount = BigDecimal.valueOf(transaction.getAmount());
                report.put(category, report.getOrDefault(category, BigDecimal.ZERO).add(amount));
            }
        }
        return report;
    }

    // Getters and Setters
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

    public PieChartModel getMonthlyChartModel() {
        return monthlyChartModel;
    }

    public BarChartModel getYearlyChartModel() {
        return yearlyChartModel;
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
