package com.example.andri.kalkulators;

public interface Calculator {
    void clear();

    void putOperand(String otherString) throws CalculatorException;

    void putAction(CalculatorAction action);

    String getResult();

    Double getOperand();

    String getActionToString();
}
