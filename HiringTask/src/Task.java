import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

public class Task {
	public static void main(String args[]) throws ClientProtocolException, IOException, JSONException {
		Scanner scanner = new Scanner(System.in);
		String s = "";
		System.out.println("Please enter the required method (Encryption / Decryption): ");
		String method = scanner.nextLine();

		while (!(method.equalsIgnoreCase("Encryption") || method.equalsIgnoreCase("Decryption"))) {
			System.out.println("Please enter the required method (Encryption / Decryption): ");
			method = scanner.nextLine();
		}

		System.out.println("Please enter the algorithm (Shift / Matrix / Reverse): ");
		String func = scanner.nextLine();
		while (!(func.equalsIgnoreCase("Shift") || func.equalsIgnoreCase("Matrix")
				|| func.equalsIgnoreCase("Reverse"))) {
			System.out.println("Please Enter the Encryption Algorithm (Shift / Matrix / Reverse):  ");
			func = scanner.nextLine();
		}

		if (method.equalsIgnoreCase("encryption")) {
			if (func.equals("matrix")) {
				System.out.print("Please enter a 16 letter string to be Encrypted: ");
				String mssg = scanner.nextLine();
				while (mssg.length() != 16) {
					System.out.print("Please enter a 16 letter string to be Encrypted: ");
					mssg = scanner.nextLine();
				}
				s = matrixEncryption(mssg);
				System.out.println("Your ciphered text is: " + s);

			}

			if (func.equalsIgnoreCase("Shift")) {
				System.out.print("Please enter a string to be Encrypted: ");
				String mssg = scanner.nextLine();
				s = stringEncryption(mssg);
				System.out.println("Your ciphered text is: " + s);

			}

			if (func.equalsIgnoreCase("Reverse")) {
				System.out.print("Please enter a string to be Encrypted: ");
				String mssg = scanner.nextLine();
				s = reverseEnc(mssg);
				System.out.println("Your ciphered text is: " + s);

			}

		}

		if (method.equalsIgnoreCase("decryption")) {

			if (func.equals("matrix")) {
				System.out.print("Please enter a binary string to be decrypted: ");
				String mssg = scanner.nextLine();
				String a[] = mssg.split(" ");
				String msg = mssg.replaceAll("\\s+", "");
				while (a.length != 16 || !(msg.matches("(0|1)*"))) {
					System.out.print("Please enter a BINARY string of 16 characters to be decrypted: ");
					mssg = scanner.nextLine();
					a = mssg.split(" ");
					msg = mssg.replaceAll("\\s+", "");
				}
				s = decMatrixEnc(mssg);
				System.out.println("Your message is: " + s);
			}

			if (func.equalsIgnoreCase("Shift")) {
				System.out.print("Please enter a string to be decrypted: ");
				String mssg = scanner.nextLine();
				s = decStringEnc(mssg);
				System.out.println("Your message is: " + s);

			}

			if (func.equalsIgnoreCase("Reverse")) {
				System.out.print("Please enter a string to be Decrypted: ");
				String mssg = scanner.nextLine();
				s = reverseDec(mssg);
				System.out.println("Your ciphered text is: " + s);

			}

		}
	}

	public static String stringEncryption(String mssg) {
		int shift = 3;
		String cipher = "";

		for (int i = 0; i < mssg.length(); i++) {
			if (mssg.charAt(i) == ' ') {
				cipher += (char) (mssg.charAt(i));
			} else {
				if (mssg.charAt(i) >= 'x') {
					char c = (char) (mssg.charAt(i) - (26 - shift));
					cipher += c;
				} else {
					char c = (char) (mssg.charAt(i) + shift);
					cipher += c;
				}

			}
		}
		return cipher;

	}

	public static String matrixEncryption(String mssg) throws IOException {
		String data = new String(Files.readAllBytes(Paths.get("matrix.txt")));
		String[] arr = data.split(" ");
		// System.out.println(arr.length);
		double[] temp = new double[16];
		double[][] matrix = new double[16][16];
		double[] ascii = new double[16];
		double[] result = new double[16];
		String binary = "";

		int x = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = Double.valueOf(arr[x]);
				x++;
			}
		}
		// System.out.println(Arrays.deepToString(matrix));

		matrix = transpose(matrix);

		// System.out.println(Arrays.deepToString(matrix));
		int j = 0;
		for (int i = 0; i < mssg.length(); i++) {
			ascii[i] = (double) mssg.charAt(i);
		}
		// System.out.println("THIS IS MY ASCII " + Arrays.toString(ascii));
		for (int i = 0; i < matrix.length; i++) {
			result[i] = multiplyMatrix(ascii, matrix, i, j);

		}
		// System.out.println(Arrays.toString(result));
		for (int i = 0; i < result.length; i++) {
			int r = (int) result[i];
			binary += Integer.toBinaryString(r) + " ";

		}
		// System.out.println(binary);
		return binary;
	}

	public static double[][] transpose(double[][] matrix) {
		double[][] matrixtanspose = new double[matrix.length][matrix[0].length];
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[i].length; j++)
				matrixtanspose[i][j] = matrix[j][i];
		return matrixtanspose;
	}

	public static double multiplyMatrix(double[] ascii, double[][] matrix, int row, int col) {
		double result = 0;
		for (int i = 0; i < matrix.length; i++) {
			result += ascii[i] * matrix[row][i];
		}
		return result;
	}

	public static String decStringEnc(String cipher) {
		int shift = -3;
		String mssg = "";

		for (int i = 0; i < cipher.length(); i++) {
			if (cipher.charAt(i) == ' ') {
				mssg += (char) (cipher.charAt(i));
			} else {
				if (cipher.charAt(i) <= 'c') {
					char c = (char) (cipher.charAt(i) + (26 + shift));
					mssg += c;
				} else {
					char c = (char) (cipher.charAt(i) + shift);
					mssg += c;
				}

			}
		}
		// System.out.println(mssg);
		return mssg;

	}

	public static String decMatrixEnc(String binary) throws IOException {
		String[] st = binary.split(" ");
		double[] res = new double[16];
		for (int i = 0; i < st.length; i++)
			res[i] = Integer.parseInt(st[i], 2);
		// System.out.println(Arrays.toString(res));

		String data = new String(Files.readAllBytes(Paths.get("inverse.txt")));
		String[] arr = data.split(" ");
		// System.out.println(arr.length);
		double[][] matrix = new double[16][16];
		int[] ascii = new int[16];

		int x = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = Double.valueOf(arr[x]);
				x++;
			}
		}
		// System.out.println(Arrays.deepToString(matrix));
		matrix = transpose(matrix);
		int j = 0;
		for (int i = 0; i < matrix.length; i++) {
			ascii[i] = (int) Math.round(multiplyMatrix(res, matrix, i, j));
		}
		// System.out.println(Arrays.toString(ascii));
		String mssg = "";
		for (int i = 0; i < ascii.length; i++) {
			mssg += (char) ascii[i];
		}
		// System.out.println(mssg);
		return mssg;

	}

	public static String reverseEnc(String mssg) throws ClientProtocolException, IOException, JSONException {

//		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpClient httpclient = HttpClients.createDefault();

		HttpPost request = new HttpPost("http://backendtask.robustastudio.com/encode");
		StringEntity params = new StringEntity("{\"string\":\"" + mssg + "\"}");
		request.addHeader("content-type", "application/json");
		request.setEntity(params);
		HttpResponse response = httpclient.execute(request);
		HttpEntity entity = response.getEntity();

		String responseString = EntityUtils.toString(entity);
		;
		org.json.JSONObject temp1 = new org.json.JSONObject(responseString);
		String myresponse = (String) temp1.get("string");

		return myresponse;
	}

	public static String reverseDec(String mssg) throws ClientProtocolException, IOException, JSONException {

		HttpClient httpClient = HttpClientBuilder.create().build();

		HttpPost request = new HttpPost("http://backendtask.robustastudio.com/decode");
		StringEntity params = new StringEntity("{\"string\":\"" + mssg + "\"}");
		request.addHeader("content-type", "application/json");
		request.setEntity(params);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();

		String responseString = EntityUtils.toString(entity);
		org.json.JSONObject temp1 = new org.json.JSONObject(responseString);
		String myresponse = (String) temp1.get("string");

		return myresponse;
	}
}
