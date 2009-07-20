package ar.com.gnuler.vpn.openvpn;

public interface IOpenVPNConfigFileInterpreter {

	public abstract String generateConfigFile(OpenVPNServerInstance server, String baseConfigPath, String baseLogPath, String baseRunPath);

}