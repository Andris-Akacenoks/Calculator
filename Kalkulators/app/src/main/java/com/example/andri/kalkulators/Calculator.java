package com.example.andri.kalkulators;

public interface Calculator {
    public void clear();
    public void putOperand(String argument) throws CalculatorExeption;
    public void putAction(CalculatorAction calc);
    public String getResult();
    public CalculatorAction getCalculatorAction();
    public Double getOperand();
}
