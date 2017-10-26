package com.example.andri.kalkulators;

public class CalculatorException extends IllegalStateException {
    public CalculatorException(String message) {
        super(message);
    }

    public CalculatorException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public CalculatorException(Throwable throwable) {
        super(throwable);
    }
}
