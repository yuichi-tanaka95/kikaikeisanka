package Display;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import CalcTester.CalcTester;

public class Display extends JFrame implements ActionListener {

	private JTextField displayField;  // 数式と結果の表示フィールド
	private StringBuilder inputBuilder;  // 入力値を保持する
	private CalcTester tester; // CalcTesterへの参照

	
	//プッシュ確認用テキスト
	
	
	
	public Display(CalcTester tester) {
		this.tester = tester; // CalcTesterを保存
		inputBuilder = new StringBuilder();

		// フレーム設定
		setTitle("多機能電卓");
		setSize(400, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// 表示フィールド
		displayField = new JTextField();
		displayField.setEditable(false);
		displayField.setFont(new Font("Arial", Font.PLAIN, 24));
		add(displayField, BorderLayout.NORTH);

		// ボタンパネル
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(5, 4));

		// ボタンの追加
		String[] buttons = {
				"7", "8", "9", "÷",   // 1行目
				"4", "5", "6", "×",   // 2行目
				"1", "2", "3", "-",   // 3行目
				"0", ".", "=", "+",   // 4行目
				"C", "(", ")", "+"    // 5行目（最後の行にC,(,),+）
		};

		for (String text : buttons) {
			JButton button = new JButton(text);
			button.setFont(new Font("Arial", Font.PLAIN, 24));
			button.addActionListener(this);
			buttonPanel.add(button);
		}

		add(buttonPanel, BorderLayout.CENTER);
	}

	// ボタンが押された時の処理
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		// "=" が押された場合、計算を実行
		if (command.equals("=")) {
			String result = tester.handleInput(inputBuilder.toString()
					.replaceAll("×", "*").replaceAll("÷", "/"));
			displayField.setText(result);
		}
		// クリアボタンが押された場合
		else if (command.equals("C")) {
			inputBuilder.setLength(0);
			displayField.setText("");
		}
		// 入力ボタンが押された場合
		else {
			inputBuilder.append(command);
			displayField.setText(inputBuilder.toString());
		}
	}

	// メインウィンドウの起動メソッド
	public void showDisplay() {
		setVisible(true);
	}
}