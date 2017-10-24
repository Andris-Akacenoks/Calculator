package com.example.andri.kalkulators;

public enum CalculatorAction {
    ADDITION("+"),
    SUBTRACTION("-"),
    MULTIPLICATION("*"),
    DIVISION("/");

    public final String argument;

    CalculatorAction(String argument) {
        this.argument = argument;
    }
}
