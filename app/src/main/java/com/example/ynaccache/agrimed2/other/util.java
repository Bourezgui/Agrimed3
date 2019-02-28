package com.example.ynaccache.agrimed2.other;



public  class util {

public static String convertor(String input,int longeur){
    int l=input.length();
    String s="";
       for(int i=0;i<longeur-l;i++){
           s=s+"0";
       }


    input=s+input;
    return input;
}

/*public static void main(String[] args){

    util u = new util();
    System.out.println(u.convertor("55555",6));

}*/
}
