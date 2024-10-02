package Matuzaki;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI {
	
	//GUIじゃなくてパネルAクラスでもいいかも
//	static ArrayList<Integer> NumList = new ArrayList<>();
	
	
	




	GUI() {
		Input.NumList = new ArrayList<>();
		
		JFrame frame = new JFrame();
		JPanel pane = new JPanel();
		
		//1ボタン 説明用
		JButton b1 = new JButton("1");
		pane.add(b1);
		//ActionListenerのactionPerformedメソッドを実装し実行
		ActionListener al1 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				//1が押されたらリストに１を追加
				Input.NumList.add(1);
				System.out.println("1");
			}
		};
		b1.addActionListener(al1);
		
		//２ボタン
		JButton b2 = new JButton("2");
		pane.add(b2);
		//ActionListenerのactionPerformedメソッドを実装しInput.makeALNum(3)
		//
		b2.addActionListener(Input.makeALNum(3));
		
		
		//３ボタン
		JButton b3 = new JButton("3");
		pane.add(b3);
		//Inputのメソッドを実行
		b3.addActionListener(Input.makeALNum(3));
		
		
		
		
		
		JFrame resultFrame = new JFrame();
		JPanel resultPane = new JPanel();
		//＝ボタン
		//今はテストのため、listの中身表示ボタン
		JButton bEqu = new JButton("=");
		resultPane.add(bEqu);
		//ActionListenerのactionPerformedメソッドを実装し実行
		ActionListener alEqu = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				//1が押されたらリストに１を追加し、表示
				System.out.print("NumListの中身は");
				for(Integer a : Input.NumList) {
					System.out.print(a + ",");
				}
				System.out.println("1");
			}
		};
		bEqu.addActionListener(alEqu);
		//サイズセット(横幅、縦幅)	
		resultFrame.add(resultPane);
		resultFrame.setSize(300,200);
		resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		resultFrame.setVisible(true);
		
		
		
		//サイズセット(横幅、縦幅)	
		frame.add(pane);
		frame.setSize(300,200);
		
		//ピクセル数で場所を指定
		frame.setLocation(200,150);
		/*この2つはセット*/
		//フレームを閉じる記述
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//trueのときウィンドウを可視化
		frame.setVisible(true);
		
	}

	

}
