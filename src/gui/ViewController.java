package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import gui.util.Alerts;
import gui.util.Constraints;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Person;

public class ViewController implements Initializable {

	@FXML
	private TextField txtNumber1;
	@FXML
	private TextField txtNumber2;
	@FXML
	private Label labelResult;
	@FXML
	private Button btSum;
	@FXML
	private ComboBox<Person> comboBox;
	@FXML
	private Button btShowAll;

	@Override
	public void initialize(URL url, ResourceBundle resBundle) {
		Constraints.setTextFieldDouble(txtNumber1);
		Constraints.setTextFieldDouble(txtNumber2);
		Constraints.setTextFieldMaxLength(txtNumber1, 6);
		Constraints.setTextFieldMaxLength(txtNumber2, 6);

		List<Person> list = new ArrayList<>();
		list.add(new Person(1, "Maria", "maria@gmail.com"));
		list.add(new Person(2, "Alex", "alex@gmail.com"));
		list.add(new Person(3, "Bob", "bob@gmail.com"));

		ObservableList<Person> obsList = FXCollections.observableArrayList(list);
		comboBox.setItems(obsList);

		// Personaliza o que é mostrado na combo box
		Callback<ListView<Person>, ListCell<Person>> factory = lv -> new ListCell<Person>() {
			@Override
			protected void updateItem(Person item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getName());
			}
		};

		comboBox.setCellFactory(factory);
		comboBox.setButtonCell(factory.call(null));
	}

	@FXML
	public void onBtSumAction() {

		Locale.setDefault(Locale.US);
		try {
			double number1 = Double.parseDouble(txtNumber1.getText());
			double number2 = Double.parseDouble(txtNumber2.getText());
			double sum = number1 + number2;
			labelResult.setText(String.format("%.2f", sum));
		} catch (Exception e) {
			Alerts.showAlert("Error", null, e.getMessage(), AlertType.ERROR);
		}

	}

	@FXML
	public void onComboBoxAction() {
		Person person = comboBox.getSelectionModel().getSelectedItem();
		System.out.println(person);
		Alerts.showAlert("Person", null, person.getName(), AlertType.INFORMATION);
	}

	@FXML
	public void onBtShowAllAction() {
		StringBuilder sb = new StringBuilder();
		for (Person person : comboBox.getItems()) {
			System.out.println(person);
			sb.append(person.getName() + "\n");
		}
		Alerts.showAlert("Person", null, sb.toString(), AlertType.INFORMATION);
	}

}
