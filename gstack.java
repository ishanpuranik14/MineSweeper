public class gstack
{
    int s[],e;
    
    public gstack(int n)
    {
        s=new int[n*n];
        e=-1;
    }
    
    public void push(int x){
        e=e+1;
        s[e]=x;
    }
    
    public int pop(){        
        e=e-1;
        return s[e+1];        
    }
    
    public boolean empty(){
        if(e==-1){
            return true;
        }
        return false;
    }

}
