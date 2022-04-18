package edu.metrostate.ics340.p1.aa5013;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class Huffman {

	Node<Character> decodingTree;

	Map<Character, String> encodingMap = new HashMap<>();
	String decodedText = "";
	String inputText = "";

	/*
	 * constructor is private, we use factory design patteren to call it. created
	 * file and while there is next line in file, read next line and concat it to
	 * String input. Calculate the frequency of each character and store it in a map
	 * 
	 */
	private Huffman(String filePath) throws FileNotFoundException, IOException {

		File file = new File(filePath);

		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;

		while ((st = br.readLine()) != null) {

			inputText = inputText.concat(st) + "\n";
		}

		Map<Character, Integer> frequencyMap = new HashMap<>();

		for (char c : inputText.toCharArray()) {
			if (frequencyMap.keySet().contains(c)) {

				frequencyMap.put(c, frequencyMap.get(c) + 1);
			} else {

				frequencyMap.put(c, 1);
			}
		}

		/*
		 * priority queue for saving nodes of Huffman tree item with the highest
		 * priority has the lowest frequency
		 * 
		 */

		PriorityQueue<Node> queue = new PriorityQueue<>();

		queue = new PriorityQueue<>(Comparator.comparingInt(l -> l.count));

		for (Entry<Character, Integer> entry : frequencyMap.entrySet()) {

			queue.add(new Node(entry.getKey(), entry.getValue()));
		}

		while (queue.size() != 1) {
			// returns and removes the element at the front end of the queue
			Node left = queue.poll();
			Node right = queue.poll();
			// calculate sum of their counters
			int sum = left.count + right.count;
			// return merge node in queue
			queue.add(new Node(null, sum, left, right));
		}

		decodingTree = queue.peek();
	}

	public static Huffman build(String filePath) throws IOException {
		return new Huffman(filePath);
	}

	public Map<Character, String> getEncodingMap() {
		return encodingMap;
	}

	/*
	 * method for encoding, input is text from file, and output is encoded text
	 * 
	 */
	public String encode(String text) {
		String encodedText;

		String str = "";

		encodeRecursion(decodingTree, str);

		StringBuilder sb = new StringBuilder();

		for (char c : text.toCharArray()) {

			sb.append(encodingMap.get(c));
		}
		encodedText = sb.toString();

		return encodedText;
	}

	/*
	 * if there is no Huffman tree we return. if node is leaf, does not have any
	 * child.
	 */
	public void encodeRecursion(Node<Character> root, String str) {
		if (root == null) {
			return;
		}

		if (Node.isLeaf(root)) {

			encodingMap.put(new Character((char) root.getValue()), str.length() > 0 ? str : "1");
		}

		encodeRecursion(root.getLeftChild(), str + "0");

		encodeRecursion(root.getRightChild(), str + "1");

	}

	/*
	 * decoding method gets encoded text and returns decoded text.
	 * 
	 */
	public String decode(String code) {

		if (Node.isLeaf(decodingTree)) {

			while (decodingTree.count-- > 0) {
				decodedText += decodingTree.getValue();
			}
		} else {

			int index = -1;
			while (index < code.length() - 1) {
				index = decodeRecursion(decodingTree, index, code);
			}
		}

		return decodedText;

	}

	/*
	 * if tree is empty, we return. if root is leaf, one node in the tree.
	 * 
	 */
	public int decodeRecursion(Node root, int index, String encodedText) {

		if (root == null) {
			return index;
		}

		if (Node.isLeaf(root)) {

			decodedText += root.getValue();
			return index;
		}

		index++;

		if (encodedText.charAt(index) == '0')
			root = root.getLeftChild();
		else
			root = root.getRightChild();

		index = decodeRecursion(root, index, encodedText);
		return index;
	}

	public Node<Character> getDecodingTree() {
		return decodingTree;
	}

}
