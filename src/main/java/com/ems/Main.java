package com.ems;

import com.ems.data.DataStore;
import com.ems.util.MainRouter;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Employee Management System...\n");
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n\nSaving all data before exit...");
            DataStore.getInstance().saveAllData();
            System.out.println("Data saved successfully. Goodbye!");
        }));

        
        // Start
        MainRouter router = new MainRouter();
        router.start();
    }
}
