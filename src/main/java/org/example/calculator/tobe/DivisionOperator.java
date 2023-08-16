package org.example.calculator.tobe;

import org.example.calculator.domain.PositiveNumber;

public class DivisionOperator implements ArithmeticOperator {
    @Override
    public boolean supports(String operator) {
        return "/".equals(operator);
    }

    @Override
    public int calculate(PositiveNumber operand1, PositiveNumber operand2) {

        // PositiveNumber에서 이미 0에 대한 예외처리를 진행함..(무조건 양수임을 보장)
//        if (operand2.toInt() == 0) {
//            throw new IllegalArgumentException("0으로 나눌 수 없습니다.");
//        }

        return operand1.toInt() / operand2.toInt();
    }
}
