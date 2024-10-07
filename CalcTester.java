package CalcTester;

import java.util.List;

import Calc.Calc;
import Display.Display;
import Input.Input;

public class CalcTester {

	private Input input;
	private Calc calc;
	private Display display;  // 電卓のGUIを表示する

	public CalcTester() {
		input = new Input(); // 入力のトークン化担当
		calc = new Calc();   // 計算担当
		display = new Display(this); // 電卓画面の表示、thisでCalcTesterを渡す
	}

	// 電卓画面を表示
	public void start() {
		display.showDisplay();
	}

	// 入力された数式を処理して計算結果を返す
	public String handleInput(String expression) {
		// 数式をトークンに分割
		List<String> tokens = input.tokenize(expression);
		
		//トークンが空の場合、そのまま返す
		if (tokens.isEmpty()) {
			return "";
		}
		
		try {
		// トークンをCalcに渡して計算
		double result = calc.evaluate(tokens);
		
		if(result==(int)result) {//計算結果がキャストされたint型の結果と同じなら
			return String.valueOf((int)result);//int型にキャストされた結果をString型文字列で返す
		}else {  
			//小数点が必要な場合 結果を文字列として返す
			return String.valueOf(result);
		}
		}catch(Exception e) {
			return "Error";
		}
	}

	public static void main(String[] args) {
		CalcTester tester = new CalcTester();
		tester.start(); // 電卓画面を表示して計算を開始
	}
}
