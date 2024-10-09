package CalcTester;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import Calc.Calc;
import Display.Display;
import Input.Input;

public class CalcTester {

	public static JTabbedPane originTabbedPane;
	private Display display1;  // 電卓のタブと文字盤を表示する
	private Display display2;  // 電卓のタブと文字盤を表示する
	private Display display3;  // 電卓のタブと文字盤を表示する
	private Display display4;
	
	public CalcTester() {
		//メインフレームを作成
		Display.mainFrame = new JFrame();
		// フレーム設定
		Display.mainFrame.setTitle("電卓");
		Display.mainFrame.setSize(400, 370); //レイアウトに合わせてサイズ変更してます
		Display.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Display.mainFrame.setLayout(new BorderLayout());
		
		//大元のタブを作成
		originTabbedPane = new JTabbedPane();
		
		//Tab1を作成＆追加
		display1 = new Display(this); //文字盤、表示画面ふくめ一枚のタブになってます
		addNewTab("タブ1",display1);
		
		//Tab2を作成＆追加
		display2 = new Display(this); //文字盤、表示画面ふくめ一枚のタブになってます
		addNewTab("タブ2",display2);
		
		//Tab3を追加＆追加
		display3 = new Display(this); //文字盤、表示画面ふくめ一枚のタブになってます
		addNewTab("タブ3",display3);
		
		//Tab4を追加＆追加
		display4 = new Display(this); //文字盤、表示画面ふくめ一枚のタブになってます
		addNewTab("タブ4",display4);
	}

	// 電卓画面を表示
	public void start() {
		Display.mainFrame.setVisible(true);
	}
	public void addNewTab(String tabName, Display display) {
		Display.mainFrame.add(originTabbedPane, BorderLayout.NORTH);
		//大元のタブにdisplay2を追加
		originTabbedPane.addTab(tabName,display.getTabPanel());
	}

	// 入力された数式を処理して計算結果を返す
	public String handleInput(String expression, boolean b) {
		// 数式をトークンに分割
		Input input = new Input();
		List<String> tokens = input.tokenize(expression);
		
		//トークンが空の場合、そのまま返す
		if (tokens.isEmpty()) {
			return "";
		}
		
		try {
		// トークンをCalcに渡して計算
		Calc calc = new Calc(); 
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
		
		/*ウィンドウを常に手前に表示する設定*/
		Display.mainFrame.setAlwaysOnTop(true);
		
		/*タブのフォーカスを無効化*/
		originTabbedPane.setFocusable(false);
	}
}
