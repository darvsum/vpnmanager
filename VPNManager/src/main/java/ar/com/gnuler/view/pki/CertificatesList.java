package ar.com.gnuler.view.pki;


import java.security.KeyStoreException;
import java.security.cert.X509Certificate;

import javax.security.auth.x500.X500Principal;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;

import ar.com.gnuler.pki.IdentityStore;

/*
 * Example usage:
 * 
 * <tr wicket:id="servers">
 *		<td><span wicket:id="name">Test ID</span></td>
 *		<td><span wicket:id="state">Test ID</span></td>
 *		<td><a href="#" wicket:id="delete">delete</a></td>
 *		<td><a href="#" wicket:id="startstoplink"><span wicket:id="startstoplabel">Test ID</span></a></td>
 *		<td><a href="#" wicket:id="viewlog">view log</a></td>
 *	</tr>
 *
 */

public class CertificatesList extends DataView<String> {

	private static final long serialVersionUID = 1L;
	Component componentToUpdate;
		
	
	public CertificatesList(String id, Component componentToUpdate) {
		super(id, new CANameListModel());
		setOutputMarkupId(true);
		this.componentToUpdate = componentToUpdate;
	}

	

	@Override
	protected void populateItem(Item<String> item) {
		String alias = item.getModelObject();
		
		item.add(new Label("alias", alias));
		
		IdentityStore is = IdentityStore.getInstance();
		
		try {
			X509Certificate cert = is.getCertificateForAlias(alias);

			item.add(new Label("commonName", cert.getSubjectX500Principal().toString()));	
			
			
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
	}

}
