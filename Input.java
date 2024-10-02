package Matuzaki;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Input{
	static ArrayList<Integer> NumList = new ArrayList<>();
	
	Input(){

	}

	public static ActionListener makeALNum(int num) {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				//1が押されたらリストに１を追加
				Input.NumList.add(num);
				System.out.println(num);	
			}
		};
		return al;
	}
	




}
