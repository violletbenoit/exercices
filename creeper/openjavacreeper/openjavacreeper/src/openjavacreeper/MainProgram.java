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
		stage.setWidth(800);
		stage.setHeight(600);
		// met un titre dans la fenêtre
		stage.setTitle("Joli décor!");

		// la racine du sceneGraph est le root
		Group root = new Group();
		Scene scene = new Scene(root);
		scene.setFill(Color.SKYBLUE);

		// création du soleil
		Circle sun = new Circle(60, Color.web("yellow", 0.8));
		sun.setCenterX(600);
		sun.setCenterY(100);

		// création du sol
		Rectangle ground = new Rectangle(0, 400, 800, 200);
		ground.setFill(Color.GREEN);

		// création d'un élément plus complexe, le panneau
		Group sign = new Group();
		sign.setTranslateX(150);
		sign.setTranslateY(200);
		// Attention les coordonnées sont celles du panneau, pas de la scène
		Text text = new Text(10, 30, "Hello world!");
		text.setFont(new Font(80));
		text.setFill(Color.WHITE);
		// le repère utilisé est celui du panneau
		Rectangle panel = new Rectangle( 0, -50, 500, 110);
		panel.setFill(Color.DARKBLUE);
		// composer l'élément plus complexe
		sign.getChildren().add(panel);
		sign.getChildren().add(text);



		// image
		int SIZE = 10;
		Random random = new Random();

		int [][] hauteurs = new int[SIZE][SIZE];
		for (int i = 0; i<SIZE; i++){
			for (int j=0 ;j<SIZE; j++){
				hauteurs[i][j] = random.nextInt(20);
			}
		}

		int [][] hauteurseau = new int[SIZE][SIZE];
		for (int i = 0; i<SIZE; i++){
			for (int j=0 ;j<SIZE; j++){
				hauteurseau[i][j] = random.nextInt(20);
			}
		}

		Image img = new Image("file:asset/Blocks/Yellow Grass 1.png");
		ImageView [][] tabImgView = new ImageView[SIZE][SIZE];
		Image imgeau = new Image("file:asset/Blocks/Blue 1.png");
		ImageView [][] tabImgViewEau = new ImageView[SIZE][SIZE];
		for (int i = 0; i<SIZE; i++){
			for (int j=0 ;j<SIZE; j++){


				tabImgView[i][j] = new ImageView(img);
				root.getChildren().add(tabImgView[i][j]);
				tabImgView[i][j].setTranslateX((i-j+SIZE)*80);
				tabImgView[i][j].setTranslateY((i+j)*40+hauteurs[i][j]);

				tabImgViewEau[i][j] = new ImageView(imgeau);
				root.getChildren().add(tabImgViewEau[i][j]);
				tabImgViewEau[i][j].setTranslateX((i-j+SIZE)*80);
				tabImgViewEau[i][j].setTranslateY((i+j)*40+hauteurseau[i][j]);

				if (hauteurs[i][j]>hauteurseau[i][j]){
					tabImgViewEau[i][j].setVisible(false);
				}else{
					tabImgView[i][j].setVisible(false);
				}
			}
		}

		Canvas canvas = new Canvas(stage.getWidth(),stage.getHeight());
		root.getChildren().add(canvas);
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
				float delta = (float) ((now - firstnow)/1000000000.0);
				int deltaint = (int) delta;
				if (deltaint!=inttime){
					System.out.println("1s");
					inttime = deltaint;
				}
				final double width = 0.5 * stage.getWidth();
				final double height = 0.5 * stage.getHeight();
				final double radius =Math.min(width, height);
				text.setTranslateX(Math.cos(delta) * radius);
				text.setTranslateY(Math.sin(delta) * radius);
			}
		};
		timer.start();

	}


}

