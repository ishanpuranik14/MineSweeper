import java.util.*;

public class MineSweeper
{
    int g[][], table[][], x, y, m, index, mineflag, numflags, flagsx[], flagsy[], flagindex, gameend;
    Random generatorx, generatory;
    gstack stx,sty;
    MineSweeper(int n){
        mineflag=1;
        gameend=0;
        g = new int[n][n];
        table = new int[(n*n)/5][2];                    //approx. 20% positions have mines
        numflags=(n*n)/5;
        flagsx=new int[numflags];
        flagsy=new int[numflags];
        x = n;
        y = n;
        index = 0;
        flagindex=0;
        generatorx = new Random();
        generatory = new Random();
        stx = new gstack(n);
        sty = new gstack(n);        
    }
    
    public boolean NotInTable(int q, int r){
        for(int i=0;i<index;i++){
            if((table[i][0] == q) && (table[i][1] == r)){
                return false;
            }
        }
        return true;
    }
    
    public void AddInTable(int q, int r){
        table[index][0] = q;
        table[index][1] = r;
        index = index + 1;
    }
    
    public void initgraph(){
        m = (x*y)/5;
        while(m!=0){
            int q = generatorx.nextInt(x);
            int r = generatory.nextInt(y);
            if(NotInTable(q,r)){
                AddInTable(q,r);
                g[q][r] = 9;                // '9' is for mines.
                m=m-1;
            }
        }
        for(int i=0;i<index;i++){           //all non-mine fields around every mined fie ar increased by 1
            int fieldx=table[i][0];
            int fieldy=table[i][1];         
            for(int j=-1;j<=1;j++){
                for(int o=-1;o<=1;o++){
                    if(fieldx+j<0|fieldx+j>=x|fieldy+o<0|fieldy+o>=y){
                    }
                    else{
                            if(g[fieldx+j][fieldy+o]!=9){
                                g[fieldx+j][fieldy+o]=g[fieldx+j][fieldy+o]+1;
                            }    
                    }
                }
            }
        }
    }
    
    public void displaygrid(){
        System.out.print(":) ");
        for(int i=0;i<y;i++){
            System.out.print(i);
            System.out.print(" ");            
        }
        System.out.println();
        for(int i=0;i<x;i++){
            System.out.print(i);
            System.out.print("  ");
            for(int j=0;j<y;j++){
                if(g[i][j]>=10){
                    System.out.print(g[i][j]-10);
                }
                else{
                    System.out.print(g[i][j]);                                        
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }
    
    public void display(){
        System.out.print("   Flags left : ");
        System.out.print(numflags);
        System.out.println();
        System.out.print(":) ");
        for(int i=0;i<y;i++){
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.println();
        for(int i=0;i<x;i++){
            System.out.print(i);
            System.out.print("  ");
            for(int j=0;j<y;j++){
                if(g[i][j]>=9){
                    gameend=1;
                }
                else{
                    gameend=0;
                }
                if(g[i][j]>=10){
                    System.out.print((g[i][j]-10));
                    System.out.print(" ");
                }
                else{                    
                    int temp=searchflags(i,j);
                    if(temp>=0){
                        System.out.print("F ");
                    }
                    else{
                        System.out.print("_ ");
                    }                    
                }
            }
            System.out.println();
        }
        if(flagindex!=((x*y)/5)){
            gameend=gameend*0;
        }        
    }
    
    public void dfs(int xin,int yin){
        int valx,valy;       
        stx.push(xin);
        sty.push(yin);
        while(!stx.empty()){
            valx=stx.pop();
            valy=sty.pop();            
            for(int i=-1;i<=1;i++){
                for(int j=-1;j<=1;j++){
                    if(valx+i<0 | valy+j<0 | valx+i>=x | valy+j>=y){                       
                    }                    
                    else{
                        int temp=searchflags(valx+i,valy+j);
                        if((g[valx+i][valy+j]==0) & (temp==-1)){                            
                            stx.push(valx+i);
                            sty.push(valy+j);
                            g[valx+i][valy+j]=10;
                        }
                        if((g[valx+i][valy+j]<9) & (temp==-1)){
                            g[valx+i][valy+j]= g[valx+i][valy+j]+10;
                        }                                                
                    }
                }
            }
        }
    }
    
    public void addflag(int xin, int yin){
        flagsx[flagindex]=xin;
        flagsy[flagindex]=yin;
        flagindex=flagindex+1;
    }
    
    public int searchflags(int xin, int yin){
        int i;
        for(i=0;i<flagindex;i++){
            if((flagsx[i] == xin) & (flagsy[i] == yin)){
                return i;
            }
        }
        return -1;
    }
    
    public void removeflag(int removeindex){
        for(int i=removeindex;i<flagindex-1;i++){
            flagsx[i]=flagsx[i+1];
            flagsy[i]=flagsy[i+1];
        }
        flagindex=flagindex-1;
    }
    
    public void input(int xin, int yin){
        if(xin<0 & yin<0){
            if(xin<=-30){
                xin=0;
            }
            if(yin<=-30){
                yin=0;
            }
            if(g[-xin][-yin]>=10){
                System.out.println("The field has already been selected.Please select some other field.");
            }
            else{
                int temp=searchflags(-xin,-yin);
                if(temp>=0){
                    removeflag(temp);
                    numflags=numflags+1;
                }
                else{                    
                    if(numflags>0){
                        numflags=numflags-1;
                        addflag(-xin,-yin);
                    }
                    else{
                        System.out.println("No more flags!");
                    }
                }
            }
        }
        else{
            int temp1=searchflags(xin,yin);
            if(g[xin][yin]>=10){
                System.out.println("The field has already been selected.Please select some other field.");
            }
        else if(g[xin][yin]==9){
            if(temp1>=0){
                    System.out.println("This field is flagged.To select it, you must unflag it first.");
                }
            else{
                    System.out.println("Mine field! Game Over!");
                    mineflag=0;
                }
            }
        else{           
                if(temp1>=0){
                    System.out.println("This field is flagged.To select it, you must unflag it first.");
                }
                else{
                    g[xin][yin] = g[xin][yin]+10;
                    if(g[xin][yin]==10){
                        dfs(xin,yin);
                    }
                }                
            }
        }        
    }
    
    public int alive(){
        return mineflag;
    }
    
    public int finished(){
        return gameend;
    }
}
