package com.amm.manmlab.main;

import com.amm.manmlab.controller.MainController;
import com.amm.manmlab.implementations.FileInputLoaderImplementation;
import java.awt.EventQueue;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainController mainController = new MainController(
                    new FileInputLoaderImplementation()
            );
            mainController.startApplication();
        });
    }
    
}