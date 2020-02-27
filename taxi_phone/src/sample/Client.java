package sample;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.*;
import javafx.util.Duration;
import ring.*;

import java.util.Random;
import java.util.concurrent.Semaphore;


public class Client implements Runnable {


    private final  int callDuration ;
    public Client(int callDuration ) {
        System.out.println(" place Cl 8 r: " + callDuration);
        this.callDuration = callDuration * 1_000;
    }

    public static int circleTime;

    @Override
    public void run() {

        System.out.println(" place Cl 9 r: ");
        try{


            System.out.println("callDuration " + callDuration );

            Semaphore s = new Semaphore(0);

            System.out.println("Hello my name is " + Thread.currentThread().getName());



            Main.entree.acquire();

            Main.p1.acquire();

            ImageView client = imgClient();

            client.setLayoutX(Main.point1.getX());
            client.setLayoutY(Main.point1.getY());

            Platform.runLater(() -> {
                System.out.println(" place new 2 : ");
                Main.root.getChildren().add(client);
            });

            System.out.println(" place new 1 : ");
            PathTransition pathTransition = new PathTransition();
            pathTransition.setDuration(Duration.seconds(2.4));
            pathTransition.setNode(client);


            Path path = new Path();

            Main.p2.acquire();

            System.out.println(" place new 3 : ");
            path.getElements().add(new MoveTo(
                    0,
                    0
            ));
            System.out.println(" place new 4 : ");
            path.getElements().add(new QuadCurveTo(
                    Main.point2.getX() - Main.point1.getX(),
                    Main.point1.getY() - Main.point1.getY(),
                    Main.point2.getX() - Main.point1.getX(),
                    Main.point2.getY() - Main.point1.getY()
            ));
            System.out.println(" place new 5 : ");
            pathTransition.setPath(path);

            PathTransition finalPathTransition1 = pathTransition;
            finalPathTransition1.setOnFinished(event -> s.release());
            /******* hello create image or add image .. manich 3aref +++  w declration ta3 hadik CIRCLE_PROJECT  LOL  *******/
            ImageView image = new ImageView("images/others/phone.gif");

            image.setFitWidth(30);
            image.setFitHeight(30);

            RingProgressIndicator rg = new RingProgressIndicator();
            rg.makeIndeterminate();
            rg.setRingWidth(50);

            Platform.runLater(() -> finalPathTransition1.play() );
            s.acquire();
            System.out.println(Thread.currentThread().getName() + " played path5");
            System.out.println("////////////////////////////////////////////////////////////////////////////////////////////");

            Main.p1.release();


            Point waitPoint = null;
            int waitPlace = -1;
            int checkPlace = -1;
            Point cabinPoint = null;
            int freeCabinIndex = -1;


            Main.waitC.acquire();

            if (Main.waiting >= Main.j)
            {

                Main.waitC.release();

                Main.gettingWaitingSeat.acquire();

                Main.waitC.acquire();
                Main.waiting++;
                Main.waitC.release();

                Main.getFreeWaitingSeat.acquire();

                //waitingSeatsPoints = Main.freeWait === hashmap
                //Coordinates.getListWaitingPoints().get(i) === waitPoints
                //waitingSeatsPoints.put(Coordinates.getListWaitingPoints().get(i), false) = Main.freeWait === hashmap
                //waitingSeatsPoints.get(Coordinates.getListWaitingPoints().get(i))
                //Coordinates.getListWaitingPoints().get(i)

                for (int i = 0; i < Main.freeWait.size(); i++) {
                    if ( Main.freeWait.get(i).isFree() ) {
                        waitPlace = i;
                        waitPoint = Main.waitPoints.get(i);
                        Main.freeWait.get(i).setFree(false);
                        break;
                    }
                }
                Main.getFreeWaitingSeat.release();

                if ( Main.p2 != null && waitPoint != null) {


                    pathTransition = new PathTransition();
                    pathTransition.setDuration(Duration.seconds(2.4));
                    pathTransition.setNode(client);
                    path = new Path();

                    path.getElements().add(new MoveTo(
                            Main.point2.getX() - Main.point1.getX(),
                            Main.point2.getY() - Main.point1.getY()
                    ));
                    path.getElements().add(new QuadCurveTo(
                            waitPoint.getX() - Main.point1.getX(),
                            Main.point2.getY() - Main.point1.getY(),
                            waitPoint.getX() - Main.point1.getX(),
                            waitPoint.getY() - Main.point1.getY()
                    ));

                    pathTransition.setPath(path);

                }

                PathTransition finalPathTransition2 = pathTransition;
                finalPathTransition2.setOnFinished(event -> {
                    s.release();
                });
                Platform.runLater(() -> {
                    finalPathTransition2.play();
                });
                s.acquire();


                Main.p2.release();

                Main.entree.release();

                Main.gettingCabin.acquire();

                Main.getCabin.acquire();


                for (int i = 0; i < Main.freeCabin.size(); i++) {
                    if (Main.freeCabin.get(i).isFree()) {
                        cabinPoint = Main.cabinPoints.get(i);
                        Main.freeCabin.get(i).setFree(false);
                        freeCabinIndex = i;
                        break;
                    }
                }

                Main.getCabin.release();

                if (cabinPoint != null && waitPoint != null) {
                    pathTransition = new PathTransition();
                    pathTransition.setDuration(Duration.seconds(2.4));
                    pathTransition.setNode(client);
                    path = new Path();

                    path.getElements().add(new MoveTo(
                            waitPoint.getX() - Main.point1.getX(),
                            waitPoint.getY() - Main.point1.getY()
                    ));
                    path.getElements().add(new QuadCurveTo(
                            cabinPoint.getX() - Main.point1.getX(),
                            waitPoint.getY() - Main.point1.getY(),
                            cabinPoint.getX() - Main.point1.getX(),
                            cabinPoint.getY() - Main.point1.getY()
                    ));

                    pathTransition.setPath(path);

                }

                PathTransition finalPathTransition3 = pathTransition;
                finalPathTransition3.setOnFinished(event -> {
                    s.release();
                });
                Platform.runLater(() -> {
                    finalPathTransition3.play();
                    System.out.println("path5 " + Thread.currentThread().getName());
                    System.out.println("Alive: " + Thread.currentThread().getName() + " : " + Thread.currentThread().isAlive());
                });
                s.acquire();
                System.out.println(Thread.currentThread().getName() + " played path5");

                Main.callC.acquire();
                Main.calling++;
                Main.callC.release();

                Main.getFreeWaitingSeat.acquire();
                Main.freeWait.get(waitPlace).setFree(true);
                Main.getFreeWaitingSeat.release();

                Main.waitC.acquire();
                Main.waiting--;
                Main.waitC.release();
                Main.gettingWaitingSeat.release();
            }
            else
            {

                if (Main.waiting == 0)
                {

                    Main.callC.acquire();

                    if (Main.calling >= Main.i)

                    {

                        Main.callC.release();

                        Main.gettingWaitingSeat.acquire();

                        Main.waiting++;
                        Main.waitC.release();

                        Main.getFreeWaitingSeat.acquire();



                        for (int i = 0; i < Main.freeWait.size(); i++) {
                            if ( Main.freeWait.get(i).isFree() ) {
                                waitPlace = i;
                                waitPoint = Main.waitPoints.get(i);
                                Main.freeWait.get(i).setFree(false);
                                break;
                            }
                        }

                        Main.getFreeWaitingSeat.release();

                        if ( Main.p2 != null && waitPoint != null) {

                            pathTransition = new PathTransition();
                            pathTransition.setDuration(Duration.seconds(2.4));
                            pathTransition.setNode(client);
                            path = new Path();

                            path.getElements().add(new MoveTo(
                                    Main.point2.getX() - Main.point1.getX(),
                                    Main.point2.getY() - Main.point1.getY()
                            ));
                            path.getElements().add(new QuadCurveTo(
                                    waitPoint.getX() - Main.point1.getX(),
                                    Main.point2.getY() - Main.point1.getY(),
                                    waitPoint.getX() - Main.point1.getX(),
                                    waitPoint.getY() - Main.point1.getY()
                            ));

                            pathTransition.setPath(path);

                        }

                        PathTransition finalPathTransition4 = pathTransition;
                        finalPathTransition4.setOnFinished(event -> {
                            s.release();
                        });
                        Platform.runLater(() -> {
                            finalPathTransition4.play();

                        });
                        s.acquire();
                        System.out.println(Thread.currentThread().getName() + " played path5");

                        Main.p2.release();

                        Main.entree.release();

                        Main.gettingCabin.acquire();

                        Main.getCabin.acquire();

                        for (int i = 0; i < Main.freeCabin.size(); i++) {
                            if (Main.freeCabin.get(i).isFree()) {
                                cabinPoint = Main.cabinPoints.get(i);
                                Main.freeCabin.get(i).setFree(false);
                                freeCabinIndex = i;
                                break;
                            }
                        }

                        Main.getCabin.release();

                        if (cabinPoint != null && waitPoint != null) {


                            pathTransition = new PathTransition();
                            pathTransition.setDuration(Duration.seconds(2.4));
                            pathTransition.setNode(client);
                            path = new Path();

                            path.getElements().add(new MoveTo(
                                    waitPoint.getX() - Main.point1.getX(),
                                    waitPoint.getY() - Main.point1.getY()
                            ));
                            path.getElements().add(new QuadCurveTo(
                                    cabinPoint.getX() - Main.point1.getX(),
                                    waitPoint.getY() - Main.point1.getY(),
                                    cabinPoint.getX() - Main.point1.getX(),
                                    cabinPoint.getY() - Main.point1.getY()
                            ));

                            pathTransition.setPath(path);

                        }

                        PathTransition finalPathTransition5 = pathTransition;
                        finalPathTransition5.setOnFinished(event -> {
                            s.release();
                        });
                        Platform.runLater(() -> {
                            finalPathTransition5.play();
                        });
                        s.acquire();

                        Main.callC.acquire();
                        Main.calling++;
                        Main.callC.release();

                        Main.getFreeWaitingSeat.acquire();
                        Main.freeWait.get(waitPlace).setFree(true);
                        Main.getFreeWaitingSeat.release();

                        Main.waitC.acquire();
                        Main.waiting--;
                        Main.waitC.release();

                        Main.gettingWaitingSeat.release();

                    }

                    else
                    {

                        Main.gettingCabin.acquire();

                        Main.getCabin.acquire();
                        for (int i = 0; i < Main.freeCabin.size(); i++) {
                            if (Main.freeCabin.get(i).isFree()) {
                                cabinPoint = Main.cabinPoints.get(i);
                                Main.freeCabin.get(i).setFree(false);
                                freeCabinIndex = i;
                                break;
                            }
                        }


                        Main.getCabin.release();

                        if (cabinPoint != null && Main.point2 != null) {

                            pathTransition = new PathTransition();
                            pathTransition.setDuration(Duration.seconds(2.4));
                            pathTransition.setNode(client);
                            path = new Path();

                            path.getElements().add(new MoveTo(
                                    Main.point2.getX() - Main.point1.getX(),
                                    Main.point2.getY() - Main.point1.getY()
                            ));
                            path.getElements().add(new QuadCurveTo(
                                    cabinPoint.getX() - Main.point1.getX(),
                                    Main.point2.getY() - Main.point1.getY(),
                                    cabinPoint.getX() - Main.point1.getX(),
                                    cabinPoint.getY() - Main.point1.getY()
                            ));

                            pathTransition.setPath(path);

                        }

                        PathTransition finalPathTransition6 = pathTransition;
                        finalPathTransition6.setOnFinished(event -> {
                            s.release();
                        });
                        Platform.runLater(() -> {

                            finalPathTransition6.play();
                        });
                        s.acquire();

                        Main.p2.release();

                        Main.entree.release();

                        Main.calling++;

                        Main.callC.release();

                        Main.waitC.release();

                    }
                }
                else
                {


                    Main.gettingWaitingSeat.acquire();

                    Main.waiting++;
                    Main.waitC.release();

                    Main.getFreeWaitingSeat.acquire();
                    for (int i = 0; i < Main.freeWait.size(); i++) {
                        if ( Main.freeWait.get(i).isFree() ) {
                            waitPlace = i;
                            waitPoint = Main.waitPoints.get(i);
                            Main.freeWait.get(i).setFree(false);
                            break;
                        }
                    }
                    Main.getFreeWaitingSeat.release();

                    if ( Main.p2 != null && waitPoint != null ) {
                        //p2
                        //waitPoint

                        //translateTransition.setFromX(Coordinates.getDeployPoint().getX() - START_X);
                        //translateTransition.setFromY(Coordinates.getDeployPoint().getY() - START_Y);
                        //translateTransition.setToX(waitPoint.getX() - START_X);
                        //translateTransition.setToY(waitPoint.getY() - START_Y);

                        pathTransition = new PathTransition();
                        pathTransition.setDuration(Duration.seconds(2.4));
                        pathTransition.setNode(client);
                        path = new Path();

                        path.getElements().add(new MoveTo(
                                Main.point2.getX() - Main.point1.getX(),
                                Main.point2.getY() - Main.point1.getY()
                        ));
                        path.getElements().add(new QuadCurveTo(
                                waitPoint.getX() - Main.point1.getX(),
                                Main.point2.getY() - Main.point1.getY(),
                                waitPoint.getX() - Main.point1.getX(),
                                waitPoint.getY() - Main.point1.getY()
                        ));

                        pathTransition.setPath(path);

                    }

                    PathTransition finalPathTransition7 = pathTransition;
                    finalPathTransition7.setOnFinished(event -> {
                        s.release();
                    });
                    Platform.runLater(() -> {
                        finalPathTransition7.play();
                    });
                    s.acquire();

                    Main.p2.release();

                    Main.entree.release();

                    Main.gettingCabin.acquire();

                    Main.getCabin.acquire();

//                for (int i = 0; i < EntryToPhoneCabinPoints.size(); i++) {
//                    if (EntryToPhoneCabinPoints.get(Coordinates.getListEntryToPhoneCabinPoints().get(i))) {
//                        freePhoneCabin = Coordinates.getListEntryToPhoneCabinPoints().get(i);
//                        EntryToPhoneCabinPoints.put(Coordinates.getListEntryToPhoneCabinPoints().get(i), false);
//                        freePhoneCabinIndex = i;
//                        break;
//                    }
//                }
//
                    //            EntryToPhoneCabinPoints == hashmap  == freeCabin
                    //           EntryToPhoneCabinPoints.get(Coordinates.getListEntryToPhoneCabinPoints().get(i)) ==
                    //
                    //
                    //
                    //
                    //

                    for (int i = 0; i < Main.freeCabin.size(); i++) {
                        if (Main.freeCabin.get(i).isFree()) {
                            cabinPoint = Main.cabinPoints.get(i);
                            Main.freeCabin.get(i).setFree(false);
                            freeCabinIndex = i;
                            break;
                        }
                    }

                    Main.getCabin.release();

                    if (cabinPoint != null && waitPoint != null) {

                        pathTransition = new PathTransition();
                        pathTransition.setDuration(Duration.seconds(2.4));
                        pathTransition.setNode(client);
                        path = new Path();

                        path.getElements().add(new MoveTo(
                                waitPoint.getX() - Main.point1.getX(),
                                waitPoint.getY() - Main.point1.getY()
                        ));
                        path.getElements().add(new QuadCurveTo(
                                cabinPoint.getX() - Main.point1.getX(),
                                waitPoint.getY() - Main.point1.getY(),
                                cabinPoint.getX() - Main.point1.getX(),
                                cabinPoint.getY() - Main.point1.getY()
                        ));
                        pathTransition.setPath(path);
                    }

                    PathTransition finalPathTransition8 = pathTransition;
                    finalPathTransition8.setOnFinished(event -> {
                        s.release();
                    });
                    Platform.runLater(() -> {
                        finalPathTransition8.play();
                    });
                    s.acquire();


                    Main.callC.acquire();
                    Main.calling++;
                    Main.callC.release();

                    Main.getFreeWaitingSeat.acquire();
                    Main.freeWait.get(waitPlace).setFree(true);
                    Main.getFreeWaitingSeat.release();

                    Main.waitC.acquire();
                    Main.waiting--;
                    Main.waitC.release();


                    Main.gettingWaitingSeat.release();



                }
            }
            ///////////////////// hello     hadi tzid image ta3 telephone and circle   kayna wahda techbehelha l fo9
            System.out.println("00000000000000000000000000000000 i m out 0000000000000000000000000 ");
            image.setLayoutX(cabinPoint.getX()+25);
            image.setLayoutY(cabinPoint.getY()-74);
            rg.setLayoutX(cabinPoint.getX()+55);
            rg.setLayoutY(cabinPoint.getY()-82);

            circleTime = callDuration/1000;

            new work(rg,callDuration/1000).start();
            Platform.runLater(()-> {
                        Main.root.getChildren().add(rg);
                        Main.root.getChildren().add(image);
                    }
            );
            Thread.sleep(callDuration);

            Point pp = new Point();
            for (int i = 0; i < Main.checkwait.size(); i++) {
                if ( Main.checkwait.get(i).isFree() ) {
                    checkPlace = i;
                    pp = Main.checkin.get(i);
                    Main.checkwait.get(i).setFree(false);
                    break;
                }
            }

            pathTransition = new PathTransition();
            pathTransition.setDuration(Duration.seconds(2.4));
            pathTransition.setNode(client);
            path = new Path();

            path.getElements().add(new MoveTo(
                    cabinPoint.getX() - Main.point1.getX(),
                    cabinPoint.getY() - Main.point1.getY()
            ));
            path.getElements().add(new QuadCurveTo(
                    cabinPoint.getX() - Main.point1.getX(),
                    pp.getY() - Main.point1.getY(),
                    pp.getX() - Main.point1.getX(),
                    pp.getY() - Main.point1.getY()
            ));

            pathTransition.setPath(path);

            Main.checkout.acquire();

            PathTransition finalPathTransition9 = pathTransition;
            finalPathTransition9.setOnFinished(event -> {
                s.release();
            });
            /////////////////////////// hello  hadi tem7i l image ta3 telephone
            Platform.runLater(()->{
                Main.root.getChildren().remove(image);
                Main.root.getChildren().remove(rg);
            });

            ////////// fin
            Platform.runLater(() -> { finalPathTransition9.play();  });
            s.acquire();

            Main.getCabin.acquire();
            Main.freeCabin.get(freeCabinIndex).setFree(true);

            Main.getCabin.release();

            Main.callC.acquire();
            Main.calling--;
            Main.callC.release();

            Main.gettingCabin.release();



            Thread.sleep(9000);

            ////////// hello hadi hiya l affichage

            Main.revenueS.acquire();
            Main.revenueC += (callDuration * 0.0005);
            System.out.println("revenue : " + Main.revenueC + " DZA ");

            ///  Main.revenueLabel.setLayoutX(1100);
            ///  Main.revenueLabel.setLayoutY(30);


            Platform.runLater(() -> {
                Main.revenueLabel.setText(String.valueOf(Main.revenueC));
                Main.root.getChildren().remove(Main.revenueLabel);
                Main.root.getChildren().add(Main.revenueLabel);

            });


            /**********   END   affichage  ******/

/////// l kharja */   new new new


            pathTransition = new PathTransition();
            pathTransition.setDuration(Duration.seconds(2.5));
            pathTransition.setNode(client);
            path = new Path();

            path.getElements().add(new MoveTo(
                    pp.getX() -Main.point1.getX(),
                    pp.getY() - Main.point1.getY()
            ));
            path.getElements().add(new QuadCurveTo(
                    pp.getX() - Main.point2.getX(),
                    Main.point1.getY() - Main.point2.getY(),
                    Main.point1.getX() - Main.point2.getX(),
                    Main.point1.getY() - Main.point2.getY()
            ));

            pathTransition.setPath(path);

            PathTransition finalPathTransition10 = pathTransition;

            finalPathTransition10.setOnFinished(event -> { s.release();  });

            Platform.runLater(() -> {  finalPathTransition10.play(); });
            // yehkem l bab ta3 l kharja
            Main.p1.acquire();
            s.acquire();

            // bach yekhrej mn l bab

            Main.p1.release();

            // hna tekmel lkharja

            // bach ykhali lokhrin ykhalso
            Main.revenueS.release();



            // bach yna9as wahda mn check out
            Main.checkout.release();


            Main.checkwait.get(checkPlace).setFree(true);

            //// hadi bach tetem7a taswira ki tkemel khedmetha
            Platform.runLater(() -> {  Main.root.getChildren().remove(client); });

///// fin new new new



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ImageView imgClient(){

       /* int d = new Random().nextInt(10);
        String string = new String();
        string = "images/clients/client"+ Integer.toString(d)+".png ";
        Image image = new Image(string);
        ImageView imageView = new ImageView(image);*/
        ImageView imageView = new ImageView(new Image("images/clients/client"+Integer.toString(new Random().nextInt(6))+".png "));



        imageView.setPreserveRatio(true);

        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        return imageView;
    }

    ////////// class work li rani dayro 3la jal ta3 time
    class work extends Thread{

        RingProgressIndicator r ;
        int progress ;

        public work(RingProgressIndicator r, int p){
            this.r = r ;
            this.progress = p+1;
        }
        public void run (){

            while(true){
                ///// hello ... for laughing ...  hadik 1   w   2   w  3  9lebthom bark  ... t7al hadak l mochkil li 9otlk 3lih
                Platform.runLater(()-> r.setProgress(progress) );   /// 2

                progress-=1;                                        /// 3
                if(progress <= 0)                                   /// 3
                    break;                                          /// 3

                try {                                               /// 1
                    Thread.sleep(1000);                          /// 1
                }catch (Exception e){                               /// 1
                }                                                   /// 1

            }
        }
    }
}