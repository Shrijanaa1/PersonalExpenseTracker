package com.syntech.pem.bean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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


@Named
@ViewScoped
public class ReportBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Inject
    private TransactionRepository transactionRepository;

    private int selectedMonth;
    private int selectedYear;
    private Map<String, BigDecimal> monthlyExpenseReport;
    private Map<String, BigDecimal> monthlyIncomeReport;
    private Map<String, BigDecimal> yearlyExpenseReport;
    private Map<String, BigDecimal> yearlyIncomeReport;
    

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
        generateReports();
    }
    
    public void generateReports(){
        monthlyExpenseReport = generateExpenseReport(selectedMonth, selectedYear);
        monthlyIncomeReport = generateIncomeReport(selectedMonth, selectedYear);
        yearlyExpenseReport = generateExpenseReport(selectedYear);
        yearlyIncomeReport = generateIncomeReport(selectedYear);
    }
    
    
    public Map<String, BigDecimal> generateExpenseReport(int month, int year){
        List<Transaction> transactions = transactionRepository.findByMonthAndYear(month, year);
        return aggregateTransactionsByCategory(transactions, TransactionType.Expense);
    }
    
    public Map<String, BigDecimal> generateIncomeReport(int month, int year){
        List<Transaction> transactions = transactionRepository.findByMonthAndYear(month, year);
        return aggregateTransactionsByCategory(transactions, TransactionType.Income);
    }
    
    public Map<String, BigDecimal> generateExpenseReport(int year){
        List<Transaction> transactions = transactionRepository.findByYear(year);
        return aggregateTransactionsByCategory(transactions, TransactionType.Expense);
    }
    
     public Map<String, BigDecimal> generateIncomeReport(int year){
        List<Transaction> transactions = transactionRepository.findByYear(year);
        return aggregateTransactionsByCategory(transactions, TransactionType.Income);
    }


    private Map<String, BigDecimal> aggregateTransactionsByCategory(List<Transaction> transactions, TransactionType type) {
        Map<String, BigDecimal> report = new HashMap<>();
        
        for (Transaction transaction : transactions) {
            if (transaction.getType() == type) {
                String category = transaction.getCategory().name();
                BigDecimal amount = transaction.getAmount();
                report.put(category, report.getOrDefault(category, BigDecimal.ZERO).add(amount));
            }
        }
        return report;
    }
    
    
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

    public Map<String, BigDecimal> getMonthlyExpenseReport() {
        return monthlyExpenseReport;
    }

    public void setMonthlyExpenseReport(Map<String, BigDecimal> monthlyExpenseReport) {
        this.monthlyExpenseReport = monthlyExpenseReport;
    }

    public Map<String, BigDecimal> getMonthlyIncomeReport() {
        return monthlyIncomeReport;
    }

    public void setMonthlyIncomeReport(Map<String, BigDecimal> monthlyIncomeReport) {
        this.monthlyIncomeReport = monthlyIncomeReport;
    }

    public Map<String, BigDecimal> getYearlyExpenseReport() {
        return yearlyExpenseReport;
    }

    public void setYearlyExpenseReport(Map<String, BigDecimal> yearlyExpenseReport) {
        this.yearlyExpenseReport = yearlyExpenseReport;
    }

    public Map<String, BigDecimal> getYearlyIncomeReport() {
        return yearlyIncomeReport;
    }

    public void setYearlyIncomeReport(Map<String, BigDecimal> yearlyIncomeReport) {
        this.yearlyIncomeReport = yearlyIncomeReport;
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
    
    // Method to convert report data to JSON
    public String getMonthlyExpenseReportAsJson() {
        return convertMapToJson(monthlyExpenseReport);
    }

    public String getMonthlyIncomeReportAsJson() {
        return convertMapToJson(monthlyIncomeReport);
    }

    public String getYearlyExpenseReportAsJson() {
        return convertMapToJson(yearlyExpenseReport);
    }

    public String getYearlyIncomeReportAsJson() {
        return convertMapToJson(yearlyIncomeReport);
    }

    private String convertMapToJson(Map<String, BigDecimal> reportData) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(reportData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}"; // Return empty JSON object in case of error
        }
    }
}
