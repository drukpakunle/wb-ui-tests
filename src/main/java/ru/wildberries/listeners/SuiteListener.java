package ru.wildberries.listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class SuiteListener implements ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        System.out.println("\n----------============ Suite start ============----------\n");
    }

    @Override
    public void onFinish(ISuite suite) {
        System.out.println("\n----------============ Suite finish ============----------\n");
    }

}