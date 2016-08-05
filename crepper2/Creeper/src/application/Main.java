package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


public class Main extends Application {
	Plateau plateau;
	Pane root;
	Canvas canvas;
	int n=0;

	@Override
	public void start(Stage primaryStage) {
		
		try {
			root = new Pane();

			Polygon p = new Polygon(0,0,50,0,50,50,0,50);
			p.addEventHandler(MouseEvent.MOUSE_ENTERED, e->{
				((Polygon) e.getSource()).setFill(Color.RED);
			});
			p.addEventHandler(MouseEvent.MOUSE_EXITED, e->{
				((Polygon) e.getSource()).setFill(Color.BLUE);
			});
			
			canvas = new Canvas(1000,1000);
			
			plateau = new Plateau(30,canvas.getGraphicsContext2D());

			p.setFill(Color.ROSYBROWN.brighter());
			p.setStroke(Color.ROSYBROWN);
			root.getChildren().add(p);
			root.getChildren().add(canvas);
			//plateau.draw(root);
			Scene scene = new Scene(root,1000,1000);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			Draw(0);
		   /* Timer timer = new Timer();
		    timer.scheduleAtFixedRate(new TimerTask() {
		        @Override
		        public void run() {
					plateau.generateRandom(n);
					plateau.draw();	
					n++;

		        }
		    }, 0, 5000);*/

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Draw(int n){
			plateau.generateRandom(1000);
			plateau.draw();			
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
