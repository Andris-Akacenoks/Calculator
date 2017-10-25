package com.example.andri.kalkulators;

import java.math.BigDecimal;

import static com.example.andri.kalkulators.CalculatorAction.ADDITION;
import static com.example.andri.kalkulators.CalculatorAction.DIVISION;
import static com.example.andri.kalkulators.CalculatorAction.MULTIPLICATION;
import static com.example.andri.kalkulators.CalculatorAction.SUBTRACTION;

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
        double other;
        try {
            other = Double.parseDouble(argument);
        } catch (NullPointerException | NumberFormatException e) {
            throw new CalculatorExeption( "Trouble parsing");
        }
        if (operand == null) {
            operand = other;
        }
        else if (action != null){
            switch (action) {
                case ADDITION:
                    operand += other;break;
                case SUBTRACTION:
                    operand -= other;break;
                case MULTIPLICATION:
                    operand *= other;break;
                case DIVISION:
                    operand /= other;break;
            }
        } else {
            throw new CalculatorExeption("No operations specified");
        }

        if (operand.isInfinite() || operand.isNaN()) {
            throw new CalculatorExeption("Operand can't be represented as a number");
        }

            //putAction(null);
        }


    public void putAction(CalculatorAction calc){
        action = calc;
    }

    public String getResult(){
        double result = operand == null ? 0.0 : operand;
        return new BigDecimal(result).stripTrailingZeros().toPlainString();    }

}
