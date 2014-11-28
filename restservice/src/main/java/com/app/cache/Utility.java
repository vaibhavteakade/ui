package com.app.cache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class Utility {
	public static Logger logger = Logger.getLogger(Utility.class);
	/**
	 * @param path
	 * @param fileName
	 * @param data
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static synchronized void writeJsonFile(String path, String fileName,
			String data) throws FileNotFoundException, IOException {
		FileOutputStream fileOutputStream = null;
		File file;
		try {
			file = new File(path + File.separator + fileName);
			fileOutputStream = new FileOutputStream(file, false);
			/**
			 * if file does not exists, then create it
			 */
			if (!file.exists()) {
				file.createNewFile();
			}
			/**
			 * get the data in bytes
			 */
			byte[] contentInBytes = data.getBytes();
			fileOutputStream.write(contentInBytes);
			logger.info(fileName + " Updated successfully");
		} finally {
			if (fileOutputStream != null) {
				fileOutputStream.flush();
				fileOutputStream.close();
			}
		}
	}

	/**
	 * @param path
	 * @param fileName
	 * @return string
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static synchronized String readJsonFile(String path, String fileName)
			throws IOException, FileNotFoundException {
		logger.debug("Reading file :" + path + File.separator + fileName);
		StringBuilder fileContent = new StringBuilder();
		FileReader fileReader = null;
		BufferedReader br = null;
		try {
			fileReader = new FileReader(path + File.separator + fileName);
			br = new BufferedReader(fileReader);
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				fileContent.append(sCurrentLine);
			}
		} finally {
			br.close();
			fileReader.close();
		}
		return fileContent.toString();
	}

	/**
	 * configure log4j properties
	 * 
	 * @param path
	 */
	public static void configureLog4j(String path) {
		Logger rootLogger = Logger.getRootLogger();
		if (!rootLogger.getAllAppenders().hasMoreElements()) {
			rootLogger.setLevel(Level.INFO);
			rootLogger.addAppender(new ConsoleAppender(new PatternLayout(
					"%d %p [%c{1}] - %m%n")));

			FileAppender fa = new FileAppender();
			fa.setName("FileLogger");
			fa.setFile(path + File.separator + "mock_server.log");
			fa.setLayout(new PatternLayout("%d %p [%c{1}] - %m%n"));
			fa.setThreshold(Level.DEBUG);
			fa.setAppend(true);
			fa.activateOptions();
			rootLogger.addAppender(fa);
		}
	}

	/**
	 * initialze Utile 
	 */
		public static void init() {
			String resourceLocation = Utility.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath();
			String parentFolder = new File(resourceLocation).getParent();
			System.out.println(parentFolder);
				Utility.configureLog4j(parentFolder + File.separator + "logs");
		}
}
