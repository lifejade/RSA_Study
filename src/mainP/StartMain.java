package mainP;

import guiP.RSAGUI;

public class StartMain {
	public static void main(String[] args) throws Exception {
		Manager.getMan();
		Manager.getMan().existfile();
		RSAGUI.getmaingui();
	}

}
