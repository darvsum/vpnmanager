package ar.com.gnuler.view.pki;

import java.security.Security;

import org.apache.wicket.markup.html.WebMarkupContainer;

import ar.com.gnuler.pki.CertificateAuthorityFactory;
import ar.com.gnuler.view.ViewTemplate;
import ar.com.gnuler.view.components.OpenVPNServersList;

public class PKIMainView extends ViewTemplate {
	
	private WebMarkupContainer container;
	
	public PKIMainView(){
		
		
		
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		container = new WebMarkupContainer("container");
		container.setOutputMarkupId(true);
		container.add(new CAList("servers", container));
		
		add(container);

	}
}
