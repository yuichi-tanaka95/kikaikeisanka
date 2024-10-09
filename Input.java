package Input;

import java.util.ArrayList;
import java.util.List;

public class Input {
	// 数式をトークンに分割するメソッド
	public List<String> tokenize(String expression) {
		List<String> tokens = new ArrayList<>();
		StringBuilder number = new StringBuilder();

		for (int i = 0; i < expression.length(); i++) {
			char c = expression.charAt(i);

			if (c == ' ') {
				continue;
			}

			if (Character.isDigit(c) || c == '.') {
				number.append(c);
			} else {
				if (number.length() > 0) {
					tokens.add(number.toString());
					number = new StringBuilder();
				}
				tokens.add(Character.toString(c));
			}
		}

		if (number.length() > 0) {
			tokens.add(number.toString());
		}

		return tokens;
	}
}
