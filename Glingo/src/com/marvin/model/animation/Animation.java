package com.marvin.model.animation;

import java.awt.Image;
import java.util.ArrayList;

public class Animation {

	private ArrayList<Scene> scenes;
	private int time;
	private int index;

	public synchronized void addScene(Image i, int t) {
		time += t;
		scenes.add(new Scene(i, t));
	}

	public synchronized void update(int timePassed) {

		if (scenes != null && !scenes.isEmpty()) {
			time += timePassed;

			if (time >= scenes.size()) {
				time = 0;
				index = 0;
			}

			while (time > getScene(index).getEnd()) {
				index++;
			}
		}
	}

	public synchronized Image getImage() {
		Image image = null;
		if (scenes != null && !scenes.isEmpty()) {
			image = getScene(index).getImage();
		}
		return image;
	}

	public synchronized Scene getScene(int x) {
		Scene scene = null;

		if (scenes != null && !scenes.isEmpty()) {
			scene = scenes.get(x);
		}

		return scene;
	}

}
