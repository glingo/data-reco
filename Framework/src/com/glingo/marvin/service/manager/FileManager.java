package com.glingo.marvin.service.manager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager extends Manager<File> {

	protected static final File[] ROOTS = File.listRoots();

	@Override
	protected void save(File managed) throws IOException {
		if (managed != null && !managed.exists() && !managed.isDirectory()) {
			System.out.println("Saving : " + managed.getAbsolutePath());
			managed.createNewFile();
		}
	}

	@Override
	protected File load(String ressource) {
		
		System.out.println("Loading : " + ressource);

		File file = new File(ressource);

		if (!file.isDirectory()) {
			cache.put(ressource, file);
		}
		
		File[] files = file.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				load(files[i].getPath());
			}
		}

		return file;
	}

	@Override
	protected File update(File update) throws IOException {
		System.out.println("Updating : " + update.getAbsolutePath());
		
		save(update);
		
		if (update.isDirectory()) {
			File[] files = update.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					update(files[i]);
				}
			}
		}

		return update;
	}
	
	public static void main(String[] args) {
		FileManager loader = new FileManager();
		File tmp = loader.load("/tmp");

		try {
			FileWriter writer = new FileWriter(loader.get("/tmp/test.txt"));
			writer.write("Bonjour");
			writer.flush();
			writer.close();
			loader.update(tmp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
