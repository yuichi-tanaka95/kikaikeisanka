package Display;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import CalcTester.CalcTester;

public class Display extends JFrame implements ActionListener {

	private JTextField resultDisplayField1;  // 数式と結果の表示フィールド
	private JTextField formulaDisplayField1; //打ち込んだ式を表示し続ける
	
		/*↓追加してます 田中↓*/
	private int inputPosition=0; 	//入力位置を保持
		/*↑				 ↑*/
	
	private StringBuilder inputBuilder1;  // 入力値を保持する
	private CalcTester tester; // CalcTesterへの参照

	public Display(CalcTester tester) {
		this.tester = tester; // CalcTesterを保存
		inputBuilder1 = new StringBuilder();

		// フレーム設定
		setTitle("電卓");
		setSize(400, 345); //レイアウトに合わせてサイズ変更してます 田中
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		
		
				/*↓ここから変更してます 田中↓*/
		
		//途中式と結果を表示する2段レイアウト設定
		JPanel displayPanel=new JPanel();
		displayPanel.setLayout(new BoxLayout(displayPanel,BoxLayout.Y_AXIS));
		
		// 表示フィールド formulaDisplayField用のテキストエリアを追加
		formulaDisplayField1=new JTextField();
		formulaDisplayField1.setEditable(false);
		formulaDisplayField1.setFont(new Font("Arial",Font.PLAIN,24));
		formulaDisplayField1.setPreferredSize(new Dimension(380,30)); //ここでテキストエリアのサイズ変更
		displayPanel.add(formulaDisplayField1);
		
		resultDisplayField1 = new JTextField();
		resultDisplayField1.setEditable(false);
		resultDisplayField1.setFont(new Font("Arial", Font.PLAIN, 24));
		resultDisplayField1.setPreferredSize(new Dimension(380,50)); //ここでテキストエリアのサイズ変更
		displayPanel.add(resultDisplayField1);
		
		add(displayPanel,BorderLayout.NORTH);
		
		
		// ボタンパネル GridLayoutに6行目を追加
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(6, 4));

		// ボタンの追加 ボタンの配置変更
		String[] buttons = {
				"(", ")", "←", "→",   // 1行目
				"%", "C", "AC", "÷",   // 2行目
				"7", "8", "9", "×",   // 3行目
				"4", "5", "6", "-",   // 4行目
				"1", "2", "3", "+",	  // 5行目
				"0", "DEL", ".", "=",  // 6行目
		};

		for (String text : buttons) {
			JButton button = new JButton(text);
			button.setFont(new Font("Arial", Font.PLAIN, 24));
			button.addActionListener(this);
			buttonPanel.add(button);
		}

		add(buttonPanel, BorderLayout.SOUTH);
	}

	// ボタンが押された時の処理
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		// "=" が押された場合、計算を実行   =が押された時のエラー用の例外を追加 
		if(command.equals("=")) {
			try {
				formulaDisplayField1.setText(inputBuilder1.toString());
				
				String result = tester.handleInput(inputBuilder1.toString().replaceAll("×", "*").replaceAll("÷","/"));
				
				resultDisplayField1.setText(result);
			}catch(Exception ex) {
				resultDisplayField1.setText("Error");
			}
		}
		
		// オールクリアボタンが押された場合 C→ACに変更
		else if (command.equals("AC")) {
			inputBuilder1.setLength(0);
			inputPosition=0;
			formulaDisplayField1.setText(""); //ACで途中式も消す？？
			updateDisplay();
		}
		
		// 1文字クリアボタンが押された場合 数字、演算子問わず1文字ずつ消去するコマンドを追加
		else if(command.equals("DEL")) {
			if(inputPosition>0) {
			inputBuilder1.deleteCharAt(inputPosition-1);
			inputPosition--;
		}
			updateDisplay();
		}
		
		//クリアボタンが押された場合 最後の数式以降を消去
		else if(command.equals("C")) {
			clearLastExpression();
			resultDisplayField1.setText(inputBuilder1.toString());
		}
		
		//"←"が押された場合 入力位置を左に移動するコマンドを追加
		else if(command.equals("←")) {
			movePositionLeft();
		}
		
		//"→"が押された場合 入力位置を右に移動するコマンドを追加
		else if(command.equals("→")) {
			movePositionRight();
		}
		
		// 入力ボタンが押された場合
		else {
			insertPosition(command);
		}
	}
	
	//最後の数式を消去するメソッド
	private void clearLastExpression() {
		int lastOperatorIndex = Math.max(
				Math.max(inputBuilder1.lastIndexOf("+"), inputBuilder1.lastIndexOf("-")),
				Math.max(inputBuilder1.lastIndexOf("×"), inputBuilder1.lastIndexOf("÷"))
				);

		if (lastOperatorIndex >= 0) {
			inputBuilder1.setLength(lastOperatorIndex + 0);  // 演算子以降を削除
		} else {
			inputBuilder1.setLength(0);  // 演算子がない場合は全てを削除
			/*村田　終わり*/
		}
	}
	
	
	//入力位置を左に移動
	public void movePositionLeft() {
		if(inputPosition>0) {
			inputPosition--;
		}
		updateDisplay();
	}
	
	//入力位置を右に移動
	public void movePositionRight() {
		if(inputPosition<inputBuilder1.length()) {
			inputPosition++;
		}
		updateDisplay();
	}
	
	//入力位置に数字、演算子を入力
	public void insertPosition(String c) {
		inputBuilder1.insert(inputPosition, c);
		inputPosition++;
		updateDisplay();
	}

	//表示を更新
	public void updateDisplay() {
		StringBuilder upd=new StringBuilder(inputBuilder1);
		upd.insert(inputPosition,"|"); //入力位置に入れる
		resultDisplayField1.setText(upd.toString());
	}
	
		/*↑ここまで変更してます 田中↑*/
	
	
	// メインウィンドウの起動メソッド
	public void showDisplay() {
		setVisible(true);
	}
}
