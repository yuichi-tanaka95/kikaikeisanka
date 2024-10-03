package CalcTester;

import java.util.List;

import Calc.Calc;
import Display.Display;
import Input.Input;

public class CalcTester {
	
	//10:49元木更新テスト
	//村田テスト

	private Input input;
	private Calc calc;
	private Display display;  // 電卓のGUIを表示する

	public CalcTester() {
		System.out.println("test"); //松﨑コミットテストです
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
		// トークンをCalcに渡して計算
		double result = calc.evaluate(tokens);
		// 結果を文字列として返す
		return String.valueOf(result);
	}

	public static void main(String[] args) {
		CalcTester tester = new CalcTester();
		tester.start(); // 電卓画面を表示して計算を開始
	}
}