import com.esotericsoftware.kryonet.Client;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

public class ServerFinder {
    static String[] get() {
        Client client = new Client();
        List<InetAddress> ipList = client.discoverHosts(54777, 500);
        String[] ipStrings = new String[ipList.size()];
        for (int i = 0; i < ipList.size(); i++) {
            ipStrings[i] = (Arrays.toString(ipList.get(i).getAddress()));
        }
        return ipStrings;
    }
}
