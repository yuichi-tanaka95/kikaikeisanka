package Display;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import CalcTester.CalcTester;

public class InputKeyboad implements KeyListener{
	
protected CalcTester tester;
protected Display display;

	public InputKeyboad(CalcTester tester,Display display) {
		this.tester=tester;
		this.display=display;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		//使用しない
	}
//テンキーに動作を設定
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
        case KeyEvent.VK_NUMPAD0:			//テンキーの数字が押された時の設定
            display.insertPosition("0");
            break;
        case KeyEvent.VK_NUMPAD1:
            display.insertPosition("1");
            break;
        case KeyEvent.VK_NUMPAD2:
            display.insertPosition("2");
            break;
        case KeyEvent.VK_NUMPAD3:
            display.insertPosition("3");
            break;
        case KeyEvent.VK_NUMPAD4:
            display.insertPosition("4");
            break;
        case KeyEvent.VK_NUMPAD5:
            display.insertPosition("5");
            break;
        case KeyEvent.VK_NUMPAD6:
            display.insertPosition("6");
            break;
        case KeyEvent.VK_NUMPAD7:
            display.insertPosition("7");
            break;
        case KeyEvent.VK_NUMPAD8:
            display.insertPosition("8");
            break;
        case KeyEvent.VK_NUMPAD9:
            display.insertPosition("9");
            break;
        case KeyEvent.VK_ENTER:		//ENTERキーが押された時の設定					
            try {
                display.getFormulaDisplayField().setText(display.inputBuilder1.toString());
                String result = tester.handleInput(display.inputBuilder1.toString().replaceAll("×", "*").replaceAll("÷", "/"), false);
                display.resultDisplayField.setText(result);
            } catch (Exception ex) {
                display.resultDisplayField.setText("Error");	
            }
            break;
        case KeyEvent.VK_BACK_SPACE:		//BACKSPACEキーが押された時の設定
            if (display.inputPosition > 0) {
                display.inputBuilder1.deleteCharAt(display.inputPosition - 1);
                display.inputPosition--;
            }
            display.updateDisplay();
            break;
        case KeyEvent.VK_ADD:		//"＋"キーが押された時の設定
        	display.insertPosition("+");
        	break;
        case KeyEvent.VK_SUBTRACT:		//"-"キーが押された時の設定
        	display.insertPosition("-");
        	break;
        case KeyEvent.VK_MULTIPLY:		//"*"キーが押された時の設定
        	display.insertPosition("×");
        	break;
        case KeyEvent.VK_DIVIDE:		//"/"キーが押された時の設定
        	display.insertPosition("÷");
        	break;
        case KeyEvent.VK_DECIMAL:		//"."キーが押された時の設定
        	display.insertPosition(".");
        	break;
        case KeyEvent.VK_DELETE:		//DELETEキーが押された時の設定
        	display.inputBuilder1.setLength(0);
			display.inputPosition=0;
			display.formulaDisplayField.setText("");
			display.updateDisplay();
			break;
        case KeyEvent.VK_LEFT:		//左矢印キーが押された時の設定
        	display.movePositionLeft();
        	break;
        case KeyEvent.VK_RIGHT:	//右矢印キーが押された時の設定
        	display.movePositionRight();
        	default:
		}
}
	@Override
	public void keyReleased(KeyEvent e) {
		//使用しない
	}

}
