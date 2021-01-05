import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

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


    public NaiveBayes(double smoothingFactor,int wordsToCheck,int wordsToSkip,int trainSize) {
        this.smoothingFactor=smoothingFactor;
        this.wordsToCheck=wordsToCheck;
        this.wordsToSkip=wordsToSkip;
        this.trainSize=trainSize;

    }

    public void train() {
        File pos = new File("C:\\Users\\taso\\Desktop\\NaiveBayesClassifier\\assets\\train\\pos");
        File[] posDir = pos.listFiles();
        File neg=new File("C:\\Users\\taso\\Desktop\\NaiveBayesClassifier\\assets\\train\\neg");
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
        File pos = new File("C:\\Users\\taso\\Desktop\\NaiveBayesClassifier\\assets\\train\\pos");
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
                }else if(good.compareTo(bad)==-1){
                    //  System.out.println("bad movie");
                    badMovies++;
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
        File pos = new File("C:\\Users\\taso\\Desktop\\NaiveBayesClassifier\\assets\\train\\neg");
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
                }else if(good.compareTo(bad)==-1){
                    //  System.out.println("bad movie");
                    badMovies++;
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
        File pos = new File("C:\\Users\\taso\\Desktop\\NaiveBayesClassifier\\assets\\test\\pos");
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
                }else if(good.compareTo(bad)==-1){
                    //  System.out.println("bad movie");
                    badMovies++;
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
        File pos = new File("C:\\Users\\taso\\Desktop\\NaiveBayesClassifier\\assets\\test\\neg");
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
                }else if(good.compareTo(bad)==-1){
                    //  System.out.println("bad movie");
                    badMovies++;
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

        NaiveBayes a=new NaiveBayes(0.02,70000,5,12500);
        a.train();
        a.evaluateTrainPos();
        a.evaluateTrainNeg();
        a.evaluateTestPos();
        a.evaluateTestNeg();

   }




}
