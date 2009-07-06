package ar.com.gnuler.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import ar.com.gnuler.net.IPV4Address;
import ar.com.gnuler.net.InvalidAddressFormatException;
import ar.com.gnuler.net.Protocol;
import ar.com.gnuler.net.exceptions.InvalidProtocolException;
import ar.com.gnuler.net.exceptions.NotHostException;
import ar.com.gnuler.net.exceptions.NotSubnetException;
import ar.com.gnuler.view.components.IPV4AddressField;
import ar.com.gnuler.vpn.openvpn.OpenVPNServer;
import ar.com.gnuler.vpn.openvpn.OpenVPNServerManager;

public class CreateOpenVPNServerView extends WebPage {
	
	OpenVPNServer  server = null;
	
	public CreateOpenVPNServerView(){
		
		
		server = OpenVPNServerManager.getInstance().createServer();
		
		
		
		Form form = new Form("loginForm"){

			
			private static final long serialVersionUID = 1L;
			
			public void onSubmit() {
					System.out.println(server.getConfigFile());
					
			}

		};
			
		
		// Set default values, just for simplify testing
		// TODO remove this
		try {
			server.setVpnSubnet(IPV4Address.getAddressFromString("192.168.1.1/255.255.255.0"));
			
			server.setLocalIP(IPV4Address.getAddressFromString("192.168.1.1"));
			
			server.setPort(1194);
			server.setProtocol(Protocol.TCP);
			server.setDev(OpenVPNServer.VirtualDeviceType.tun);
			server.setDhFileName("dh1024.pem");
			server.setVpnSubnet(IPV4Address.getAddressFromString("172.40.0.0/255.255.0.0"));
			server.setPersistPoolFileName("/var/run/openvpn/ipp2.txt");
			server.addPushRoute(IPV4Address.getAddressFromString("10.0.0.0/255.0.0.0"));
			server.addPushDNS(IPV4Address.getAddressFromString("10.10.1.180"));
			server.setPersistKey(true);
			server.setPersistTun(true);
			server.setStatusFileName("/var/log/openvpn3-status.log");
			server.setAppendLog(true);
			server.setLogFilePath("var/log/openvpn.log");
			server.setLogVerbosity(3);
			server.setManagementAddress(IPV4Address.getAddressFromString("127.0.0.1"));
			server.setManagementPort(7505);
			server.setCaFileName("ca.crt");
			server.setCertFileName("server.crt");
			server.setKeyFileName("ca.key");
			server.setEnableManagement(true);
		
		
		} catch (NotSubnetException e) {
			e.printStackTrace();
		} catch (NotHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAddressFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Ok
		form.add(new TextField<String>("name", new Model<String>("")));
		form.add(new IPV4AddressField("address", new PropertyModel<IPV4Address>(server, "localIP")));
		form.add( new TextField<Integer>("port", new PropertyModel<Integer>(server, "port")));
		form.add( new TextField<String>("cafilename", new PropertyModel<String>(server,"caFileName")));
		form.add( new TextField<String>("certfilename", new PropertyModel<String>(server,"certFileName")));
		form.add( new TextField<String>("keyfilename", new PropertyModel<String>(server,"keyFileName")));
		form.add( new TextField<String>("dhfilename", new PropertyModel<String>(server, "dhFileName")));
		form.add( new TextField<String>("persistpoolfilename", new PropertyModel<String>(server, "persistPoolFileName")));
		
		form.add( new TextField<String>("statusfilename", new PropertyModel<String>(server,"statusFileName")));
		form.add( new TextField<String>("logfilename", new PropertyModel<String>(server, "logFileName")));
		form.add( new TextField<Integer>("logverbosity", new PropertyModel<Integer>(server, "logVerbosity")));
		form.add( new TextField<Integer>("managementport", new PropertyModel<Integer>(server,"managementPort")));
		form.add( new IPV4AddressField("managementaddress", new PropertyModel<IPV4Address>(server, "managementAddress")));
		form.add(new IPV4AddressField("vpnsubnet", new PropertyModel<IPV4Address>(server, "vpnSubnet")));
		
		
		// TODO ver como armar el modelo
		
		DropDownChoice<Protocol> protocolsChoice = new DropDownChoice<Protocol>(
				"protocol",
				new PropertyModel<Protocol>(server, "protocol"),
				new LoadableDetachableModel<List<Protocol>>(){
					private static final long serialVersionUID = 1L;

					@Override
					protected List<Protocol> load() {
						List<Protocol> l = new ArrayList<Protocol>();
						l.add(Protocol.TCP);
						l.add(Protocol.UDP);
                        return l;
                    }
					
				}
				);
		
		form.add(protocolsChoice);
		
		DropDownChoice<OpenVPNServer.VirtualDeviceType> deviceTypeChoice = new DropDownChoice<OpenVPNServer.VirtualDeviceType>(
				"devicetype",
				new PropertyModel<OpenVPNServer.VirtualDeviceType>(server, "dev"),
				new LoadableDetachableModel<List<OpenVPNServer.VirtualDeviceType>>(){
					private static final long serialVersionUID = 1L;

					@Override
					protected List<OpenVPNServer.VirtualDeviceType> load() {
						List<OpenVPNServer.VirtualDeviceType> l = new ArrayList<OpenVPNServer.VirtualDeviceType>();
						l.add(OpenVPNServer.VirtualDeviceType.tap);
						l.add(OpenVPNServer.VirtualDeviceType.tun);
                        return l;
                    }
					
				}
				);
		form.add(deviceTypeChoice);
		
		form.add( new CheckBox("persistkey", new PropertyModel<Boolean>(server, "persistKey")));
		form.add( new CheckBox("persisttun", new PropertyModel<Boolean>(server, "persistTun")));
		form.add( new CheckBox("appendlog", new PropertyModel<Boolean>(server, "appendLog")));
		form.add( new CheckBox("enablemanagement", new PropertyModel<Boolean>(server, "enableManagement")));
//		
//		form.add( new TextField("persistkey", new Model("")));
//		form.add( new TextField("persisttun", new Model("")));
//		form.add( new TextField("appendlog", new Model("")));
//		form.add( new TextField("enablemanagement", new Model("")));
//		
		
		
		
		

		
		add(form);
		
		add(new FeedbackPanel("feedback"));

	}

}
