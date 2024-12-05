package kr.msp.example.http.tcp;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 등록된 Legacy 시스템을 주기적으로 감시하여 Load Balancing과 Failover 처리를 함
 */
@Slf4j
public class TcpAliveManager {
	private int lastSendSocketNo = 0;

	/**
	 * 감시 주기(60 * 1000)
	 */
	@Getter
	private long connectionRetryTime = 60 * 1000;

	/**
	 * 체크해야할 host Map
	 */
	@Getter
	private Map<String, SocketAddress> allHostMap = new HashMap<>();

	/**
	 * 연결 가능한 호스트 목록
	 */
	@Getter
	private List<String> aliveHostNameList = new ArrayList<>();

	/**
	 * 연결 가능한 호스트 Name Set
	 */
	@Getter
	private Set<String> aliveHostNameSet = new HashSet<>();

	/**
	 * 연결 불가능한 호스트 목록
	 */
	@Getter
	private List<String> badHostNameList = new ArrayList<>();

	private TcpAliveThread tcpAliveThread;

	private static TcpAliveManager instance;

	public static TcpAliveManager getInstance() {
		if (instance == null) {
			instance = new TcpAliveManager();
		}
		return instance;
	}

	public void init(List<String> hostNameList, long checkLoopTime) {
		connectionRetryTime = checkLoopTime * 1000;
		for (String hostName : hostNameList) {
			int findIndex = hostName.indexOf("://");
			if (findIndex > 0) {
				//http or https 경우는 포트를 명시 하지 않을 경우 default 값 80 or 443으로 세팅
				String protocol = hostName.substring(0, findIndex);
				//"://" 다음 문자 부터 가져오기 위해 +3
				String hostAddress = hostName.substring(findIndex + 3);
				String chkProtocol = protocol.trim().toLowerCase();
				String onlyHostName = hostAddress;
				//http or https 경우 컨텍스트 루트가 있을 수 있고 포트가 없을 수 있다.
				int ctxRootChkIndex = hostAddress.indexOf("/");
				if (ctxRootChkIndex > 0) {
					onlyHostName = hostAddress.substring(0, ctxRootChkIndex);
				}
				String aliveChkIP = "";
				int aliveChkPort = 0;
				int portChkIndex = onlyHostName.indexOf(":");
				// 포트가 없을 경우 http, https 경우 기본포트로 셋팅
				if (portChkIndex < 0) {
					if (chkProtocol.equals("http")) {
						aliveChkIP = onlyHostName;
						aliveChkPort = 80;
					} else if (chkProtocol.equals("https")) {
						aliveChkIP = onlyHostName;
						aliveChkPort = 443;
					} else {
						badHostNameList.add(hostName);
					}
				} else {
					try {
						String[] conAddressArr = onlyHostName.split(":");
						aliveChkIP = conAddressArr[0];
						aliveChkPort = Integer.parseInt(conAddressArr[1]);
					} catch (Exception e) {
						badHostNameList.add(hostName);
						log.error("Exception occurred.", e);
					}
				}

				try {
					//분리해 낸 아이피 포트 검증
					if (!aliveChkIP.isEmpty() && aliveChkPort != 0) {
						SocketAddress socketAddress = new InetSocketAddress(aliveChkIP, aliveChkPort);
						// 체크해야할 socketAddress을 등록한다.
						allHostMap.put(hostName, socketAddress);
					} else {
						badHostNameList.add(hostName);
					}
				} catch (Exception e) {
					badHostNameList.add(hostName);
					log.error("Exception occurred.", e);
				}

			} else {
				badHostNameList.add(hostName);
			}
		}
		if (!allHostMap.isEmpty()) {
			tcpAliveThread = new TcpAliveThread(this);
			tcpAliveThread.start();
		}

	}

	public synchronized String getConnectableHostName() throws Exception {
		if (aliveHostNameList.isEmpty()) {
			throw new Exception("There is no session is connected provider.");
		}

		int newConHostNo = 0;

		if (aliveHostNameList.size() > 1) {
			newConHostNo = (lastSendSocketNo) % aliveHostNameList.size();
			lastSendSocketNo = newConHostNo + 1;
		}
		log.trace("#### 연결할 호스트 번호 :" + newConHostNo +
			"    가장 최근 연결 한 호스트 번호:" + lastSendSocketNo +
			"    연결 가능한 호스트 사이즈 :" + aliveHostNameList.size());

		return aliveHostNameList.get(newConHostNo);
	}

	/**
	 * 연결 성공 시 처리
	 * @param hostName 성공 처리 할 hostName
	 * @param socketAddress 성공 처리할 socketAddress
	 */
	protected synchronized void successConnectSocketAddress(String hostName, SocketAddress socketAddress) {
		aliveHostNameList.add(hostName);
		aliveHostNameSet.add(hostName);
	}

	/**
	 * 연결 실패 시 처리
	 * @param hostName 실패 처리 할 hostName
	 * @param socketAddress 실패 처리 할 socketAddress
	 */
	protected synchronized void failConnectSocketAddress(String hostName, SocketAddress socketAddress) {
		aliveHostNameList.remove(hostName);
		aliveHostNameSet.remove(hostName);

		if (aliveHostNameList.size() != aliveHostNameSet.size()) {
			log.warn("!!!!!! ALIVE HOST 동기화 작업 수행 !!!!!!!!!");
			aliveHostNameList.clear();
			aliveHostNameList.addAll(aliveHostNameSet);
		}
	}

	// /**
	//  * 사용 가능한 Legacy System Host List 정보를 조회하는 메서드
	//  * @return 사용 가능한 Legacy System Host List 정보
	//  */
	// public List<String> getAliveHostNameList() {
	// 	return new ArrayList<>(aliveHostNameList);
	// }

	/**
	 * 현재 할당 가능한 네거시 시스템 호스트 정보를 프린트 한다.
	 */
	public void tcpAliveInfoPrintOut() {
		log.info("### BAD HOSTS : {}", new Gson().toJson(badHostNameList));
		log.info("### Alive HOSTS : {}", new Gson().toJson(aliveHostNameList));
	}

	/**
	 * 어플리케이션 종료 시 감시 Thread 종료
	 */
	public void destroy() {
		tcpAliveThread.interrupt();
		tcpAliveThread.setRunning(false);
	}
}
