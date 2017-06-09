package net.sf.redisbook.ch5;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class PipelineData {
	private BufferedWriter writer;

	private String fileNamePrefix = "./redis_data";

	private String fileNamePostfix = ".txt";

	private final int TOTAL_NUMBER_OF_COMMAND = 10000000;

	public static void main(String[] args) throws IOException {
		PipelineData data = new PipelineData();
		data.makeDataFileAsProtocol();
		data.makeDataFileAsCommand();
	}

	private void makeDataFileAsCommand() throws IOException {
		String fileName = fileNamePrefix + "_command" + fileNamePostfix;
		writer = new BufferedWriter(new FileWriter(fileName));

		String key, value;
		for (int i = 0; i < TOTAL_NUMBER_OF_COMMAND; i++) {
			key = value = String.valueOf("key" + (100000000 + i));
			writer.write("set " + key + " " + value + "\r\n");
		}

		writer.flush();
		writer.close();
	}

	private void makeDataFileAsProtocol() throws IOException {
		String fileName = fileNamePrefix + "_protocol" + fileNamePostfix;
		writer = new BufferedWriter(new FileWriter(fileName));

		String key, value;

		for (int i = 0; i < TOTAL_NUMBER_OF_COMMAND; i++) {
			key = value = String.valueOf("key" + (100000000 + i));

			writer.write("*3\r\n");
			writer.write("$3\r\n");
			writer.write("set\r\n");
			writer.write("$" + key.length() + "\r\n");
			writer.write(key + "\r\n");
			writer.write("$" + value.length() + "\r\n");
			writer.write(value + "\r\n");
		}

		writer.flush();
		writer.close();
	}
}
