package ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import ch.bfh.bti7081.s2016.purple.HealthVisitor.ui.controller.AppointmentDetailController;

public class AppointmentDetailView extends CustomComponent implements View {
	public static final String NAME = "AppointmentDetail";
	private static final Logger logger = LogManager.getLogger(AppointmentDetailView.class);
	private final AppointmentDetailController controller;
	private final VerticalLayout general;

	public AppointmentDetailView() {
		logger.debug("arrived on appointment detail view");
		controller = new AppointmentDetailController(this);

		general = new VerticalLayout();
		general.setMargin(new MarginInfo(false, false, true, true));
		general.setSpacing(true);

		ThemeResource resource = new ThemeResource("images/Logo_HealthVisitor.png");
		Image logo = new Image("Logo", resource);
		logo.setWidth("300px");
		logo.setCaption("");
		logo.addClickListener(clickevent ->
			getUI().getNavigator().navigateTo(DashboardView.NAME));
		

		Label lblHeader = new Label("Termin Detail");
		lblHeader.setStyleName("header");
		
		Button btBack = new Button("Zurück");
		btBack.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		btBack.addClickListener((clickEvent -> getUI().getNavigator().navigateTo(DashboardView.NAME)));
		
		Label lblTitle = new Label("Aktueller Termin: ");
		lblTitle.setStyleName("h1");
		
		Button btnArrival = new Button("Ankunft bestätigen");
		btnArrival.addClickListener(clickevent -> 
			btnArrivalClicked(btnArrival));
		
		

//		Google-Maps-Implementation
		LatLon pos = new LatLon(46.9648208, 7.453848);
		
		GoogleMap map = new GoogleMap("AIzaSyCnqIoyh9ULI3b6rtkCYXPdMXRqivaH714", null, "german");
		map.setSizeFull();
//		map.setHeight("20%");
//		map.setWidth("50%");
		map.addMarker("Wankdorffeldstrasse 102, 3014 Bern", pos, false, null);
		map.setMinZoom(4);
		map.setMaxZoom(16);
		map.setCenter(pos);
		map.setZoom(16);
		
//		Create Checklist
		Grid checklist = new Grid();
		checklist.setColumns("Aufgabe");
//		checklist.setCaption("Checkliste");
		
		
//		Create Column last report
		Label lastReport = new Label("Letzter Rapport");
		lastReport.setStyleName("h2");
		
		Label report = new Label("Hier steht der letzte Rapport.");
		
		Button btnNewReport = new Button("Neuer Rapport erfassen");
		btnNewReport.addClickListener(clickevent -> newReport());
		
		
		Button btnDetails = new Button("Details");
//		TODO: Show Patient data
		
		
//		Create Column Info and emergency-contact
		Label lblBeschriebTitle = new Label("Kurzbeschrieb");
		lblBeschriebTitle.setStyleName("h2");
		
		Label lblBeschrieb = new Label("Hier steht der Kurzbeschrieb über den Patienten");
		
		Button btnEmergencyContact = new Button("Notfallkontakte des Patienten");
//		TODO: EmerencyContactView
		//		btnEmergencyContact.addClickListener(clickevent -> getUI().getNavigator().navigateTo(emergencyContactView.NAME));
		

		// The Layout for the Logo
		HorizontalLayout hl = new HorizontalLayout(btBack, lblHeader);
		hl.setSpacing(true);
		hl.setMargin(new MarginInfo(true, false, true, false));
		
		VerticalLayout vl = new VerticalLayout();
		vl.addComponent(logo);
		vl.addComponent(btnArrival);
		vl.setComponentAlignment(logo, Alignment.TOP_RIGHT);
		vl.setComponentAlignment(btnArrival, Alignment.TOP_LEFT);
		vl.setMargin(new MarginInfo(false, false, true, false));
		vl.setSpacing(true);
		
		GridLayout top = new GridLayout(2, 2);
		top.setSizeFull();
		top.addComponent(hl, 0, 0);
		top.addComponent(vl, 1, 0);
//		top.addComponent(lblTitle, 0, 1);
		top.setMargin(new MarginInfo(false, true, false, false));
		
		
//		Set the data-Layout
		GridLayout data = new GridLayout(3, 5);
		data.setSpacing(true);
		data.setMargin(false);
		data.setSizeFull();
		
		
		
		
		data.addComponent(map, 0, 0, 0, 2);
		data.addComponent(checklist, 0, 3, 0, 4);
		data.addComponent(lastReport, 1, 0);
		data.setComponentAlignment(lastReport, Alignment.TOP_LEFT);
		data.addComponent(report, 1, 1, 1, 2);
		data.setComponentAlignment(report, Alignment.TOP_LEFT);
		data.addComponent(btnNewReport, 1, 3);
		data.setComponentAlignment(btnNewReport, Alignment.TOP_LEFT);
		data.addComponent(btnDetails, 1, 4);
		data.setComponentAlignment(btnDetails, Alignment.TOP_LEFT);
		data.addComponent(lblBeschriebTitle, 2, 0);
		data.setComponentAlignment(lblBeschriebTitle, Alignment.TOP_LEFT);
		data.addComponent(lblBeschrieb, 2, 1, 2, 3);
		data.setComponentAlignment(lblBeschrieb, Alignment.TOP_LEFT);
		data.addComponent(btnEmergencyContact, 2, 4);
		data.setComponentAlignment(btnEmergencyContact, Alignment.TOP_LEFT);
		
//		data.addComponents(map, checklist);
		
		
		

		// Set the root layout
		general.addComponent(top);
		general.addComponent(lblTitle);
		general.addComponent(data);
		setCompositionRoot(general);

	}
	
	private Object newReport() {
		final Window window = new Window("Neuer Rapport erstellen");
		window.setWidth("90%");
		final FormLayout content = new FormLayout();
		
		RichTextArea text = new RichTextArea();
		text.setImmediate(true);
		text.setSizeFull();
		content.addComponent(text);
		
		window.setContent(content);
		return null;
	}

	private void btnArrivalClicked(Button btn){
		btn.setCaption("Ende bestätigen");
		// TODO Persistence Current DateTime to the DB.
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
		// TODO Auto-generated method stub

	}

}
