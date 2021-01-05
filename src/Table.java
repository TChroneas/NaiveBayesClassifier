
import java.util.*;

public class Table {
     private LinkedHashMap<String,Integer> words=new LinkedHashMap<>();

     public void add(String word){
         if(!words.containsKey(word)){
             words.put(word,1);
         }else{
             words.put(word,words.get(word)+1);
         }
     }

     public int getCount(String word){
         if(!words.containsKey(word)){
             return 0;
         }
         return words.get(word);
     }

     public int getSize(){
         return words.size();
     }

     public void setMap(LinkedHashMap<String,Integer> map){
         this.words=map;
     }

     public LinkedHashMap<String,Integer> getMap(){
         return this.words;
     }

    public static LinkedHashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    {

        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());


        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });


        LinkedHashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public void cutWords(int top,int bottom){
         words.keySet().removeAll(Arrays.asList(words.keySet().toArray()).subList(0,top));
         words.keySet().removeAll(Arrays.asList(words.keySet().toArray()).subList(bottom,words.size()));



    }






}
