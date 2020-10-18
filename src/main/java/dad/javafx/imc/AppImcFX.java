package dad.javafx.imc;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

/**
 * @author Ayoze Amaro
 *
 */
public class AppImcFX extends Application {

	private DoubleProperty altura = new SimpleDoubleProperty();
	private DoubleProperty peso = new SimpleDoubleProperty();
	private DoubleProperty indice = new SimpleDoubleProperty();
	
	private TextField pesoIntroducido, alturaIntroducido;
	private Label IMCText, INFOText;
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		pesoIntroducido = new TextField();
		pesoIntroducido.setPrefColumnCount(4);
		
		alturaIntroducido = new TextField();
		alturaIntroducido.setPrefColumnCount(4);

		// HBOX Peso
		HBox pesoBox = new HBox();
		Label pesoInfo1 = new Label("Peso: ");
		Label pesoInfo2 = new Label("kg");
		pesoBox.setAlignment(Pos.BASELINE_CENTER);
		pesoBox.setSpacing(5);
		pesoBox.getChildren().addAll(pesoInfo1, pesoIntroducido, pesoInfo2);
		
		// HBOX Altura
		HBox alturaBox = new HBox();
		Label alturaInfo1 = new Label("Altura: ");
		Label alturaInfo2 = new Label("cm");
		alturaBox.setAlignment(Pos.BASELINE_CENTER);
		alturaBox.setSpacing(5);
		alturaBox.getChildren().addAll(alturaInfo1, alturaIntroducido, alturaInfo2);
		
		// Añadimos el label de resultado IMC
		IMCText = new Label("IMC: ");
		
		// HBOX Resultado IMC
		HBox imcBox = new HBox();
		imcBox.setAlignment(Pos.BASELINE_CENTER);
		imcBox.setSpacing(5);
		imcBox.getChildren().addAll(IMCText);
		
		// Añadimos el label de información IMC
		INFOText = new Label("Bajo Peso | Peso normal | Sobrepeso | Obeso");
		
		// HBOX Resultado Peso
		HBox infBox = new HBox();
		infBox.setAlignment(Pos.BASELINE_CENTER);
		infBox.setSpacing(5);
		infBox.getChildren().addAll(INFOText);
		
		// VBox principal
		VBox root = new VBox();
		root.setSpacing(5);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(pesoBox, alturaBox, imcBox, infBox);

		Scene escena = new Scene(root, 320, 200);
		
		primaryStage.setScene(escena);
		primaryStage.setTitle("IMC");
		primaryStage.show();
		
		// Bidings de peso y altura 
		Bindings.bindBidirectional(pesoIntroducido.textProperty(), peso, new NumberStringConverter());
		Bindings.bindBidirectional(alturaIntroducido.textProperty(), altura, new NumberStringConverter());
		
		// Calculo del IMC
		indice.bind(peso.divide((altura.divide(100)).multiply(altura.divide(100))));
		
		// Resultado del IMC
		IMCText.textProperty().bind(indice.asString("IMC: %.2f"));
		
		// Selección de la info del IMC
		IMCText.textProperty().addListener(e -> {
			if (indice.doubleValue() < 18.5)
				INFOText.setText("Bajo peso");
			else if (indice.doubleValue() >= 18.5 && indice.doubleValue() < 25)
				INFOText.setText("Peso normal");
			else if (indice.doubleValue() >= 25 && indice.doubleValue() < 30)
				INFOText.setText("Sobrepeso");
			else if (indice.doubleValue() > 30)
				INFOText.setText("Obesidad");
		});

	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
