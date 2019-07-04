package cn.letsky.wechat.model;

import org.springframework.stereotype.Component;
import java.util.TreeMap;

@Component
public class Trie {
	
	public class Node{
		public boolean isWord;
		public TreeMap<Character, Node> next;
		
		public Node(boolean isWord) {
			this.isWord = isWord;
			next = new TreeMap<>();
		}
		
		public Node() {
			this(false);
		}
	}
	
	private Node root;
	private int size;

	public Trie() {
		root = new Node();
		size = 0;
	}

	public Node getRoot() {
		return this.root;
	}

	public int getSize() {
		return this.size;
	}
	
	/**
	 * 向Trie中添加一个新的单词word
	 * @param word
	 */
	public void add(String word) {
		Node cur = root;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (cur.next.get(c) == null) {
				cur.next.put(c, new Node());
			}
			cur = cur.next.get(c);
		}
		if (!cur.isWord) {
			cur.isWord = true;
			size++;
		}
	}
	
	public boolean contains(String word) {
		Node cur = root;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (cur.next.get(c) == null) {
				return false;
			}
			cur = cur.next.get(c);
		}
		return cur.isWord;
	}

	public boolean contains(char w){
		Node cur = root;
		if (cur.next.get(w) == null) {
			return false;
		}
		cur = cur.next.get(w);
		return cur.isWord;
	}

	public String filter(String text){
		String replacement = "*";
		StringBuilder result = new StringBuilder();
		Node node = root;
		//回滚数
		int begin = 0;
		//当前位置
		int position = 0;
		while (position < text.length()) {
			char c = text.charAt(position);
			node = node.next.get(c);
			if (node == null) {
				//开头不存在敏感词
				result.append(text.charAt(begin));
				position = begin + 1;
				begin = position;
				node = root;
			} else if (node.isWord) {
				result.append(replacement);
				position = position + 1;
				begin = position;
				node = root;
			} else {
				++position;
			}
		}
		result.append(text.substring(begin));
		return result.toString();
	}
}
