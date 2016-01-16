package udpSocketTradingTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import udpSocketTrading.UDPsocketTrader;

public class UdpSocketTraderTest {
	@Test
	public void udpSocketTradingTest() {
		List<UDPsocketTrader> udpSocketTradingList = new ArrayList<UDPsocketTrader>();
		udpSocketTradingList.add(new UDPsocketTrader(20001, "s_1"));
		udpSocketTradingList.add(new UDPsocketTrader(20002, "s_2"));
		udpSocketTradingList.add(new UDPsocketTrader(20003, "s_3"));
		udpSocketTradingList.add(new UDPsocketTrader(20004, "s_4"));
		udpSocketTradingList.add(new UDPsocketTrader(20005, "s_5"));

		List<Thread> threadList = new ArrayList<Thread>();
		for (UDPsocketTrader a : udpSocketTradingList) {
			threadList.add(new Thread(a));
		}

		for (Thread t : threadList) {
			t.start();
		}
		try {
			for (Thread t : threadList)
				t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
