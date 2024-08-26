package com.syntech.pem.bean;

import com.syntech.pem.model.Transaction;
import com.syntech.pem.model.TransactionType;
import com.syntech.pem.repository.TransactionRepository;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author shrijanakarki
 */

@Named
@ViewScoped
public class ReportBean implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Inject
    private TransactionRepository transactionRepository;
    
    private int selectedMonth;
    private int selectedYear;
    private Map<String, BigDecimal> monthlyReport;
    private Map<String, BigDecimal> yearlyReport;
    
    @PostConstruct
    public void init(){
        selectedMonth = LocalDate.now().getMonthValue();
        selectedYear = LocalDate.now().getYear();
        generateMonthlyReport();
        generateYearlyReport();
    }
    
    public void generateMonthlyReport(){
        List<Transaction> transactions = transactionRepository.findByMonthAndYear(selectedMonth, selectedYear);
        monthlyReport = aggregateTransactionsByCategory(transactions);
    }
    
    public void generateYearlyReport(){
        List<Transaction> transactions = transactionRepository.findByYear(selectedYear);
        yearlyReport = aggregateTransactionsByCategory(transactions);
                

    }
    
    private Map<String, BigDecimal> aggregateTransactionsByCategory(List<Transaction> transactions){
        Map<String, BigDecimal> report = new HashMap<>();
        
        for(Transaction transaction: transactions){
            if(transaction.getType() == TransactionType.Expense){
                String category = transaction.getCategory().name();
                BigDecimal amount = BigDecimal.valueOf(transaction.getAmount());
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
    
    
}
