package securde.objects;

import java.util.Scanner;

public class test {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		while(true){
			System.out.println("[1] - Test Orders\n[X] - exit");
			switch(sc.nextInt()){
			case 1:test.testOrders();break;
			}
		}
	}
	
	private static void testOrders(){
		while(true){
			System.out.println("[1] - View User Orders\n[2] - View PM Orders\n[3] - Edit order status");
			
		}
	}
	
	
}
