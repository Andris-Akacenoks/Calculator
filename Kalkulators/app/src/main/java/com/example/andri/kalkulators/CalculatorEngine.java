package com.example.andri.kalkulators;

public class CalculatorEngine {
    private Double operand;
    private CalculatorAction action;

    public void clear(){
        operand = null;
        action = null;
    }

    public void putOperand(String argument){
        Double argumentDouble = Double.parseDouble(argument);
        if(operand == null){
            operand = argumentDouble;
        }
        else {
            switch(action){
                case ADDITION:
                    operand+=argumentDouble; break;
                case SUBTRACTION:
                    operand-=argumentDouble; break;
                case MULTIPLICATION:
                    operand*=argumentDouble; break;
                case DIVISION:
                    operand/=argumentDouble; break;
                default:
                    break;
            }
        }
        action = null;
    }

    public String getResult(){
        return (operand == null)? "0" : operand.toString();
    }

}
