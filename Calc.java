package Calc;


import java.util.List;
import java.util.Stack;

public class Calc {
	public double evaluate(List<String> tokens) {//CalcTesterから受ける
		Stack<Double> values = new Stack<>();    // 数値のスタック
		Stack<Character> operators = new Stack<>(); // 演算子のスタック
		String[] formula = new String[tokens.size()];

		//数値・四則演算・（）を区別する
		for (int i=0; i<tokens.size(); i++) {
			formula[i] = tokens.get(i);
			
			//数字か判定
			if (isNumeric(tokens.get(i))) {//isNumeric関数（全体が数値と認識されるとtrueを返す）
				
				//式の始まりが負の数の時は、translateでマイナス変換した負の数をvaluesへ格納する
				if (i==1 && formula[0].equals("-")) {
					values.push(translatePlusMinus(Double.parseDouble(tokens.get(1))));
					
				//括弧の始まりが負の数の時は、translateでマイナス変換した負の数をvaluesへ格納する	
				} else if (i>=2 && formula[i-2].equals("(") && formula[i-1].equals("-")) {
					values.push(translatePlusMinus(Double.parseDouble(tokens.get(i))));
				
				//正の数はvaluesへ格納する			
				} else {
					values.push(Double.parseDouble(tokens.get(i)));	
				}

				
				
			} else if (tokens.get(i).equals("(")) {//左カッコが入力された時の処理
				operators.push('('); // 左括弧はスタックにそのまま入れる
				
			} else if (tokens.get(i).equals(")")) {//右カッコが入力された時の処理

				/*演算子スタックが空でなく、トップの演算子が左カッコ（でない場合
            	スタックから演算子を取り出し、その演算子に対応する2つの数値で演算を行い、
            	結果を数値スタック (values) にプッシュ*/
				while (!operators.isEmpty() && operators.peek() != '(') {
					values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
				}
				operators.pop(); // 左括弧をポップして取り除く
				
				
			} else if (isOperator(tokens.get(i))) {//四則演算子を入力された時の処理
				
				
				//式が負の数で始まる場合は、マイナス演算子をoperatorsに格納しない
				if(i==0 && tokens.get(i).equals("-")) {
					continue;
					
				//括弧が負の数で始まる場合は、マイナス演算子をoperatorsに格納しない
				} else if (tokens.get(i).equals("-") && formula[i-1].equals("(")) {
					continue;
				}
				
				char op = tokens.get(i).charAt(0);
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
		/*戻り値に%を追加*/
		return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")|| token.equals("%");
	}
	
	// 演算子の優先順位を判定
	private boolean precedence(char op1, char op2) {
		/*優先順位に%を追加*/
		if (op2 == '(' || op2 == ')') return false;
		if ((op1 == '*' || op1 == '/'|| op1 == '%') && (op2 == '+' || op2 == '-')) return false;
		return true;
	}
	
	/*計算式に%を追加*/
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
	
	//プラスマイナスの変換を実行する
	private double translatePlusMinus(double a) {
		return -a;
	}

}

