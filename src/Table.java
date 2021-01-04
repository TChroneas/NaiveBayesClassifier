
import java.util.HashMap;

public class Table {
     public HashMap<String,Integer> words=new HashMap<>();

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






}
