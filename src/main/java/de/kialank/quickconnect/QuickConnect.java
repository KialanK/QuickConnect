package de.kialank.quickconnect;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.ServerList;

public class QuickConnect implements ClientModInitializer {
	private static String firstAddress;
	private static String secondAddress;
	private static String thirdAddress;
	private static String firstName;
	private static String secondName;
	private static String thirdName;

	@Override
	public void onInitializeClient() {

		MinecraftClient client = MinecraftClient.getInstance();
		ServerList serverList = new ServerList(client);
		serverList.loadFile();

		if (serverList.size() > 0) {
			ServerInfo firstServer = serverList.get(0);
			firstAddress = firstServer.address;
			firstName = firstServer.name;
		}

		if (serverList.size() > 1) {
			ServerInfo secondServer = serverList.get(1);
			secondAddress = secondServer.address;
			secondName = secondServer.name;
		}

		if (serverList.size() > 2) {
			ServerInfo thirdServer = serverList.get(2);
			thirdAddress = thirdServer.address;
			thirdName = thirdServer.name;
		}
	}

	public static String getFirstAddress() {return firstAddress;}
	public static String getSecondAddress() {return secondAddress;}
	public static String getThirdAddress() {return thirdAddress;}
	public static String getFirstName() {return firstName;}
	public static String getSecondName() {return secondName;}
	public static String getThirdName() {return thirdName;}

}