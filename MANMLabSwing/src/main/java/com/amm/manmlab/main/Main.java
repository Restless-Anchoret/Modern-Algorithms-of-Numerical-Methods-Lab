package com.amm.manmlab.main;

import com.amm.manmlab.controller.MainController;
import java.awt.EventQueue;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainController mainController = new MainController();
            mainController.startApplication();
        });
    }
    
}