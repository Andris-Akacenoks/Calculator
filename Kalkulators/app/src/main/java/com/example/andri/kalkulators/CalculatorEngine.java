package com.example.andri.kalkulators;

public class CalculatorEngine implements Calculator{
    private Double operand;
    private CalculatorAction action;

    public void clear(){
        operand = null;
        action = null;
    }

    public CalculatorAction getCalculatorAction(){
        return action;
    }

    public Double getOperand(){
        return operand;
    }

    public void putOperand(String argument) throws CalculatorExeption{
        Double argumentDouble = Double.parseDouble(argument);
        if (operand == null) {
            operand = argumentDouble;
        }
        else {
            switch (action) {
                case ADDITION:
                    operand += argumentDouble;break;
                case SUBTRACTION:
                    operand -= argumentDouble;break;
                case MULTIPLICATION:
                    operand *= argumentDouble;break;
                case DIVISION:
                    if (Double.isInfinite(operand / argumentDouble) || Double.isNaN(operand / argumentDouble)) {
                        throw new CalculatorExeption("Nepareiza vertiba: " + operand);
                    }
                    else{
                        operand /= argumentDouble;break;
                    }
                default: break;
            }
            putAction(null);
        }
    }

    public void putAction(CalculatorAction calc){
        action = calc;
    }

    public String getResult(){
        return (operand == null)? "0" : operand.toString();
    }

}
