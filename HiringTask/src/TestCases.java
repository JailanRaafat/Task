import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class TestCases {

	@Test
	
		public void test() throws IOException {
			Task test= new Task();
			String output= test.stringEncryption("Hello World");
			assertEquals("Khoor Zruog", output);
			output= test.stringEncryption("abc");
			assertEquals("def", output);

			
			output= test.stringEncryption("this is a test case for my function");
			assertEquals("wklv lv d whvw fdvh iru pb ixqfwlrq", output);

			output= test.stringEncryption("abcd efgh hij lmnopqrstuv wxyz");
			assertEquals("defg hijk klm opqrstuvwxy zabc", output);

			
			String out= Task.matrixEncryption("hello world hino");
			assertEquals("10001110110100 1100000011110 1101100111011 1100100111010 1100110101010 1100011001110 1011010001111 1001001111000 1010110001000 1111101100100 1011111100111 1011010000111 1101011010101 10011111100011 1011101100111 1001101110000 ", out);

			
		}
	}


