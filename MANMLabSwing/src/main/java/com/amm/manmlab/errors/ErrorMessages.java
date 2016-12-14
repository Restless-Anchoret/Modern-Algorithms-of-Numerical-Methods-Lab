package com.amm.manmlab.errors;

import org.slf4j.helpers.MessageFormatter;

public enum ErrorMessages {

    ROW_SIZE_DOES_NOT_EQUAL_MATRIX_SIZE("Row size({}) doesn't equal matrix size({})! Row number : {}."),

    CAN_NOT_READ_ADJACENCY_MATRIX_FROM_FILE("Can't read Adjacency Matrix from file. Filename : {}"),

    INCORRECT_SYMBOL_IN_LINE("Incorrect symbol {} in line : {}"),

    INPUT_FILE_DOES_NOT_CONTAIN_ENOUGH_NUMBER_OF_LINES("Input file does no contain enough number of lines. Expected number : {}, actual number : {}"),

    MATRIX_IS_NOT_SYMMETRIC_ELEMENT_DOES_NOT_EQUAL_ELEMENT("Adjacency matrix is not symmetric. Element [{},{}] does not equal element [{},{}]"),

    START_VERTEX_NUMBER_IS_GREATER_THAN_NUMBER_OF_VERTEX("Start vertex number({}) is greater than number of vertex({})"),

    POINTS_SIZE_DOESNT_EQUAL_ADJECENCY_MATRIX_SIZE("Points size doesn't equal adjacencyMatrix size!"),

    GRID_MUST_BE_POLYGON("Grid must be a polygon!"),
    
    CONDITIONS_ARE_NOT_SELECTED("Conditions are not selected!");
    
    private final String message;

    private ErrorMessages(String message) {
        this.message = message;
    }

    public String toString(Object... params) {
        if (params == null) return message;
        return MessageFormatter.arrayFormat(message, params).getMessage();
    }

    public String getMessage() {
        return message;
    }

}
