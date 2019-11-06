package com.enrico.chemistry.formulaparser.exceptions;

public class IllegalFormulaException extends IllegalArgumentException {
    public IllegalFormulaException(String formula) {
        super("Error: formula \"" + formula +"\" contains invalid characters or invalid atoms (such as noble gasses or unstable atoms). Please check the formula and try again.");
    }
}
