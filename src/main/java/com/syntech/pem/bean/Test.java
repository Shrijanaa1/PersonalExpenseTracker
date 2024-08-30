package com.syntech.pem.bean;

import com.syntech.pem.model.Transaction;
import com.syntech.pem.model.TransactionType;
import com.syntech.pem.repository.TransactionRepository;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.title.Title;
//
////import org.primefaces.model.chart.Axis;
////import org.primefaces.model.chart.AxisType;
////import org.primefaces.model.chart.BarChartModel;
////import org.primefaces.model.chart.HorizontalBarChartModel;
////import org.primefaces.model.chart.ChartSeries;
////import org.primefaces.model.charts.bar.BarChartModel;
////import org.primefaces.model.charts.hbar.HorizontalBarChartModel;
////
////
////@Named
////@ViewScoped
////public class Test implements Serializable {
////
////    private BarChartModel barModel;
////    private HorizontalBarChartModel horizontalBarModel;
////
////	public Test() {
////        createBarModels();
////	}
////
////    public BarChartModel getBarModel() {
////        return barModel;
////    }
////    
////    public HorizontalBarChartModel getHorizontalBarModel() {
////        return horizontalBarModel;
////    }
////
////    private BarChartModel initBarModel() {
////        BarChartModel model = new BarChartModel();
////
////        ChartSeries boys = new ChartSeries();
////        boys.setLabel("Boys");
////        boys.set("2004", 120);
////        boys.set("2005", 100);
////        boys.set("2006", 44);
////        boys.set("2007", 150);
////        boys.set("2008", 25);
////
////        ChartSeries girls = new ChartSeries();
////        girls.setLabel("Girls");
////        girls.set("2004", 52);
////        girls.set("2005", 60);
////        girls.set("2006", 110);
////        girls.set("2007", 135);
////        girls.set("2008", 120);
////
////        model.addSeries(boys);
////        model.addSeries(girls);
////        
////        return model;
////    }
////    
////    private void createBarModels() {
////        createBarModel();
////        createHorizontalBarModel();
////    }
////    
////    private void createBarModel() {
////        barModel = initBarModel();
////        
////        barModel.setTitle("Bar Chart");
////        barModel.setLegendPosition("ne");
////        
////        Axis xAxis = barModel.getAxis(AxisType.X);
////        xAxis.setLabel("Gender");
////        
////        Axis yAxis = barModel.getAxis(AxisType.Y);
////        yAxis.setLabel("Births");
////        yAxis.setMin(0);
////        yAxis.setMax(200);
////    }
////    
////    private void createHorizontalBarModel() {
////        horizontalBarModel = new HorizontalBarChartModel();
////
////        ChartSeries boys = new ChartSeries();
////        boys.setLabel("Boys");
////        boys.set("2004", 50);
////        boys.set("2005", 96);
////        boys.set("2006", 44);
////        boys.set("2007", 55);
////        boys.set("2008", 25);
////
////        ChartSeries girls = new ChartSeries();
////        girls.setLabel("Girls");
////        girls.set("2004", 52);
////        girls.set("2005", 60);
////        girls.set("2006", 82);
////        girls.set("2007", 35);
////        girls.set("2008", 120);
////
////        horizontalBarModel.addSeries(boys);
////        horizontalBarModel.addSeries(girls);
////        
////        horizontalBarModel.setTitle("Horizontal and Stacked");
////        horizontalBarModel.setLegendPosition("e");
////        horizontalBarModel.setStacked(true);
////        
////        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
////        xAxis.setLabel("Births");
////        xAxis.setMin(0);
////        xAxis.setMax(200);
////        
////        Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
////        yAxis.setLabel("Gender");        
////    }
////
////}
//
////import com.fasterxml.jackson.core.JsonProcessingException;
////import com.fasterxml.jackson.databind.ObjectMapper;
////import com.syntech.pem.model.TransactionType;
////import com.syntech.pem.repository.TransactionRepository;
////import javax.inject.Named;
////import javax.enterprise.context.RequestScoped;
////import javax.inject.Inject;
////import java.util.List;
////
////@Named
////@RequestScoped
////public class Test {
////
////    @Inject
////    private TransactionRepository transactionRepository;
////
////    private List<Object[]> incomeData;
////    private List<Object[]> expenseData;
////    
////    private int year = 2024; // Default year, adjust as needed
////    
////    public void generateReports() {
////        incomeData = transactionRepository.findSumByCategoryAndType(TransactionType.Income, year);
////        expenseData = transactionRepository.findSumByCategoryAndType(TransactionType.Expense, year);
////    }
////    
////    public List<Object[]> getIncomeData() {
////        return incomeData;
////    }
////
////    public List<Object[]> getExpenseData() {
////        return expenseData;
////    }
////
////    public int getYear() {
////        return year;
////    }
////
////    public void setYear(int year) {
////        this.year = year;
////    }
////    
////    public String getIncomeDataAsJson() {
////        return convertToJson(incomeData);
////    }
////
////    public String getExpenseDataAsJson() {
////        return convertToJson(expenseData);
////    }
////    
////    private String convertToJson(List<Object[]> data) {
////        ObjectMapper mapper = new ObjectMapper();
////        try {
////            return mapper.writeValueAsString(data);
////        } catch (JsonProcessingException e) {
////            e.printStackTrace();
////            return "[]";
////        }
////    }
////}


@Named
@ViewScoped
public class Test implements Serializable {

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

//public void incrementDay() {
//    if (selectedDay < LocalDate.of(selectedYear, selectedMonth, 1).lengthOfMonth()) {
//        selectedDay++;
//    } else {
//        selectedDay = 1;
//        incrementMonth();
//    }
//    generateReports();
//}
//
//public void decrementDay() {
//    if (selectedDay > 1) {
//        selectedDay--;
//    } else {
//        decrementMonth();
//        selectedDay = LocalDate.of(selectedYear, selectedMonth, 1).lengthOfMonth();
//    }
//    generateReports();
//}

}





//Executabl


//@Named
//@ViewScoped
//public class Test implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    
//    private BarChartModel barModel;
//
//    @PostConstruct
//    public void init() {
//        createBarModel();
//    }
//
//   public void createBarModel() {
//
//    barModel = new BarChartModel();  
//
//    // Create chart data
//    ChartData data = new ChartData();
//    
//     // First dataset
//        BarChartDataSet barDataSet1 = new BarChartDataSet();
//        barDataSet1.setLabel("My First Dataset");
//        barDataSet1.setBackgroundColor("rgba(255, 99, 132, 0.2)");
//        barDataSet1.setBorderColor("rgb(255, 99, 132)");
//        barDataSet1.setBorderWidth(1);
//        barDataSet1.setData(Arrays.asList(65, 59, 80, 81, 56, 55, 40));
//
//        // Second dataset
//        BarChartDataSet barDataSet2 = new BarChartDataSet();
//        barDataSet2.setLabel("My Second Dataset");
//        barDataSet2.setBackgroundColor("rgba(255, 159, 64, 0.2)");
//        barDataSet2.setBorderColor("rgb(255, 159, 64)");
//        barDataSet2.setBorderWidth(1);
//        barDataSet2.setData(Arrays.asList(85, 69, 20, 51, 76, 75, 10));
//
//        // Add datasets to data
//        data.addChartDataSet(barDataSet1);
//        data.addChartDataSet(barDataSet2);
//
//        // Add labels to data
//        List<String> labels = Arrays.asList("January", "February", "March", "April", "May", "June", "July");
//        data.setLabels(labels);
//
//        // Set data to model
//        barModel.setData(data);
//
//        // Create chart options
//        BarChartOptions options = new BarChartOptions();
//        options.setResponsive(true);
//        options.setMaintainAspectRatio(false);
//
//        // Set scales
//        CartesianScales scales = new CartesianScales();
//        CartesianLinearAxes yAxes = new CartesianLinearAxes();
//        yAxes.setBeginAtZero(true);
//        scales.addYAxesData(yAxes);
//
//        CartesianLinearAxes xAxes = new CartesianLinearAxes();
//        xAxes.setBeginAtZero(true);
//        scales.addXAxesData(xAxes);
//        options.setScales(scales);
//
//        // Configure Title
//        Title title = new Title();
//        title.setDisplay(true);
//        title.setText("Bar Chart using PrimeFaces 14");
//        options.setTitle(title);
//
//        // Configure Legend
//        Legend legend = new Legend();
//        legend.setDisplay(true);
//        options.setLegend(legend);
//
////        // Configure Tooltip (if needed)
////        Tooltip tooltip = new Tooltip();
////        tooltip.setEnabled(true);  // Enabling the tooltip
////        options.setTooltip(tooltip);
//
//        // Set options to model
//        barModel.setOptions(options);
//    }
//
//
//
//
//    public BarChartModel getBarModel() {
//        return barModel;
//    }
//}





















//Not executable


////import java.io.Serializable;
////import java.util.ArrayList;
////import java.util.List;
////import javax.annotation.PostConstruct;
////import javax.faces.view.ViewScoped;
////import javax.inject.Named;
////import org.primefaces.model.charts.ChartData;
////import org.primefaces.model.charts.bar.BarChartDataSet;
////import org.primefaces.model.charts.bar.BarChartOptions;
////import org.primefaces.model.charts.axes.cartesian.CartesianLinearAxes;
////import org.primefaces.model.charts.axes.cartesian.CartesianScales;
////import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
////import org.primefaces.model.charts.optionconfig.title.Title;
////import org.primefaces.model.charts.options.Title;
////
////
////@Named
////@ViewScoped
////public class Test implements Serializable {
////
////    private ChartData data;
////
////    @PostConstruct
////    public void init() {
////        data = new ChartData();
////
////        BarChartDataSet barDataSet = new BarChartDataSet();
////        barDataSet.setLabel("My Bar Chart");
////        barDataSet.setBackgroundColor("rgba(75, 192, 192, 0.2)");
////        barDataSet.setBorderColor("rgb(75, 192, 192)");
////        barDataSet.setBorderWidth(1);
////        
////        List<Number> values = new ArrayList<>();
////        values.add(65);
////        values.add(59);
////        values.add(80);
////        values.add(81);
////        values.add(56);
////        values.add(55);
////        values.add(40);
////        barDataSet.setData(values);
////
////        data.addChartDataSet(barDataSet);
////
////        List<String> labels = new ArrayList<>();
////        labels.add("January");
////        labels.add("February");
////        labels.add("March");
////        labels.add("April");
////        labels.add("May");
////        labels.add("June");
////        labels.add("July");
////        data.setLabels(labels);
////
////        //Options
////        BarChartOptions options = new BarChartOptions();
////        CartesianScales scales = new CartesianScales();
////        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
////        linearAxes.setOffset(true);
////        linearAxes.setBeginAtZero(true);
////        scales.addXAxesData(linearAxes);
////        scales.addYAxesData(linearAxes);
////        options.setScales(scales);
////
////        Title title = new Title();
////        title.setDisplay(true);
////        title.setText("Bar Chart");
////        options.setTitle(title);
////
////        barChartModel.setOptions(options);
////    }
////}
//
//
//
////model bhitra redirect garne
////ui ma input mai rakhne, input ni garna milyo, select garda ni change hunu paryo

//package com.syntech.pem.bean;
//import com.syntech.pem.model.TransactionType;
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.time.YearMonth;
//import java.time.format.TextStyle;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//import javax.annotation.PostConstruct;
//import javax.faces.view.ViewScoped;
//import javax.inject.Named;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//
//import org.primefaces.model.charts.bar.BarChartModel;
//import org.primefaces.model.charts.ChartData;
//import org.primefaces.model.charts.bar.BarChartDataSet;
//
//@Named
//@ViewScoped
//public class Test implements Serializable {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    private BarChartModel monthlyBarModel;
//    private BarChartModel yearlyBarModel;
//
//    @PostConstruct
//    public void init() {
//        createMonthlyBarModel();
//        createYearlyBarModel();
//    }
//
//    private void createMonthlyBarModel() {
//        monthlyBarModel = new BarChartModel();
//        ChartData data = new ChartData();
//
//        BarChartDataSet incomeDataset = new BarChartDataSet();
//        incomeDataset.setLabel("Monthly Income");
//        incomeDataset.setBackgroundColor("rgba(75, 192, 192, 0.2)");
//        incomeDataset.setBorderColor("rgb(75, 192, 192)");
//        incomeDataset.setBorderWidth(1);
//
//        BarChartDataSet expenseDataset = new BarChartDataSet();
//        expenseDataset.setLabel("Monthly Expense");
//        expenseDataset.setBackgroundColor("rgba(255, 99, 132, 0.2)");
//        expenseDataset.setBorderColor("rgb(255, 99, 132)");
//        expenseDataset.setBorderWidth(1);
//
//        // List to hold the data for each dataset
//        List<Object> incomeData = new ArrayList<>();
//        List<Object> expenseData = new ArrayList<>();
//
//        // Get all months
//        String[] labels = new String[12];
//        for (int i = 0; i < 12; i++) {
//            labels[i] = YearMonth.of(0, i + 1).getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
//        }
//        data.setLabels(labels);
//
//        // Query database and populate data lists
//        for (int i = 1; i <= 12; i++) {
//            TypedQuery<BigDecimal> incomeQuery = entityManager.createQuery(
//                "SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE FUNCTION('MONTH', t.date) = :month AND t.type = :type", BigDecimal.class);
//            incomeQuery.setParameter("month", i);
//            incomeQuery.setParameter("type", TransactionType.Income);
//            BigDecimal income = incomeQuery.getSingleResult();
//            incomeData.add(income);
//
//            TypedQuery<BigDecimal> expenseQuery = entityManager.createQuery(
//                "SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE FUNCTION('MONTH', t.date) = :month AND t.type = :type", BigDecimal.class);
//            expenseQuery.setParameter("month", i);
//            expenseQuery.setParameter("type", TransactionType.Expense);
//            BigDecimal expense = expenseQuery.getSingleResult();
//            expenseData.add(expense);
//        }
//
//        // Convert BigDecimal to Object
//        List<Object> incomeDataAsObjects = new ArrayList<>(incomeData);
//        List<Object> expenseDataAsObjects = new ArrayList<>(expenseData);
//
//        // Set data to datasets
//        incomeDataset.setData(incomeDataAsObjects);
//        expenseDataset.setData(expenseDataAsObjects);
//
//        data.addChartDataSet(incomeDataset);
//        data.addChartDataSet(expenseDataset);
//
//        monthlyBarModel.setData(data);
//    }
//
//    private void createYearlyBarModel() {
//        yearlyBarModel = new BarChartModel();
//        ChartData data = new ChartData();
//
//        BarChartDataSet incomeDataset = new BarChartDataSet();
//        incomeDataset.setLabel("Yearly Income");
//        incomeDataset.setBackgroundColor("rgba(75, 192, 192, 0.2)");
//        incomeDataset.setBorderColor("rgb(75, 192, 192)");
//        incomeDataset.setBorderWidth(1);
//
//        BarChartDataSet expenseDataset = new BarChartDataSet();
//        expenseDataset.setLabel("Yearly Expense");
//        expenseDataset.setBackgroundColor("rgba(255, 99, 132, 0.2)");
//        expenseDataset.setBorderColor("rgb(255, 99, 132)");
//        expenseDataset.setBorderWidth(1);
//
//        List<Object> incomeData = new ArrayList<>();
//        List<Object> expenseData = new ArrayList<>();
//
//        // Define labels as years (replace with your range)
//        String[] labels = {"2022", "2023", "2024", "2025"};
//        data.setLabels(labels);
//
//        // Query database and populate data lists
//        for (String year : labels) {
//            TypedQuery<BigDecimal> incomeQuery = entityManager.createQuery(
//                "SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE FUNCTION('YEAR', t.date) = :year AND t.type = :type", BigDecimal.class);
//            incomeQuery.setParameter("year", Integer.parseInt(year));
//            incomeQuery.setParameter("type", TransactionType.Income);
//            BigDecimal income = incomeQuery.getSingleResult();
//            incomeData.add(income);
//
//            TypedQuery<BigDecimal> expenseQuery = entityManager.createQuery(
//                "SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE FUNCTION('YEAR', t.date) = :year AND t.type = :type", BigDecimal.class);
//            expenseQuery.setParameter("year", Integer.parseInt(year));
//            expenseQuery.setParameter("type", TransactionType.Expense);
//            BigDecimal expense = expenseQuery.getSingleResult();
//            expenseData.add(expense);
//        }
//
//        // Convert BigDecimal to Object
//        List<Object> incomeDataAsObjects = new ArrayList<>(incomeData);
//        List<Object> expenseDataAsObjects = new ArrayList<>(expenseData);
//
//        // Set data to datasets
//        incomeDataset.setData(incomeDataAsObjects);
//        expenseDataset.setData(expenseDataAsObjects);
//
//        data.addChartDataSet(incomeDataset);
//        data.addChartDataSet(expenseDataset);
//
//        yearlyBarModel.setData(data);
//    }
//
//    public BarChartModel getMonthlyBarModel() {
//        return monthlyBarModel;
//    }
//
//    public BarChartModel getYearlyBarModel() {
//        return yearlyBarModel;
//    }
//}
