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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;

/**
 *
 * @author shrijanakarki
 */

@Named
@ViewScoped
public class ReportBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private TransactionRepository transactionRepository;
    

    private String selectedMonth;
    private int selectedYear;
    private Map<String, BigDecimal> monthlyExpenseReport;
    private Map<String, BigDecimal> monthlyIncomeReport;
    private Map<String, BigDecimal> yearlyExpenseReport;
    private Map<String, BigDecimal> yearlyIncomeReport;

    private List<String> availableMonths;

    private BarChartModel monthlyExpenseBarModel;
    private BarChartModel monthlyIncomeBarModel;
    private BarChartModel yearlyExpenseBarModel;
    private BarChartModel yearlyIncomeBarModel;

    @PostConstruct
    public void init() {
        
        availableMonths = Arrays.asList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        );
        
        selectedMonth = availableMonths.get(LocalDate.now().getMonthValue() - 1);
        selectedYear = LocalDate.now().getYear();

        // Generate initial reports and bar models
        generateReports();
        createMonthlyExpenseBarModel();
        createMonthlyIncomeBarModel();
        createYearlyExpenseBarModel();
        createYearlyIncomeBarModel();
    }

   
    
    public void generateReports() {
        int monthIndex = availableMonths.indexOf(selectedMonth) + 1;

        monthlyExpenseReport = generateExpenseReport(monthIndex, selectedYear);
        monthlyIncomeReport = generateIncomeReport(monthIndex, selectedYear);
        yearlyExpenseReport = generateExpenseReport(selectedYear);
        yearlyIncomeReport = generateIncomeReport(selectedYear);

        // Regenerate the chart models with the updated report data
        createMonthlyExpenseBarModel();
        createMonthlyIncomeBarModel();
        createYearlyExpenseBarModel();
        createYearlyIncomeBarModel();
    }

    public Map<String, BigDecimal> generateExpenseReport(int month, int year) {
        List<Transaction> transactions = transactionRepository.findByMonthAndYear(month, year);
        return aggregateTransactionsByCategory(transactions, TransactionType.Expense);
    }

    public Map<String, BigDecimal> generateIncomeReport(int month, int year) {
        List<Transaction> transactions = transactionRepository.findByMonthAndYear(month, year);
        return aggregateTransactionsByCategory(transactions, TransactionType.Income);
    }

    public Map<String, BigDecimal> generateExpenseReport(int year) {
        List<Transaction> transactions = transactionRepository.findByYear(year);
        return aggregateTransactionsByCategory(transactions, TransactionType.Expense);
    }

    public Map<String, BigDecimal> generateIncomeReport(int year) {
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

    private void createMonthlyExpenseBarModel() {
        monthlyExpenseBarModel = createBarModel(monthlyExpenseReport);
    }

    private void createMonthlyIncomeBarModel() {
        monthlyIncomeBarModel = createBarModel(monthlyIncomeReport);
    }

    private void createYearlyExpenseBarModel() {
        yearlyExpenseBarModel = createBarModel(yearlyExpenseReport);
    }

    private void createYearlyIncomeBarModel() {
        yearlyIncomeBarModel = createBarModel(yearlyIncomeReport);
    }

    private BarChartModel createBarModel(Map<String, BigDecimal> reportData) {
        BarChartModel barModel = new BarChartModel();

        // Create chart data
        ChartData data = new ChartData();
        BarChartDataSet barDataSet = new BarChartDataSet();

        // Set different background colors for each category
        List<String> backgroundColors = new ArrayList<>();
        List<String> borderColors = new ArrayList<>();

        // Generate or define a list of colors
        List<String> colors = Arrays.asList(
                "rgba(255, 99, 132, 0.2)", // Red
                "rgba(54, 162, 235, 0.2)", // Blue
                "rgba(255, 206, 86, 0.2)", // Yellow
                "rgba(75, 192, 192, 0.2)", // Green
                "rgba(153, 102, 255, 0.2)", // Purple
                "rgba(255, 159, 64, 0.2)" // Orange
        );

        List<String> borderColorsList = Arrays.asList(
                "rgba(255, 99, 132, 1)",
                "rgba(54, 162, 235, 1)",
                "rgba(255, 206, 86, 1)",
                "rgba(75, 192, 192, 1)",
                "rgba(153, 102, 255, 1)",
                "rgba(255, 159, 64, 1)"
        );

        // Assign colors to each category
        for (int i = 0; i < reportData.size(); i++) {
            backgroundColors.add(colors.get(i % colors.size())); // Repeat colors if categories exceed color list
            borderColors.add(borderColorsList.get(i % borderColorsList.size()));
        }

        barDataSet.setBackgroundColor(backgroundColors);
        barDataSet.setBorderColor(borderColors);
        barDataSet.setBorderWidth(1);

        // Add data to dataset
        barDataSet.setData(new ArrayList<>(reportData.values()));

        // Add dataset to data
        data.addChartDataSet(barDataSet);

        // Add labels to data - use category names directly as labels
        data.setLabels(new ArrayList<>(reportData.keySet()));

        // Set data to model
        barModel.setData(data);

        // Create chart options
        BarChartOptions options = new BarChartOptions();
        options.setResponsive(true);
        options.setMaintainAspectRatio(false);

        // Set scales
        CartesianScales scales = new CartesianScales();
        CartesianLinearAxes yAxes = new CartesianLinearAxes();
        yAxes.setBeginAtZero(true);
        scales.addYAxesData(yAxes);

        CartesianLinearAxes xAxes = new CartesianLinearAxes();
        xAxes.setBeginAtZero(true);
        scales.addXAxesData(xAxes);
        options.setScales(scales);

        // Configure Legend
        Legend legend = new Legend();
        legend.setDisplay(false); //Disable the legend
        options.setLegend(legend);

        // Set options to model
        barModel.setOptions(options);

        return barModel;
    }

    // Getters and Setters for Bar Models
    public BarChartModel getMonthlyExpenseBarModel() {
        return monthlyExpenseBarModel;
    }

    public BarChartModel getMonthlyIncomeBarModel() {
        return monthlyIncomeBarModel;
    }

    public BarChartModel getYearlyExpenseBarModel() {
        return yearlyExpenseBarModel;
    }

    public BarChartModel getYearlyIncomeBarModel() {
        return yearlyIncomeBarModel;
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

    public String getSelectedMonth() {
        return selectedMonth;
    }

    public void setSelectedMonth(String selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    public List<String> getAvailableMonths() {
        return availableMonths;
    }

    public void setAvailableMonths(List<String> availableMonths) {
        this.availableMonths = availableMonths;
    }

    public void incrementMonth() {
        int monthIndex = availableMonths.indexOf(selectedMonth);
        if (monthIndex < availableMonths.size() - 1) {
            selectedMonth = availableMonths.get(monthIndex + 1);
        } else {
            selectedMonth = availableMonths.get(0);
            selectedYear++;
        }
        generateReports();
    }

    public void decrementMonth() {
        int monthIndex = availableMonths.indexOf(selectedMonth);
        if (monthIndex > 0) {
            selectedMonth = availableMonths.get(monthIndex - 1);
        } else {
            selectedMonth = availableMonths.get(availableMonths.size() - 1);
            selectedYear--;
        }
        generateReports();
    }

    public void incrementYear() {
        selectedYear++;
        generateReports();
    }

    public void decrementYear() {
        selectedYear--;
        generateReports();
    }
}
