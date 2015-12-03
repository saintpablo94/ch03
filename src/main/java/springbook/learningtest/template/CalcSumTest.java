package springbook.learningtest.template;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.io.IOException;

import org.junit.Test;

public class CalcSumTest {
	
	@Test public void sumOfNumber() throws NumberFormatException, IOException {
		Calculator calculatror = new Calculator();
		int sum = calculatror.calcSum(
				getClass().getResource("numbers.txt").getPath()
				);
		assertThat(sum, is(10)); 
	}
}
