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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.title.Title;

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

    private BarChartModel monthlyExpenseBarModel;
    private BarChartModel monthlyIncomeBarModel;
    private BarChartModel yearlyExpenseBarModel;
    private BarChartModel yearlyIncomeBarModel;

    @PostConstruct
    public void init() {
        // Initialize selected month and year to the current date
        selectedMonth = LocalDate.now().getMonthValue();
        selectedYear = LocalDate.now().getYear();

        // Initialize available months (1 to 12) and available years (last 10 years including current)
        availableMonths = IntStream.rangeClosed(1, 12).boxed().collect(Collectors.toList());
        availableYears = IntStream.rangeClosed(selectedYear - 10, selectedYear).boxed().collect(Collectors.toList());

        // Generate initial reports and bar models
        generateReports();
        createMonthlyExpenseBarModel();
        createMonthlyIncomeBarModel();
        createYearlyExpenseBarModel();
        createYearlyIncomeBarModel();
    }

    public void generateReports() {
        monthlyExpenseReport = generateExpenseReport(selectedMonth, selectedYear);
        monthlyIncomeReport = generateIncomeReport(selectedMonth, selectedYear);
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
        monthlyExpenseBarModel = createBarModel(monthlyExpenseReport, "Monthly Expense Report");
    }

    private void createMonthlyIncomeBarModel() {
        monthlyIncomeBarModel = createBarModel(monthlyIncomeReport, "Monthly Income Report");
    }

    private void createYearlyExpenseBarModel() {
        yearlyExpenseBarModel = createBarModel(yearlyExpenseReport, "Yearly Expense Report");
    }

    private void createYearlyIncomeBarModel() {
        yearlyIncomeBarModel = createBarModel(yearlyIncomeReport, "Yearly Income Report");
    }

    private BarChartModel createBarModel(Map<String, BigDecimal> reportData, String title) {
        BarChartModel barModel = new BarChartModel();

        // Create chart data
        ChartData data = new ChartData();
        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel(title);
        barDataSet.setBackgroundColor("rgba(75, 192, 192, 0.2)");
        barDataSet.setBorderColor("rgb(75, 192, 192)");
        barDataSet.setBorderWidth(1);

        // Add data to dataset
        barDataSet.setData(new ArrayList<>(reportData.values()));

        // Add dataset to data
        data.addChartDataSet(barDataSet);

        // Add labels to data
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

        // Configure Title
        Title chartTitle = new Title();
        chartTitle.setDisplay(true);
        chartTitle.setText(title);
        options.setTitle(chartTitle);

        // Configure Legend
        Legend legend = new Legend();
        legend.setDisplay(true);
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

    // Getters and Setters for other fields...
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

    public void incrementMonth() {
        if (selectedMonth < 12) {
            selectedMonth++;
        } else {
            selectedMonth = 1;
            incrementYear();
        }
        generateReports();
    }

    public void decrementMonth() {
        if (selectedMonth > 1) {
            selectedMonth--;
        } else {
            selectedMonth = 12;
            decrementYear();
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


