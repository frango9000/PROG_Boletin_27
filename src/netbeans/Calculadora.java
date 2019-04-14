/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.netbeans;

import java.util.Stack;

/**
 *
 * @author NarF
 */
public class Calculadora {

    public static void main(String[] args) {
        // Check number of arguments passed
        if (args.length != 1) {
            System.out.println("Bad Format");
            System.exit(1);
        }
        try {
            System.out.println(evaluateExpression(args[0]));
        } catch (Exception ex) {
            System.out.println("Wrong expression: " + args[0]);
        }
    }

    /**
     * Evaluate an expression
     */
    public static double evaluateExpression(String expression) {
        // creamos una pila de operandos
        Stack<Double> operandStack = new Stack<>();
        // crear una pila de operadores
        Stack<Character> operatorStack = new Stack<>();
        // insertamos espacios entre los operadores
        expression = insertarBlanks(expression);
        // extraemos los terminos
        String[] tokens = expression.split(" ");
        // analizamos los tokens
        for (String token : tokens) {
            if (token.length() == 0) // espacio blanco
            {
                continue; // volvemos para extraer el token siguiente
            } else if (token.charAt(0) == '+' || token.charAt(0) == '−') {
        // procesamos todos los  +, −, *, / encima de la pila
                while (!operatorStack.isEmpty()
                        && (operatorStack.peek() == '+'
                        || operatorStack.peek() == '−'
                        || operatorStack.peek() == '*'
                        || operatorStack.peek() == '/'
                        || operatorStack.peek() == '%')) {
                    procesarOperador(operandStack, operatorStack);
                }
        // empujamos el + y el - en la pila
                operatorStack.push(token.charAt(0));
            } else if (token.charAt(0) == '*' || token.charAt(0) == '/'|| token.charAt(0) == '%') {
        // procesamos todos los / y * en la pila
                while (!operatorStack.isEmpty() && (operatorStack.peek() == '*' || operatorStack.peek() == '/' || operatorStack.peek() == '%')) {
                    procesarOperador(operandStack, operatorStack);
                }
        // empujamos el / y el * a la pila
                operatorStack.push(token.charAt(0));
            } else if (token.trim().charAt(0) == '(') {
                operatorStack.push('('); // Push '(' to stack
            } else if (token.trim().charAt(0) == ')') {
        // procesamos operadores hasta encontrar  '('
                while (operatorStack.peek() != '(') {
                    procesarOperador(operandStack, operatorStack);
                }
                operatorStack.pop(); // sacamos el '(' de la pila
            } else { // operando escaneado
        // empujamos un operando a la fila
                operandStack.push(new Double(token));
            }
        }
        // procesar el resto de operadores en la pila
        while (!operatorStack.isEmpty()) {
            procesarOperador(operandStack, operatorStack);
        }
        return operandStack.pop();
    }

    public static void procesarOperador(Stack<Double> operandStack, Stack<Character> operatorStack) {
        char op = operatorStack.pop();
        double op1 = operandStack.pop();
        double op2 = operandStack.pop();
        if (op == '+') {
            operandStack.push(op2 + op1);
        } else if (op == '-') {
            operandStack.push(op2 - op1);
        } else if (op == '*') {
            operandStack.push(op2 * op1);
        } else if (op == '/') {
            operandStack.push(op2 / op1);
        } else if (op == '%') {
            operandStack.push(op2 % op1);
        }
    }

    public static String insertarBlanks(String s) {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(' || s.charAt(i) == ')'|| s.charAt(i) == '+' || s.charAt(i) == '−'|| s.charAt(i) == '*' || s.charAt(i) == '/' || s.charAt(i) == '%') {
                result += " " + s.charAt(i) + " ";
            } else {
                result += s.charAt(i);
            }
        }
        return result;
    }

}
