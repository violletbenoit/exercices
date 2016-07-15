package openjavacreeper;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainProgram extends Application {

	public static void main(String[] args) {
		System.out.println( "Main method inside Thread : " +  Thread.currentThread().getName());
		launch(args);
	}			
	long firstnow = 0;
	int inttime = 0;

	@Override
	public void start(Stage stage) throws Exception {
		// définit la largeur et la hauteur de la fenêtre
		// en pixels, le (0, 0) se situe en haut à gauche de la fenêtre
		stage.setWidth(1000);
		stage.setHeight(1000);
		// met un titre dans la fenêtre
		stage.setTitle("Joli décor!");

		// la racine du sceneGraph est le root
		Group root = new Group();
		Scene scene = new Scene(root);
		scene.setFill(Color.SKYBLUE);

		Canvas canvas = new Canvas(stage.getWidth(),stage.getHeight());
//		canvas.getGraphicsContext2D().dra
		Plateau plateau = new Plateau();
		plateau.draw(canvas.getGraphicsContext2D());
		root.getChildren().add(canvas);
//		root.getChildren().add(testimg);
		// ajout de tous les éléments de la scène
		//   root.getChildren().add(sun);
		//   root.getChildren().add(ground);
		//   root.getChildren().add(sign);

		// ajout de la scène sur l'estrade
		stage.setScene(scene);
		// ouvrir le rideau
		stage.show();

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (firstnow == 0){
					firstnow = now;
				}
				float delta = (float) ((now - firstnow)/100000000.0);
				int deltaint = (int) delta;
				if (deltaint!=inttime){
					System.out.println("1s");
					inttime = deltaint;
					plateau.draw(canvas.getGraphicsContext2D());

				}
				plateau.tick();

				final double width = 0.5 * stage.getWidth();
				final double height = 0.5 * stage.getHeight();
				final double radius =Math.min(width, height);
			}
		};
		timer.start();

	}


}

