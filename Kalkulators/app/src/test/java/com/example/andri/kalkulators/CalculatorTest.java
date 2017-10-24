package com.example.andri.kalkulators;

import org.junit.Test;
import static junit.framework.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CalculatorTest {
    Calculator calc;
    String result;

    @Test
    public void additionTest() throws CalculatorExeption{
        calc = new CalculatorEngine();
        calc.putOperand("10");
        calc.putAction(CalculatorAction.ADDITION);
        calc.putOperand("10");
        result = calc.getResult();
        assertEquals("20.0", result);
    }

    @Test
    public void subtractionTest() throws CalculatorExeption{
        calc = new CalculatorEngine();
        calc.putOperand("10");
        calc.putAction(CalculatorAction.SUBTRACTION);
        calc.putOperand("10");
        result = calc.getResult();
        assertEquals("0.0", result);
    }

    @Test
    public void multiplicationTest() throws CalculatorExeption{
        calc = new CalculatorEngine();
        calc.putOperand("10");
        calc.putAction(CalculatorAction.MULTIPLICATION);
        calc.putOperand("10");
        result = calc.getResult();
        assertEquals("100.0", result);
    }

    @Test
    public void divisionTest() throws CalculatorExeption{
        calc = new CalculatorEngine();
        calc.putOperand("10");
        calc.putAction(CalculatorAction.DIVISION);
        calc.putOperand("10");
        result = calc.getResult();
        assertEquals("1.0", result);
    }

    @Test
    public void getResultTest() throws CalculatorExeption{
        calc = new CalculatorEngine();
        calc.putAction(CalculatorAction.ADDITION);
        calc.putOperand("5");
        result = calc.getResult();
        assertEquals("5.0", result);
    }

    @Test
    public void putOperandTest()throws CalculatorExeption{
        calc = new CalculatorEngine();
        calc.putOperand("10");
        calc.putAction(CalculatorAction.MULTIPLICATION);
        calc.putOperand("10");
        result = calc.getResult();
        assertEquals("100.0", result);
    }

    @Test
    public void clearTest()throws CalculatorExeption{
        calc = new CalculatorEngine();
        calc.putOperand("5");
        calc.putAction(CalculatorAction.DIVISION);
        result = null;
        calc.clear();
        assertEquals(calc.getCalculatorAction(), result);
        assertEquals(calc.getOperand(), result);
    }

    @Test
    public void multipleOperationsTest ()throws CalculatorExeption{
        calc = new CalculatorEngine();
        calc.putOperand("10");
        calc.putAction(CalculatorAction.DIVISION);
        calc.putOperand("10");
        calc.putAction(CalculatorAction.ADDITION);
        calc.putOperand("6");

        int resultNumeric = (10/10)+6;
        result = String.valueOf((double)resultNumeric);
        assertEquals(calc.getResult(), result);
    }

    @Test(expected = CalculatorExeption.class)
    public void calculatorExeptionTest() throws CalculatorExeption{
        calc = new CalculatorEngine();
        calc.putOperand("10");
        calc.putAction(CalculatorAction.DIVISION);
        calc.putOperand("0");
    }

}
