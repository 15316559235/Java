package hanoi;

import java.awt.*;
import javax.swing.*;

class var{
	public static int num=0;										
	public static int t_one=0,t_two=-1,t_three=-1;					
	public static int tower_one[]=new int[30],tower_two[]=new int[30],tower_three[]=new int[30];		
	public static int n=2;
	public static int rectx1,rectx2,recty=440,rectmid,rectmidt=315;	
	public static int t=0,t2=0;							
	public static int tower[]=tower_one;
	public static int bool=1;
	public static char str[]=new char[20];
}
public class Hanoi extends JFrame implements Runnable{
	public Thread thread1=new Thread(this);
	
	public Hanoi(){
		init();
		input();
		add(new Paints());
		thread1.start();
	}
	
	public void init(){
		for(int i=0;i<30;i++)								
		{
			var.tower_one[i]=i;
		}
		for(int i=0;i<30;i++)
		{
			var.tower_two[i]=i;
		}
		for(int i=0;i<30;i++)
		{
			var.tower_three[i]=i;
		}
	}
	
	public void input(){
		String str=JOptionPane.showInputDialog("�����뺺ŵ������");
		int x=(int)Float.parseFloat(str);
		var.n=x;
		var.t_one=x-1;		
	}
	
	public void moves(int a,int b)
	{ 
	    switch(a)
	    {
	        case 1:var.rectmid=155;var.tower=var.tower_one;var.t=var.t_one;var.t_one-=1;break;   
	        case 2:var.rectmid=315;var.tower=var.tower_two;var.t=var.t_two;var.t_two-=1;break;
	        case 3:var.rectmid=475;var.tower=var.tower_three;var.t=var.t_three;var.t_three-=1;break;
	    }
	    switch(b)
	    {
	        case 1:var.rectmidt=155;var.t2=var.t_one;break;			 
	        case 2:var.rectmidt=315;var.t2=var.t_two;break;
	        case 3:var.rectmidt=475;var.t2=var.t_three;break;
	    } 
	    if(a>=b) var.bool=1;
		else	 var.bool=0;
	    var.rectx1=var.rectmid-(14-var.tower[var.t])*5;				
	    var.rectx2=var.rectmid+(14-var.tower[var.t])*5;
	    var.recty=440-var.t*5;	
	    while(var.recty>=200)
	    {
	    	var.recty-=10;								
	    repaint();
	    }
	    while(var.rectmid!=var.rectmidt)					
	    {
	       if(1==var.bool)
	       {
	    	   var.rectmid-=5;
	    	   var.rectx1=var.rectmid-(14-var.tower[var.t])*5;		
	    	   var.rectx2=var.rectmid+(14-var.tower[var.t])*5;
	       repaint();
	       }
	        else
	        {
	        	var.rectmid+=5;
	        	var.rectx1=var.rectmid-(14-var.tower[var.t])*5;
	        	var.rectx2=var.rectmid+(14-var.tower[var.t])*5;
	        repaint();
	        }
	    }
	    while(var.recty<=(430-(var.t2)*5))
	    {
	    	var.recty+=10;							 
	  	try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
	    	repaint();
	    }
	    switch(b)
	    {
	        case 1:var.t_one+=1;var.tower_one[var.t_one]=var.tower[var.t];repaint();break;		
	        case 2:var.t_two+=1;var.tower_two[var.t_two]=var.tower[var.t];repaint();break;
	        case 3:var.t_three+=1;var.tower_three[var.t_three]=var.tower[var.t];repaint();break;
	    }   
	}
	
	public void hanoi(int n,int one,int two,int three)
	{
	    if(n==1) 
	    {
			moves(one,three);
	    }
		else
	    {
	        hanoi(n-1,one,three,two);
	        moves(one,three); 
	        hanoi(n-1,two,one,three);
	    }    
	}
	
	public void run(){
		hanoi(var.n,1,2,3);
	}
	
	public static void main(String[] args){
		Hanoi  frame= new Hanoi();
		
		frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(660, 550);
        frame.setVisible(true);
        frame.setResizable(false);    
	}
}

class Paints extends JPanel{
	public void paintComponent(Graphics g){
		int i,j,rectmid1;
		g.drawRect(80,450,150,5);
		g.drawRect(240,450,150,5);
		g.drawRect(400,450,150,5);
		rectmid1=155;
		
		int recty1=440;
		g.drawRect(var.rectx1,var.recty,var.rectx2-var.rectx1,9); 
		for(i=0;i<=var.t_one;i++)
			g.drawRect(155-(14-var.tower_one[i])*5,recty1-10*i,(14-var.tower_one[i])*10,10);
		for(i=0;i<=var.t_two;i++)
			g.drawRect(315-(14-var.tower_two[i])*5,recty1-10*i,(14-var.tower_two[i])*10,10);
		for(i=0;i<=var.t_three;i++)
			g.drawRect(475-(14-var.tower_three[i])*5,recty1-10*i,(14-var.tower_three[i])*10,10);
	}
}



