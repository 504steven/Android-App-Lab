package com.wgc.cmpe277;

public class MyCalculator {
    Double val1 = null;
    Double val2 = null;
    char operation = '.';

    public Double input(double v, char o) {
        if(o != '=' && o != '-'  && o != '+' && o != '*' && o != '/' ) {
            return null;
        }

        // 1+, 2-  5
        if(o != '=') {
            if(val1 == null) {
                operation = o;
                val1 = v;
                return  null;
            }else {
                Double res = cal(operation, val1, v);
                operation = o;
                val1 = res;
                val2 = v;
                return res;
            }
        }else {
            if(val1 != null) {
                Double res = cal(operation, val1, v);
                val2 = v;
                val1 = null;
                return res;
            }else if(val2 != null){
                Double res = cal(operation, v, val2);
                return res;
            }
            return null;
        }
    }

    public void clear() {
        val1 = null;
        val2 = null;
        operation = '.';
    }

    private Double cal(char o, double v1, double v2) {
        switch (o) {
            case '+':
                return  v1 + v2;
            case '-':
                return v1 - v2;
            case '*':
                return v1 * v2;
            case '/':
                return v1 / v2;
            default:
        }
        return null;
    }
}
