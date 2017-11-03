package com.example.andri.kalkulators;

import java.math.BigDecimal;


public class CalculatorEngine implements Calculator {
    private Double           operand;
    private CalculatorAction action;

    @Override
    public void clear() {
        operand = null;
        action = null;
    }

    @Override
    public void putOperand(String otherString) throws CalculatorException {
        double other;
        try {
            other = Double.parseDouble(otherString);
        } catch (NullPointerException | NumberFormatException e) {
            throw new CalculatorException("Input parsing failed", e);
        }
        if (operand == null) {
            operand = other;
        } else if (action != null) {
            switch (action) {
                case ADDITION:
                    operand = add(operand, other);
                    break;
                case SUBSTRACTION:
                    operand = sub(operand, other);
                    break;
                case MULTIPLICATION:
                    operand = mul(operand, other);
                    break;
                case DIVISION:
                    operand = div(operand, other);
                    break;
            }
            action = null;
        } else {
            throw new CalculatorException("No operations specified");
        }

        if (operand.isInfinite() || operand.isNaN()) {
            throw new CalculatorException("Operand can't be represented as a number");
        }
    }

    @Override
    public void putAction(CalculatorAction action) {
        this.action = action;
    }

    @Override
    public String getResult() {
        double result = operand == null ? 0.0 : operand;
        return new BigDecimal(result).stripTrailingZeros().toPlainString();

    }
    @Override
    public Double getOperand(){
        return operand;
    }

    @Override
    public String getActionToString() {
        switch(action){
            case ADDITION:
                return "+";
            case SUBSTRACTION:
                return "-";
            case MULTIPLICATION:
                return "*";
            case DIVISION:
                return "/";
            default:
                return "";
        }
    }



    private double add(double operand, double other) {
        return operand + other;
    }

    private double sub(double operand, double other) {
        return operand - other;
    }

    private double mul(double operand, double other) {
        return operand * other;
    }

    private double div(double operand, double other) {
        return operand / other;
    }
}
