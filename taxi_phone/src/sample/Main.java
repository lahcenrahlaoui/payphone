package sample;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

import static com.sun.javafx.scene.control.skin.Utils.getResource;
import static sample.Controller.*;
import static sample.Controller.X;
import static sample.Controller.Y;

public class Main extends Application {
// START DECLARATION

    //image disacrtive cabin
    public static ImageView img0  = imagDisactiveCabin();
    public static ImageView img2  = imagDisactiveCabin();
    public static ImageView img4  = imagDisactiveCabin();
    public static ImageView img6  = imagDisactiveCabin();
    public static ImageView img8  = imagDisactiveCabin();
    public static ImageView img10 = imagDisactiveCabin();
    public static ImageView img12 = imagDisactiveCabin();
    public static ImageView img14 = imagDisactiveCabin();

    //image active cabin
    public static ImageView img1  = imagActiveCabin();
    public static ImageView img3  = imagActiveCabin();
    public static ImageView img5  = imagActiveCabin();
    public static ImageView img7  = imagActiveCabin();
    public static ImageView img9  = imagActiveCabin();
    public static ImageView img11 = imagActiveCabin();
    public static ImageView img13 = imagActiveCabin();
    public static ImageView img15 = imagActiveCabin();

    // image disactive chair
    public static ImageView img00 = imgDisactiveChair();
    public static ImageView img02 = imgDisactiveChair();
    public static ImageView img04 = imgDisactiveChair();
    public static ImageView img06 = imgDisactiveChair();
    public static ImageView img08 = imgDisactiveChair();

    // image active chair
    public static ImageView img01 = imgActiveChair();
    public static ImageView img03 = imgActiveChair();
    public static ImageView img05 = imgActiveChair();
    public static ImageView img07 = imgActiveChair();
    public static ImageView img09 = imgActiveChair();

    public static int j = 0 ;
    public static int i = 0 ;

    static Button forCabins = new Button("Play");
    //Button forChairs = new Button("check Chair");

    public static int getI() {
        for (boolean k:X ) {
            if (k == true) Main.i++;
        }
        Main.gettingCabin = new Semaphore(Main.i);
        return Main.i;
    }

    public static int getJ() {
        for (boolean k:Y ) {
            if (k == true) Main.j++;
        }
       Main.gettingWaitingSeat = new Semaphore(Main.j);
        return Main.j;
    }

    public static int waiting = 0;
    public static int calling = 0;

    //public static int numberOfCabines = 4;
   // public static int numberOfChairs  = 5 ;

    public static final Point point1  = new Point(1450.0, 350);

    public static final Point point2  = new Point(1200.0, 350);

    public static final ArrayList<Point> cabinPoints = new ArrayList<>(
            Arrays.asList(
                new Point(70.0 , 610.0),
                new Point(260.0, 610.0),
                new Point(490.0, 610.0),
                new Point(720.0, 610.0),
                new Point(70.0 , 458.0),
                new Point(260.0, 458.0),
                new Point(490.0, 458.0),
                new Point(720.0, 458.0)
            )
    );

    public static final ArrayList<Point> waitPoints = new ArrayList<>(
            Arrays.asList(
                new Point(63.0 ,142),
                new Point(165.0,51),
                new Point(302.0,51),
                new Point(435.0,51),
                new Point(564.0,51)
            )
    );

    public static  ArrayList<Free> freeCabin = new ArrayList<>(
            Arrays.asList(
                    new Free(0,false),
                    new Free(1,false),
                    new Free(2,false),
                    new Free(3,false),
                    new Free(4,false),
                    new Free(5,false),
                    new Free(6,false),
                    new Free(7,false)
            )
    );

     public static final ArrayList<Free> freeWait = new ArrayList<>(
            Arrays.asList(
                    new Free(0,false),
                    new Free(1,false),
                    new Free(2,false),
                    new Free(3,false),
                    new Free(4,false)
            )
    );

    //////// hello    hadi ana zedtha bach ndir ta3 la chain .. chghol  mazal makhdemtha  ..
    public static final ArrayList<Point> checkin = new ArrayList<>(
            Arrays.asList(
                    new Point(1220,200),
                    new Point(1140,200),
                    new Point(1060,200),
                    new Point(1300,200),
                    new Point(1060,100),
                    new Point(1300,100)
            )
    );

    public static final ArrayList<Free> checkwait = new ArrayList<>(
            Arrays.asList(
                    new Free(0,true),
                    new Free(1,true),
                    new Free(2,true),
                    new Free(3,true),
                    new Free(4,true),
                    new Free(5,true)
            )
    );


    public static Semaphore gettingCabin;

    public static  Semaphore gettingWaitingSeat ;

    public static final Semaphore entree = new Semaphore(1);

    public static final Semaphore p1 = new Semaphore(1);

    public static final Semaphore p2 = new Semaphore(1);

    public static final Semaphore waitC = new Semaphore(1);
    public static final Semaphore callC = new Semaphore(1);

    public static final Semaphore checkout = new Semaphore(6);
    public static final Semaphore revenueS = new Semaphore(1);


     public static final Semaphore getFreeWaitingSeat = new Semaphore(1);

    public static final Semaphore getCabin = new Semaphore(1);


    public static Double revenueC = Double.valueOf(0);

    public static Label revenueLabel = new Label();

    public static AnchorPane root;


// FIN DECLARATION

// START METHOD START
    @Override
    public void start(Stage primaryStage) throws Exception{

        root = FXMLLoader.load(getClass().getResource("sample.fxml"));


        // buttons            //Controller.dada.setDisable(true);
        forCabins.setLayoutX(1010);
        forCabins.setLayoutY(52);
        forCabins.setPrefSize(85,56);
        forCabins.setFont(Font.font(20));

       forCabins.setStyle("-fx-background-color: linear-gradient(to bottom right, #5433fe ,#56ccf2)");

         forCabins.setOnAction(e->{

            Main.freeCabin.get(0).setFree(X[0]);
            Main.freeCabin.get(1).setFree(X[1]);
            Main.freeCabin.get(2).setFree(X[2]);
            Main.freeCabin.get(3).setFree(X[3]);
            Main.freeCabin.get(4).setFree(X[4]);
            Main.freeCabin.get(5).setFree(X[5]);
            Main.freeCabin.get(6).setFree(X[6]);
            Main.freeCabin.get(7).setFree(X[7]);

            Main.freeWait.get(0).setFree(Y[0]);
            Main.freeWait.get(1).setFree(Y[1]);

  	    Main.freeWait.get(2).setFree(Y[2]);
            Main.freeWait.get(3).setFree(Y[3]);
            Main.freeWait.get(4).setFree(Y[4]);


            Main.i = 0;
            Main.j = 0;
            /*getI();
            getJ();*/


 	    getI();
            getJ();
        });

       /* forChairs.setOnAction(e->{

        });
*/
        root.getChildren().addAll(forCabins);
        Main.revenueLabel.setLayoutX(1041);
        Main.revenueLabel.setLayoutY(14);
        Main.revenueLabel.setFont(Font.font(15));


        Main.revenueLabel.setText(String.valueOf(Main.revenueC));

        Platform.runLater(()-> Main.root.getChildren().add(Main.revenueLabel));

        primaryStage.setTitle("  PayPhone Simulation");

        primaryStage.getIcons().add(new Image("images/others/phone.gif"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
// FIN METHOD START
// START MAIN
    public static void main(String[] args) {
        System.out.println(" place 0  r: ");
        launch(args);

    }


// FIN MAIN

    public static ImageView imagActiveCabin(){

        Image image = new Image("images/others/activePhone.png");
        ImageView imageView = new ImageView(image);

        imageView.setPreserveRatio(true);

        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        int counter  = 199;
        for (int i = 0;i<counter;i++ ){
            System.out.print(counter+" ::debug:: "+imageView.getText)
        }

        return imageView;
    }
    public static ImageView imagDisactiveCabin(){

        Image image = new Image("images/others/disactivePhone.png");
        ImageView imageView = new ImageView(image);

        imageView.setPreserveRatio(true);

        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        return imageView;
    }
    public static ImageView imgDisactiveChair(){

        Image image = new Image("images/others/disactiveChair.png");
        ImageView imageView = new ImageView(image);

        imageView.setPreserveRatio(true);

        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        return imageView;
    }
    public static ImageView imgActiveChair(){

        Image image = new Image("images/others/activeChair.png");
        ImageView imageView = new ImageView(image);

        imageView.setPreserveRatio(true);

        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        return imageView;
    }
}

// START POINT
class Point{

    private double X, Y;

    public Point(double x, double y) {
        X = x;
        Y = y;
        System.out.println(" place M  1 r : ");
    }

    public Point() {
        X = 0.0;
        Y = 0.0;
    }

    public double getX() {
        System.out.println(" place M  10 r : ");
        return X;
    }
    public Point setX(double x) {
        System.out.println(" place M  6 : ");
        X = x;
        return this;
    }
    public double getY() {
        System.out.println(" place M  11 r: ");
        return Y;
    }
    public Point setY(double y) {
        System.out.println(" place M  8 : ");
        Y = y;
        return this;
    }

}
// FIN POINT

// START FREE
class Free{

    private int index;

    private boolean free;

    public Free(int i, boolean f){
         index = i;
        free = f;
    }

    public int getIndex() {
        return index;
    }

    public Free setIndex(int index) {
         this.index = index;
        return this;
    }

    public boolean isFree() {
         return free;
    }

    public Free setFree(boolean free) {
     this.free = free;
     return this;
    }
}
// FIN FREE