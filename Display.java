package Display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import CalcTester.CalcTester;

public class Display extends JTabbedPane implements ActionListener {
	
	protected JTextField resultDisplayField;  // 数式と結果の表示フィールド
	protected JTextField formulaDisplayField; //打ち込んだ式を表示し続ける
	protected JPanel TabPanel;
	
	protected int inputPosition=0; 	//入力位置の保持
	protected StringBuilder inputBuilder1;  // 入力値を保持する
	private CalcTester tester; // CalcTesterへの参照
	private JPanel buttonPanel;
	private JButton leftParenthesisButton;//「 ( 」ボタン
	public static JFrame mainFrame;
	
	public Display(CalcTester tester) {
		
				
		this.tester = tester; // CalcTesterを保存
		inputBuilder1 = new StringBuilder();

		//Tab1を作成
		formulaDisplayField=new JTextField();
		resultDisplayField = new JTextField();
		//結果と途中式を２段で表示するパネルを作成
		TabPanel = makeNewTab(formulaDisplayField,resultDisplayField);
		
		/*↓田中 KeyListenerを追加↓*/
		
		formulaDisplayField.addKeyListener(new InputKeyboad(tester, this));
		resultDisplayField.addKeyListener(new InputKeyboad(tester, this));
		
		/*↑田中↑*/
		
		//フィールドにセット
		setFormulaDisplayField(formulaDisplayField);
		setResultDisplayField(resultDisplayField);
		
		// ボタンパネル GridLayoutに6行目を追加
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(6, 4));

		// ボタンの追加 ボタンの配置変更
		String[] buttons = {
				"(", ")", "←", "→",   // 1行目
				"AC", "C", "%", "÷",   // 2行目
				"7", "8", "9", "×",   // 3行目
				"4", "5", "6", "-",   // 4行目
				"1", "2", "3", "+",	  // 5行目
				"0", "DEL", ".", "=",  // 6行目
		};

		for (int i=0; i<buttons.length; i++) {
			JButton button = new JButton(buttons[i]);
			button.setFont(new Font("Arial", Font.PLAIN, 24));
			if (i==0 || i==1 || i==2 || i==3 || i==4 || i==5 || i==6) {
				button.setForeground(Color.BLACK);
				button.setBackground(Color.WHITE);
			} else if (i==7 || i==11 || i==15 || i==19 || i==23) {
				button.setForeground(Color.WHITE);
				button.setBackground(Color.ORANGE);
			} else {
				button.setForeground(Color.WHITE);
				button.setBackground(Color.GRAY);
			}
			button.addActionListener(this);
			
			//後にボタンのOn/Offを変更するため
			//「(」のボタンのアドレスをleftParenthesisButtonにセットしておく
			if(i==0) {leftParenthesisButton = button;}
			
			buttonPanel.add(button);
		}
		TabPanel.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public JPanel getButtonPanel() {
		return buttonPanel;
	}
	
	public JPanel getTabPanel() {
		return TabPanel;
	}

	public JTextField getResultDisplayField() {
		return resultDisplayField;
	}

	public void setResultDisplayField(JTextField resultDisplayField) {
		this.resultDisplayField = resultDisplayField;
	}

	public JTextField getFormulaDisplayField() {
		return formulaDisplayField;
	}

	public void setFormulaDisplayField(JTextField formulaDisplayField) {
		this.formulaDisplayField = formulaDisplayField;
	}
	
	//指定したDisplayFieldシリーズの両方を受け取り、タブを作って返す
	//ここでいうDisplayFieldシリーズは各formulaDisplayField と resultDisplayFieldのことです
	public JPanel makeNewTab(JTextField formulaDisplayField ,JTextField resultDisplayField) {
		JPanel Tab1=new JPanel();
		Tab1.setLayout(new BoxLayout(Tab1,BoxLayout.Y_AXIS));
		
		//途中式と結果のテキストフィールドの設定をしたものをTab1にセット
		Tab1.add(setFormulaText(formulaDisplayField));
		Tab1.add(setResultText(resultDisplayField));
		return Tab1;	
	}
	
	//式のテキストフィールドの設定
	public JTextField setFormulaText(JTextField formulaDisplayField) {
		formulaDisplayField.setEditable(false);
		formulaDisplayField.setFont(new Font("Arial",Font.PLAIN,24));
		formulaDisplayField.setPreferredSize(new Dimension(380,30)); //ここでテキストエリアのサイズ変更
		return formulaDisplayField;
	}
	//結果のテキストフィールドの設定
	public JTextField setResultText(JTextField formulaDisplayField) {
		formulaDisplayField.setEditable(false);
		formulaDisplayField.setFont(new Font("Arial",Font.PLAIN,24));
		formulaDisplayField.setPreferredSize(new Dimension(380,50)); //ここでテキストエリアのサイズ変更
		return formulaDisplayField;
	}
	
	// ボタンが押された時の処理
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		//一旦、「（」が復活
		leftParenthesisButton_On();

		// "=" が押された場合、計算を実行   =が押された時のエラー用の例外を追加 
		if(command.equals("=")) {
			try {
				formulaDisplayField.setText(inputBuilder1.toString());
				
				String result = tester.handleInput(inputBuilder1.toString().replaceAll("×", "*").replaceAll("÷","/"), true);
				
				resultDisplayField.setText(result);
			}catch(Exception ex) {
				resultDisplayField.setText("Error");
			}
		}
		// "%" が押された場合
		else if (command.equals("%")) {
			try {
				formulaDisplayField.setText(inputBuilder1.toString());
				// =の代わりにパーセントを使用するので＝と同じ計算を入れる
				String result = tester.handleInput(inputBuilder1.toString().replaceAll("×", "*").replaceAll("÷", "/"), true);
				 // 結果に100を掛ける
		        double percentageResult = Double.parseDouble(result) * 100;
		        // 計算結果を表示
		        resultDisplayField.setText(String.valueOf(percentageResult));
		    } catch (Exception e1) {
		        resultDisplayField.setText("Error");

			}
		}
		// オールクリアボタンが押された場合 C→ACに変更
		else if (command.equals("AC")) {
			inputBuilder1.setLength(0);
			inputPosition=0;
			formulaDisplayField.setText("");
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
			resultDisplayField.setText(inputBuilder1.toString());
		}
		
		//"←"が押された場合 入力位置を左に移動するコマンドを追加
		else if(command.equals("←")) {
			movePositionLeft();
		}
		
		//"→"が押された場合 入力位置を右に移動するコマンドを追加
		else if(command.equals("→")) {
			movePositionRight();
		}
		
		//四則演算子が入力された場合は何もしない
		else if(command.equals("×")||command.equals("÷")||command.equals("+")||command.equals("-")) {
			insertPosition(command);
		}
		
		// 入力ボタンが押された場合
		else{
			insertPosition(command);
			//「（」ボタンが押せなくなる
			leftParenthesisButton_Off();
		}
		
		/*ボタンを押した後にテンキー入力を可能にするフォーカス設定*/
		formulaDisplayField.requestFocus();
		resultDisplayField.requestFocus();
		
	}
	
	public void leftParenthesisButton_Off() {
		this.leftParenthesisButton.setEnabled(false);
	}
	public void leftParenthesisButton_On() {
		this.leftParenthesisButton.setEnabled(true);
	}

	//最後の数式を消去するメソッド
	public void clearLastExpression() {
		int lastOperatorIndex = Math.max(
				Math.max(inputBuilder1.lastIndexOf("+"), inputBuilder1.lastIndexOf("-")),
				Math.max(inputBuilder1.lastIndexOf("×"), inputBuilder1.lastIndexOf("÷"))
				);

		if (lastOperatorIndex >= 0) {
			inputBuilder1.setLength(lastOperatorIndex + 0);
			inputPosition=lastOperatorIndex;
			// 演算子以降を削除
		} else {
			inputBuilder1.setLength(0);  // 演算子がない場合は全てを削除
			inputPosition=0;
			formulaDisplayField.setText("");
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
	public void insertPosition(int i) { //テンキー入力用
		inputBuilder1.insert(inputPosition,i);
		inputPosition++;
		updateDisplay();
		
	}
	//表示を更新
	public void updateDisplay() {
		StringBuilder upd=new StringBuilder(inputBuilder1);
		upd.insert(inputPosition,"|"); //入力位置に入れる
		resultDisplayField.setText(upd.toString());
	}
	// メインウィンドウの起動メソッド
	public void showDisplay() {
		setVisible(true);
	}
}
