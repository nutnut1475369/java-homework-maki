package com.wongnai.interview.wamup;

import java.util.InputMismatchException;

public class DigitalRoot {
	public Object check(long number) {
		if(number < 0) throw new InputMismatchException("Invalid input");
		long sum = 0;
		while(number > 0){
			long digit = number%10;
			number = number/10;
			sum += digit;
		}
		if(sum > 9) {
			return check(sum);
		}else{
			return sum;
		}
	}
}