package edu.metrostate.ics340.p1.aa5013;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {

		Huffman h = Huffman.build("C:\\Users\\Abdif\\eclipse-workspace\\ P1_AA5013_Huffman\\huffman8.txt");
		System.out.println("ENCODED TEXT");
		String encodedText = h.encode(h.inputText);
		System.out.println(encodedText);
		System.out.println("----------------------------");
		System.out.println("DECODED TEXT");
		String decodedText = h.decode(encodedText);
		System.out.println(decodedText);

	}
}
