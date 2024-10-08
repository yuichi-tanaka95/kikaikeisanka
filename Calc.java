package dentakuVol20.Calc;

import java.util.List;
import java.util.Stack;

public class Calc {

	public double evaluate(List<String> tokens) {//CalcTesterから受ける
		Stack<Double> values = new Stack<>();    // 数値のスタック
		Stack<Character> operators = new Stack<>(); // 演算子のスタック

		//数値・四則演算・（）を区別する
		for (String token : tokens) {
			if (isNumeric(token)) {//isNumeric関数（全体が数値と認識されるとtrueを返す）
				values.push(Double.parseDouble(token)); // 数値はそのままスタックへ
			} 
			else if (token.equals("(")) {//左カッコが入力された時の処理
				operators.push('('); // 左括弧はスタックにそのまま入れる
			} 
			else if (token.equals(")")) {//右カッコが入力された時の処理
				/*演算子スタックが空でなく、トップの演算子が左カッコ（でない場合
            	スタックから演算子を取り出し、その演算子に対応する2つの数値で演算を行い、
            	結果を数値スタック (values) にプッシュ*/
				while (!operators.isEmpty() && operators.peek() != '(') {
					values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
				}
				operators.pop(); // 左括弧をポップして取り除く
			} 
			else if (isOperator(token)) {//四則演算子を入力された時の処理
				char op = token.charAt(0);
				/*現在の演算子opが演算子スタックoperatorにある他の演算子よりも
                優先順位が高いか判断。*/
				while (!operators.isEmpty() && precedence(op, operators.peek())) {
					values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
				}
				operators.push(op); // 演算子をスタックに追加、これにより次に出てくる演算子と
			}                       //比べて処理されるまで待機
		}

		// 残りの演算子をすべて計算
		while (!operators.isEmpty()) {
			values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
		}

		return values.pop(); // 結果を返す
	}
	// 文字列が数値かどうかを判定
	private boolean isNumeric(String token) {
		try {
			Double.parseDouble(token);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	// 演算子かどうかを判定
	private boolean isOperator(String token) {
		/*村田　始め　戻り値に%を追加*/
		return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")|| token.equals("%");
	}/*村田　終わり*/
	// 演算子の優先順位を判定
	private boolean precedence(char op1, char op2) {
		/*村田　始め 優先順位に%を追加*/
		if (op2 == '(' || op2 == ')') return false;
		if ((op1 == '*' || op1 == '/'|| op1 == '%') && (op2 == '+' || op2 == '-')) return false;
		return true;/*村田　終わり*/
	}
	/*村田　始め 計算式に%を追加*/
	private double applyOperator(char operator, double b, double a) {
		switch (operator) {
		case '+': return a + b;
		case '-': return a - b;
		case '*': return a * b;
		case '/': return a / b;
		case '%': return a * (b / 100);  // %演算はbをaの%として計算
		default: return 0;
		}
	}
}