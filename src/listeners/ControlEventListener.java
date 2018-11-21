package listeners;

import java.awt.event.ActionEvent;
import java.io.File;

public interface ControlEventListener  {
	 public void onNewGameButtonClicked(ActionEvent event);
	 public void onLoadGameButtonClicked(ActionEvent event, File file);
	 public void onSaveGameClicked(ActionEvent event, File file);
	 public void onRollDiceClicked(ActionEvent event);
}
