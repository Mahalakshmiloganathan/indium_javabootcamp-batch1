package com.indium.bankingapp.service;


import com.indium.bankingapp.model.Account;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class FileHandler {
    private Map<String, Account> accountMap;

    public FileHandler() {
        accountMap = new HashMap<>();
    }
    private static final String IMPORT_FILE_PATH = "./input/input.txt";
    private static final String EXPORT_FILE_PATH = "./output/output.txt";
    private static final int NUM_THREADS = 2;

    private static ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
    private static AtomicInteger exportThreadCount = new AtomicInteger(0);
    private static AtomicInteger importThreadCount = new AtomicInteger(0);


    public static void importAccounts(HashMap<String, Account> accounts) {
        System.out.println("Importing accounts...");

        executorService.execute(() -> {
            System.out.println("Import thread started.");
            importThreadCount.incrementAndGet();

            try (FileReader reader = new FileReader(IMPORT_FILE_PATH);
                 BufferedReader bufferedReader = new BufferedReader(reader)) {
                int accountId = 1;
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        String accountNumber = parts[0];
                        String accountHolderName = parts[1];
                        double balance = Double.parseDouble(parts[2]);
                        String type = parts[3];
                        Account account = new Account(accountId, accountNumber, accountHolderName, balance, type);
                        accounts.put(String.valueOf(accountId), account);
                        accountId++;
                    }
                }
                System.out.println("Accounts imported successfully from " + IMPORT_FILE_PATH);
            } catch (IOException | NumberFormatException e) {
                System.out.println("Error importing accounts from file: " + e.getMessage());
            } finally {
                importThreadCount.decrementAndGet();
                System.out.println("Import thread finished.");
            }
        });
    }
    public static void exportAccounts(HashMap<String, Account> accounts) {
        System.out.println("Exporting accounts...");

        executorService.execute(() -> {
            System.out.println("Export thread started.");
            exportThreadCount.incrementAndGet();

            try (FileWriter writer = new FileWriter(EXPORT_FILE_PATH)) {
                for (Account account : accounts.values()) {
                    String accountDetails = account.toString();
                    writer.write(accountDetails + "\n");
                }
                System.out.println("Accounts exported successfully to " + EXPORT_FILE_PATH);
            } catch (IOException e) {
                System.out.println("Error exporting accounts to file: " + e.getMessage());
            } finally {
                exportThreadCount.decrementAndGet();
                System.out.println("Export thread finished.");
            }
        });
    }



    public static void shutdownExecutor() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted during shutdown: " + e.getMessage());
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("All threads have finished.");
    }
}

