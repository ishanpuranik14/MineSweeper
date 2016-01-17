import java.util.Scanner;

public class main
{
    MineSweeper m;
    Scanner in;
    int x,y,rc;
    public void main()
    {
        x=0;
        y=0;
        rc=7;
        in = new Scanner(System.in);
        m = new MineSweeper(rc);
        m.initgraph();
        System.out.println("Welcome to Minesweeper! This game was developed by Ishan Puranik.");
        System.out.println("Flags can put by passing both coordinates as negative.");
        System.out.println("BUT when value of any row or column or both is zero, you can pass ");
        System.out.println("any value less than -30 to indicate for that row or column or both!");        
        System.out.println();        
        while((m.alive()==1)){
            m.display();
            if(m.finished()==0){
                System.out.print("enter x and y(start : 0 ; end : ");
                System.out.print(rc-1);
                System.out.println("): ");
                x=in.nextInt();                  
                y=in.nextInt();
                m.input(x,y);
            }
            else{
                break;
            }
        }
        if(m.finished()==1){
            System.out.println("Congratulations player! you have won the game!");
        }
        System.out.println();
        m.displaygrid();
    }
}
