package com.app;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Strcomp {
	public static void main(String[] args) {
		int n;
		Scanner sc = new Scanner(System.in);
		n=Integer.parseInt(sc.nextLine());
		List<String> list = new ArrayList<String>();
		StrLComparator ss = new StrLComparator(null);
	    for(int i=0;i<n;i++){
	    	list.add(sc.nextLine());
	    }
	    Collections.sort(list, ss);
    	System.out.println("After Sorting");
	    for(String s:list){
	    	System.out.println(s);	
	    }
	    
	}
}
