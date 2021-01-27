import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;
import org.math.plot.*;

import javax.swing.*;

public class NaiveBayes {
    private Table allWords=new Table();
    private Table posWords=new Table();
    private Table negWords=new Table();
    private int posWordCount=0;
    private int negWordCount=0;
    private int goodMovies;
    private int badMovies;
    private int trainSize;
    private int wordsToSkip;
    private int wordsToCheck;
    private double prob;
    private double smoothingFactor;

    private int truePositives=0;
    private int falsePositives=0;
    private int trueNegatives=0;
    private int falseNegatives=0;

    public int getTruePositives() {
        return truePositives;
    }

    public void setTruePositives(int truePositives) {
        this.truePositives = truePositives;
    }

    public int getFalsePositives() {
        return falsePositives;
    }

    public void setFalsePositives(int falsePositives) {
        this.falsePositives = falsePositives;
    }

    public int getTrueNegatives() {
        return trueNegatives;
    }

    public void setTrueNegatives(int trueNegatives) {
        this.trueNegatives = trueNegatives;
    }

    public int getFalseNegatives() {
        return falseNegatives;
    }

    public void setFalseNegatives(int falseNegatives) {
        this.falseNegatives = falseNegatives;
    }





    public NaiveBayes(double smoothingFactor,int wordsToCheck,int wordsToSkip,int trainSize) {
        this.smoothingFactor=smoothingFactor;
        this.wordsToCheck=wordsToCheck;
        this.wordsToSkip=wordsToSkip;
        this.trainSize=trainSize;

    }

    public void train() {
        File pos = new File("/home/tasos/Desktop/imbd/train/pos");
        File[] posDir = pos.listFiles();
        File neg=new File("/home/tasos/Desktop/imbd/train/neg");
        File[] negDir = neg.listFiles();

        Scanner scan;
        String str;
        int i=0;


        for (int h=0;h<trainSize;h++) {
            try {
                scan = new Scanner(posDir[h]);
                do {
                    str = scan.next();
                    str=str.toLowerCase();
                    str = str.replace(".", "");
                    str = str.replace("\"", "");
                    str = str.replace(",", "");
                    str = str.replace("!", "");
                    str = str.replace("(", "");
                    str = str.replace(")", "");
                    str = str.replace(";", "");
                    str = str.replace(":", "");
                    str = str.replace("<", "");
                    str = str.replace(">", "");
                    str = str.replace("?", "");
                    str = str.replace("%", "");
                    str = str.replace("/", "");
                    str = str.replace("&", "");
                    str = str.replace(" ", "");
                    str = str.replace("+", "");
                    str = str.replace("#", "");
                    str = str.replace("*", "");
                    str = str.replace("'", "");
                    if(str.endsWith("-")){
                        str = str.replace("-", "");
                    }


                    if(str!="")
                    {
                        if(str!=" ") {
                            if(str!="-"){
                                posWords.add(str);
                                posWordCount++;

                            }
                        }

                    }





                } while (scan.hasNext());
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            }
            if(i==1000){
                System.out.print("#");
                i=-1;
            }
            i++;


       }

        for (int g=0;g<trainSize;g++) {
            try {
                scan = new Scanner(negDir[g]);
                do {
                    str = scan.next();
                    str=str.toLowerCase();
                    str = str.replace(".", "");
                    str = str.replace("\"", "");
                    str = str.replace(",", "");
                    str = str.replace("!", "");
                    str = str.replace("(", "");
                    str = str.replace(")", "");
                    str = str.replace(";", "");
                    str = str.replace(":", "");
                    str = str.replace("<", "");
                    str = str.replace(">", "");
                    str = str.replace("?", "");
                    str = str.replace("%", "");
                    str = str.replace("/", "");
                    str = str.replace("&", "");
                    str = str.replace(" ", "");
                    str = str.replace("+", "");
                    str = str.replace("#", "");
                    str = str.replace("*", "");
                    str = str.replace("'", "");
                    if(str.endsWith("-")){
                        str = str.replace("-", "");
                    }


                    if(str!="")
                    {
                        if(str!=" ") {
                            if(str!="-"){
                                negWords.add(str);
                                negWordCount++;

                            }
                        }

                    }





                } while (scan.hasNext());
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            }
            if(i==1000){
                System.out.print("#");
                i=-1;
            }
            i++;


        }



        posWords.setMap(posWords.sortByValue(posWords.getMap()));
        negWords.setMap(negWords.sortByValue(negWords.getMap()));
        posWords.cutWords(wordsToSkip,wordsToCheck);
        negWords.cutWords(wordsToSkip,wordsToCheck);
        System.out.println();
        System.out.println("Training complete");




    }

    public void evaluateTrainPos(){
        int i=0;
        File pos = new File("/home/tasos/Desktop/imbd/train/pos");
        File[] posDir = pos.listFiles();
        Scanner scan;
        String str;
        badMovies=0;
        goodMovies=0;


        for(int h=0;h<trainSize;h++){
            ArrayList <Double> probabilitiesGood=new ArrayList<Double>();
            ArrayList <Double> probabilitiesBad=new ArrayList<Double>();
            BigDecimal good=BigDecimal.valueOf(1);
            BigDecimal bad=BigDecimal.valueOf(1);
            try {
                scan=new Scanner(posDir[h]);
                do{
                    str=scan.next();
                    str=str.toLowerCase();
                    str = str.replace(".", "");
                    str = str.replace("\"", "");
                    str = str.replace(",", "");
                    str = str.replace("!", "");
                    str = str.replace("(", "");
                    str = str.replace(")", "");
                    str = str.replace(";", "");
                    str = str.replace(":", "");
                    str = str.replace("<", "");
                    str = str.replace(">", "");
                    str = str.replace("?", "");
                    str = str.replace("%", "");
                    str = str.replace("/", "");
                    str = str.replace("&", "");
                    str = str.replace(" ", "");
                    str = str.replace("+", "");
                    str = str.replace("#", "");
                    str = str.replace("*", "");
                    str = str.replace("'", "");
                    if(str.endsWith("-")){
                        str = str.replace("-", "");
                    }
                    prob=(double)(posWords.getCount(str)+smoothingFactor)/(trainSize+smoothingFactor*posWords.getSize());
                    good=good.multiply(BigDecimal.valueOf(prob));
                    prob=(double)(negWords.getCount(str)+smoothingFactor)/(trainSize+smoothingFactor*negWords.getSize());
                    bad=bad.multiply(BigDecimal.valueOf(prob));





                }while(scan.hasNext());


                // System.out.println(good+" "+bad);
                if(good.compareTo(bad)==1){
                    // System.out.println("good movie");
                    goodMovies++;
                    truePositives++;
                }else if(good.compareTo(bad)==-1){
                    //  System.out.println("bad movie");
                    badMovies++;
                    falseNegatives++;
                }




            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if(i==1000){
                System.out.print("#");
                i=-1;
            }
            i++;


        }
        System.out.println();
        System.out.println("Evaluation of positive review train dataset");
        System.out.println("good reviews:"+goodMovies);
        System.out.println("bad reviews:"+badMovies);
         double accuracy=((double)goodMovies/(double)trainSize)*100;
        System.out.println(accuracy+"% accuracy");






    }

    public void evaluateTrainNeg(){
        int i=0;
        File pos = new File("/home/tasos/Desktop/imbd/train/neg");
        File[] posDir = pos.listFiles();
        Scanner scan;
        String str;
        badMovies=0;
        goodMovies=0;


        for(int h=0;h<trainSize;h++){
            ArrayList <Double> probabilitiesGood=new ArrayList<Double>();
            ArrayList <Double> probabilitiesBad=new ArrayList<Double>();
            BigDecimal good=BigDecimal.valueOf(1);
            BigDecimal bad=BigDecimal.valueOf(1);
            try {
                scan=new Scanner(posDir[h]);
                do{
                    str=scan.next();
                    str=str.toLowerCase();
                    str = str.replace(".", "");
                    str = str.replace("\"", "");
                    str = str.replace(",", "");
                    str = str.replace("!", "");
                    str = str.replace("(", "");
                    str = str.replace(")", "");
                    str = str.replace(";", "");
                    str = str.replace(":", "");
                    str = str.replace("<", "");
                    str = str.replace(">", "");
                    str = str.replace("?", "");
                    str = str.replace("%", "");
                    str = str.replace("/", "");
                    str = str.replace("&", "");
                    str = str.replace(" ", "");
                    str = str.replace("+", "");
                    str = str.replace("#", "");
                    str = str.replace("*", "");
                    str = str.replace("'", "");
                    if(str.endsWith("-")){
                        str = str.replace("-", "");
                    }
                    prob=(double)(posWords.getCount(str)+smoothingFactor)/(trainSize+smoothingFactor*posWords.getSize());
                    good=good.multiply(BigDecimal.valueOf(prob));
                    prob=(double)(negWords.getCount(str)+smoothingFactor)/(trainSize+smoothingFactor* negWords.getSize());
                    bad=bad.multiply(BigDecimal.valueOf(prob));





                }while(scan.hasNext());


                // System.out.println(good+" "+bad);
                if(good.compareTo(bad)==1){
                    // System.out.println("good movie");
                    goodMovies++;
                    falsePositives++;
                }else if(good.compareTo(bad)==-1){
                    //  System.out.println("bad movie");
                    badMovies++;
                    trueNegatives++;
                }




            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if(i==1000){
                System.out.print("#");
                i=-1;
            }
            i++;


        }
        System.out.println();
        System.out.println("Evaluation of negative review train dataset");
        System.out.println("good reviews:"+goodMovies);
        System.out.println("bad reviews:"+badMovies);
         double accuracy=((double)badMovies/(double)trainSize)*100;
        System.out.println(accuracy+"% accuracy");




    }


    public void evaluateTestPos(){
        int i=0;
        File pos = new File("/home/tasos/Desktop/imbd/test/pos");
        File[] posDir = pos.listFiles();
        Scanner scan;
        String str;
        badMovies=0;
        goodMovies=0;


        for(File file:posDir){
            ArrayList <Double> probabilitiesGood=new ArrayList<Double>();
            ArrayList <Double> probabilitiesBad=new ArrayList<Double>();
            BigDecimal good=BigDecimal.valueOf(1);
            BigDecimal bad=BigDecimal.valueOf(1);
            try {
                scan=new Scanner(file);
                do{
                    str=scan.next();
                    str=str.toLowerCase();
                    str = str.replace(".", "");
                    str = str.replace("\"", "");
                    str = str.replace(",", "");
                    str = str.replace("!", "");
                    str = str.replace("(", "");
                    str = str.replace(")", "");
                    str = str.replace(";", "");
                    str = str.replace(":", "");
                    str = str.replace("<", "");
                    str = str.replace(">", "");
                    str = str.replace("?", "");
                    str = str.replace("%", "");
                    str = str.replace("/", "");
                    str = str.replace("&", "");
                    str = str.replace(" ", "");
                    str = str.replace("+", "");
                    str = str.replace("#", "");
                    str = str.replace("*", "");
                    str = str.replace("'", "");
                    if(str.endsWith("-")){
                        str = str.replace("-", "");
                    }
                    prob=(double)(posWords.getCount(str)+smoothingFactor)/(trainSize+smoothingFactor*posWords.getSize());
                    good=good.multiply(BigDecimal.valueOf(prob));
                    prob=(double)(negWords.getCount(str)+smoothingFactor)/(trainSize+smoothingFactor*negWords.getSize());
                    bad=bad.multiply(BigDecimal.valueOf(prob));





                }while(scan.hasNext());


                // System.out.println(good+" "+bad);
                if(good.compareTo(bad)==1){
                    // System.out.println("good movie");
                    goodMovies++;
                    truePositives++;
                }else if(good.compareTo(bad)==-1){
                    //  System.out.println("bad movie");
                    badMovies++;
                    falseNegatives++;
                }




            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if(i==1000){
                System.out.print("#");
                i=-1;
            }
            i++;


        }
        System.out.println();
        System.out.println("Evaluation of positive review test dataset");
        System.out.println("good reviews:"+goodMovies);
        System.out.println("bad reviews:"+badMovies);
         double accuracy=((double)goodMovies/(double)12500)*100;
        System.out.println(accuracy+"% accuracy");





    }

    public void evaluateTestNeg(){
        int i=0;
        File pos = new File("/home/tasos/Desktop/imbd/test/neg");
        File[] posDir = pos.listFiles();
        Scanner scan;
        String str;
        badMovies=0;
        goodMovies=0;


        for(File file:posDir){
            ArrayList <Double> probabilitiesGood=new ArrayList<Double>();
            ArrayList <Double> probabilitiesBad=new ArrayList<Double>();
            BigDecimal good=BigDecimal.valueOf(1);
            BigDecimal bad=BigDecimal.valueOf(1);
            try {
                scan=new Scanner(file);
                do{
                    str=scan.next();
                    str=str.toLowerCase();
                    str = str.replace(".", "");
                    str = str.replace("\"", "");
                    str = str.replace(",", "");
                    str = str.replace("!", "");
                    str = str.replace("(", "");
                    str = str.replace(")", "");
                    str = str.replace(";", "");
                    str = str.replace(":", "");
                    str = str.replace("<", "");
                    str = str.replace(">", "");
                    str = str.replace("?", "");
                    str = str.replace("%", "");
                    str = str.replace("/", "");
                    str = str.replace("&", "");
                    str = str.replace(" ", "");
                    str = str.replace("+", "");
                    str = str.replace("#", "");
                    str = str.replace("*", "");
                    str = str.replace("'", "");
                    if(str.endsWith("-")){
                        str = str.replace("-", "");
                    }
                    prob=(double)(posWords.getCount(str)+smoothingFactor)/(trainSize+smoothingFactor*posWords.getSize());
                        good=good.multiply(BigDecimal.valueOf(prob));
                    prob=(double)(negWords.getCount(str)+smoothingFactor)/(trainSize+smoothingFactor* negWords.getSize());
                        bad=bad.multiply(BigDecimal.valueOf(prob));





                }while(scan.hasNext());


                // System.out.println(good+" "+bad);
                if(good.compareTo(bad)==1){
                    // System.out.println("good movie");
                    goodMovies++;
                    falsePositives++;
                }else if(good.compareTo(bad)==-1){
                    //  System.out.println("bad movie");
                    badMovies++;
                    trueNegatives++;
                }




            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if(i==1000){
                System.out.print("#");
                i=-1;
            }
            i++;


        }
        System.out.println();
        System.out.println("Evaluation of negative review test dataset");
        System.out.println("good reviews:"+goodMovies);
        System.out.println("bad reviews:"+badMovies);
        double accuracy=((double)badMovies/(double)12500)*100;
        System.out.println(accuracy+"% accuracy");




    }












    public static void main(String [] args){

        int size;
        int reps;
        System.out.println("Enter number of repetitions");
        Scanner scan2=new Scanner(System.in);
        String input2=scan2.nextLine();


        /*Plot2DPanel plot=new Plot2DPanel();
        plot.addLinePlot("accuracy",x,y);
        JFrame frame=new JFrame("plot");
        frame.setSize(500, 500);
        frame.setContentPane(plot);
        frame.setVisible(true);*/



try {

    reps= Integer.parseInt(input2);
    double [] accuracyTest=new double[reps];
    double [] precisionTest=new double[reps];
    double [] recallTest=new double[reps];
    double [] f1Test=new double[reps];
    double [] accuracyTrain=new double[reps];
    double [] precisionTrain=new double[reps];
    double [] recallTrain=new double[reps];
    double [] f1Train=new double[reps];
    double [] y=new double[reps];
    double accuracy;
    double precision;
    double recall;
    double f1;
    for(int i=0;i<reps;i++) {
        System.out.println("Enter training size(max 12500)");
        Scanner scan=new Scanner(System.in);
        String input=scan.nextLine();
        size=Integer.parseInt(input);
        y[i]=size;
        NaiveBayes a = new NaiveBayes(0.02, size*6, 5, size);
        a.train();
        a.evaluateTrainPos();
        a.evaluateTrainNeg();
        accuracy=(double) (a.getTruePositives()+a.getTrueNegatives()) / (a.getTrueNegatives()+a.getTruePositives()+a.getFalseNegatives()+a.getFalsePositives());
        precision=(double) (a.getTruePositives())/(a.getTruePositives()+a.getFalsePositives());
        recall=(double) (a.getTruePositives())/(a.getTruePositives()+a.getFalseNegatives());
        f1=2*(precision*recall)/(precision+recall);
        accuracyTrain[i]=accuracy;
        precisionTrain[i]=precision;
        recallTrain[i]=recall;
        f1Train[i]=f1;
        a.setFalseNegatives(0);
        a.setFalsePositives(0);
        a.setTrueNegatives(0);
        a.setTruePositives(0);
        a.evaluateTestPos();
        a.evaluateTestNeg();
        accuracy=(double) (a.getTruePositives()+a.getTrueNegatives()) / (a.getTrueNegatives()+a.getTruePositives()+a.getFalseNegatives()+a.getFalsePositives());
        precision=(double) (a.getTruePositives())/(a.getTruePositives()+a.getFalsePositives());
        recall=(double) (a.getTruePositives())/(a.getTruePositives()+a.getFalseNegatives());
        f1=2*(precision*recall)/(precision+recall);
        accuracyTest[i]=accuracy;
        precisionTest[i]=precision;
        recallTest[i]=recall;
        f1Test[i]=f1;
    }

    Plot2DPanel plot=new Plot2DPanel();
    plot.addLinePlot("accuracy",y,accuracyTrain);
    plot.setAxisLabels("training size","accuracy");
    JFrame frame=new JFrame("accuracy for train data");
    frame.setSize(800, 800);
    frame.setContentPane(plot);frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    Plot2DPanel plot2=new Plot2DPanel();
    plot2.addLinePlot("precision",y,precisionTrain);
    plot2.setAxisLabels("training size","precision");
    JFrame frame2=new JFrame("precision for train data");
    frame2.setSize(800, 800);
    frame2.setContentPane(plot2);
    frame2.setVisible(true);
    frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    Plot2DPanel plot3=new Plot2DPanel();
    plot3.setAxisLabels("training size","recall");
    plot3.addLinePlot("recall",y,recallTrain);
    JFrame frame3=new JFrame("recall for train data");
    frame3.setSize(800, 800);
    frame3.setContentPane(plot3);
    frame3.setVisible(true);
    frame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    Plot2DPanel plot4=new Plot2DPanel();
    plot4.addLinePlot("f1",y,f1Train);
    plot4.setAxisLabels("training size","f1 score");
    JFrame frame4=new JFrame("f1 score for train data");
    frame4.setSize(800, 800);
    frame4.setContentPane(plot4);
    frame4.setVisible(true);
    frame4.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    Plot2DPanel plot5=new Plot2DPanel();
    plot5.addLinePlot("accuracy",y,accuracyTest);
    plot5.setAxisLabels("training size","accuracy");
    JFrame frame5=new JFrame("accuracy for test data");
    frame5.setSize(800, 800);
    frame5.setContentPane(plot5);
    frame5.setVisible(true);
    frame5.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    Plot2DPanel plot6=new Plot2DPanel();
    plot6.addLinePlot("precision",y,precisionTest);
    plot6.setAxisLabels("training size","precision");
    JFrame frame6=new JFrame("precision for test data");
    frame6.setSize(800, 800);
    frame6.setContentPane(plot6);
    frame6.setVisible(true);
    frame6.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    Plot2DPanel plot7=new Plot2DPanel();
    plot7.addLinePlot("recall",y,recallTest);
    plot7.setAxisLabels("training size","recall");
    JFrame frame7=new JFrame("recall for test data");
    frame7.setSize(800, 800);
    frame7.setContentPane(plot7);
    frame7.setVisible(true);
    frame7.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    Plot2DPanel plot8=new Plot2DPanel();
    plot8.addLinePlot("f1",y,accuracyTrain);
    plot8.setAxisLabels("training size","f1 score");
    JFrame frame8=new JFrame("f1 score for test data");
    frame8.setSize(800, 800);
    frame8.setContentPane(plot8);
    frame8.setVisible(true);
    frame8.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);



}catch (NumberFormatException e) {
    e.printStackTrace();
    System.out.println("invalid input");
}



   }




}
