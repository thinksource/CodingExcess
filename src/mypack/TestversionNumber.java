package mypack;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("A special test case")
class TestversionNumber {

	private final String[] exp1 = {"12-34-86","01-34-85","34-34-19","02-34-34","01-34-24","23-34-18","24-34-38"};
	private final String[] empty= {};
	private final String[] invalid_input= {"er-34-90"};
	private final String[] invalid_input2= {"2147483648-34-90"};
	private final String[] invalid_addition= {"12-34-90-90"};
	private final String[] invalid_mixture= {"er-34-90", "12-34-86","01-34-85"};
	
	void resultAssert(List<VersionNumber> val, String[] target) {
		assertEquals(val.size(), target.length);
		for(int i=0; i<target.length;i++) {
			assertEquals(val.get(i).toString(), target[i]);
		}
	}

	@Test
	@DisplayName("work with filter and sorted")
	void testWithfilter() {
		Predicate<VersionNumber> btf = n -> {return n.compareTo(new VersionNumber(8,0,50))>0;};
		List<VersionNumber> output=VersionNumber.sortAndConvert(Arrays.asList(exp1), Optional.of(btf));
		String[] result1 = {"34-34-19", "24-34-38", "23-34-18", "12-34-86"};
		resultAssert(output, result1);
	}
	
	@Test
	@DisplayName("work with null filer and just sorted")
	void testEmptyfilter() {
		List<VersionNumber> output=VersionNumber.sortAndConvert(Arrays.asList(exp1), null);
		String[] result2 = {"34-34-19", "24-34-38", "23-34-18", "12-34-86", "02-34-34", "01-34-85", "01-34-24"};
		resultAssert(output, result2);
	}
	
	@Test
	@DisplayName("work with null input")
	void testEmptyInput() {
		List<VersionNumber> output=VersionNumber.sortAndConvert(Arrays.asList(empty), null);
		resultAssert(output, empty);
	}
	
	@Test
	@DisplayName("work with null input but have some filter")
	void testEmptyInputFilter() {
		Predicate<VersionNumber> btf = n -> {return n.compareTo(new VersionNumber(8,0,50))>0;};
		List<VersionNumber> output=VersionNumber.sortAndConvert(Arrays.asList(empty), Optional.of(btf));
		resultAssert(output, empty);
	}
	
	@Test
	@DisplayName("work with invalid input")
	void testInvalidInput() {
		List<VersionNumber> output=VersionNumber.sortAndConvert(Arrays.asList(invalid_input), null);
		resultAssert(output, empty);
	}
	
	@Test
	@DisplayName("work with invalid input")
	void testInvalidInput2() {
		List<VersionNumber> output=VersionNumber.sortAndConvert(Arrays.asList(invalid_input2), null);
		resultAssert(output, empty);
	}
	
	@Test
	@DisplayName("work with invalid_additional input")
	void testAdditionalInput() {
		List<VersionNumber> output=VersionNumber.sortAndConvert(Arrays.asList(invalid_addition), null);
		resultAssert(output, empty);
	}
	
	@Test
	@DisplayName("work with invalid input and valid input mixture")
	void testInvalidMix() {
		List<VersionNumber> output=VersionNumber.sortAndConvert(Arrays.asList(invalid_mixture), null);
		String[] result3 = {"12-34-86","01-34-85"};
		resultAssert(output, result3);
	}

}
