package ar.com.gnuler.view.pki;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

import ar.com.gnuler.view.ViewTemplate;

public class CreateCAView extends ViewTemplate {
	
//	OpenVPNServer  server = null;
	private String name;
	private String mail;
	private String cn;
	
	
	public CreateCAView(){
		
			
		Form form = new Form("createcaform"){

			
			private static final long serialVersionUID = 1L;
			
			public void onSubmit() {
					
				System.out.println("*********************");
				System.out.println(name);
				System.out.println(cn);
				System.out.println(mail);
				System.out.println("*********************");
				
//				try {
//					ICertificateAuthority ca1 = CertificateAuthorityFactory.createSelfSignedCA(name, cn, mail);
//					CAStore.getInstance().addCA(ca1);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//				for (String name: CAStore.getInstance().getCaNames())
//					System.out.println(name);
				
				//TODO Redirigir a la pagina view de OpenVPNServers
				setResponsePage(PKIMainView.class);
			}

		};
			

		
		// Ok
		form.add(new TextField<String>("name", new PropertyModel<String>(this, "name")));
		form.add( new TextField<String>("cn", new PropertyModel<String>(this, "cn")));
		form.add( new TextField<String>("mail", new PropertyModel<String>(this, "mail")));

		
		add(form);
		
		add(new FeedbackPanel("feedback"));

	}
	
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setCN(String cn){
		this.cn = cn;
	}

	
	public void setMail(String mail){
		this.mail = mail;
	}
}
