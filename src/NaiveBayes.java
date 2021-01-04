import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

public class NaiveBayes {
    private static final double INIT_GUESS=0.5;
    Table allWords=new Table();
    Table posWords=new Table();
    Table negWords=new Table();
    int posWordCount=0;
    int negWordCount=0;
    int goodMovies=0;
    int badMovies=0;

    public NaiveBayes() {
    }

    public void train() {
        File pos = new File("C:\\Users\\taso\\Desktop\\NaiveBayesClassifier\\assets\\train\\pos");
        File[] posDir = pos.listFiles();
        File neg=new File("C:\\Users\\taso\\Desktop\\NaiveBayesClassifier\\assets\\train\\neg");
        File[] negDir = neg.listFiles();

        Scanner scan;
        String str;
        int i=0;
        int j=0;

        for (File file : posDir) {
            try {

  //              System.out.println(file.getAbsolutePath());
                scan = new Scanner(file);
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
                                if(!negWords.words.containsKey(str)){
                                    negWords.add(str);
                                    negWordCount++;
                                }
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

        for (File file : negDir) {
            try {

                //              System.out.println(file.getAbsolutePath());
                scan = new Scanner(file);
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
                                if(!posWords.words.containsKey(str)){
                                    posWords.add(str);
                                    posWordCount++;
                                }
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


        Map<String, Integer> hm1 =sortByValue(posWords.words);
        Map<String, Integer> hm2 =sortByValue(negWords.words);



    }

    public void evaluate(){
        int i=0;
        File pos = new File("C:\\Users\\taso\\Desktop\\NaiveBayesClassifier\\assets\\test\\pos");
        File[] posDir = pos.listFiles();
        Scanner scan;
        String str;
        BigDecimal good=BigDecimal.valueOf(1);
        BigDecimal bad=BigDecimal.valueOf(1);

        for(File file:posDir){
            ArrayList <Double> probabilitiesGood=new ArrayList<Double>();
            ArrayList <Double> probabilitiesBad=new ArrayList<Double>();
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
                    Double prob=(double)posWords.getCount(str)/posWordCount;
                    if(prob!=0.0){
                    probabilitiesGood.add(prob);}
                    else{
                        probabilitiesGood.add(0.0002);
                    }

                    prob=(double)negWords.getCount(str)/negWordCount;
                    if(prob!=0.0){
                        probabilitiesBad.add(prob);}
                    else{
                        probabilitiesBad.add(0.0002);
                    }





                }while(scan.hasNext());
                for(Double d:probabilitiesGood){
                    good=good.multiply(BigDecimal.valueOf(d));
                }
                for(Double d:probabilitiesBad){
                    bad=bad.multiply(BigDecimal.valueOf(d));
                }

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

        System.out.println("good movies:"+goodMovies);
        System.out.println("bad movies:"+badMovies);




    }

    public void evaluate2(){
        int i=0;
        File pos = new File("C:\\Users\\taso\\Desktop\\NaiveBayesClassifier\\assets\\test\\neg");
        File[] posDir = pos.listFiles();
        Scanner scan;
        String str;


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
                    Double prob=(double)posWords.getCount(str)/posWordCount;
                    if(prob!=0.0){
                        good=good.multiply(BigDecimal.valueOf(prob));
                    }
                    else{
                        good=good.multiply(BigDecimal.valueOf(0.0002));
                    }

                    prob=(double)negWords.getCount(str)/negWordCount;
                    if(prob!=0.0){
                        bad=bad.multiply(BigDecimal.valueOf(prob));
                    }
                    else{
                        bad=bad.multiply(BigDecimal.valueOf(0.0002));
                    }




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

        System.out.println("good movies:"+goodMovies);
        System.out.println("bad movies:"+badMovies);




    }





    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }




    public static void main(String [] args){

        NaiveBayes a=new NaiveBayes();
        a.train();
        a.evaluate2();
   }




}
