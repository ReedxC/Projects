import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class TwoZeroFourEight
{
    Game game=new Game();
    String direction="";
    int times=0;
    Set<Integer> pressedKeys = new TreeSet<Integer>();
    boolean addEnd=false;
    TwoZeroFourEight()
    {
        JFrame f;
        JPanel p;
        JPanel end;
        javax.swing.Timer t;
        ActionListener a;
        f = new JFrame("2048");
        f.setMinimumSize(new Dimension(400,450));
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        end = new JPanel(true)
        {
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.setColor(Color.BLUE);
                Font f = new Font( "SansSerif", Font.PLAIN, 32 );
                g.setFont(f);
                g.drawString("Game Over",10,250);
            }
        }
        ;
        p = new JPanel(true)
        {
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                setBackground(Color.BLACK);
                int x,y=20;
                for(int i=0;i<4;i++)
                {
                    x=-50;
                    for(int j=0;j<4;j++)
                    {
                        game.table[i][j].xpos=x+=80;
                        game.table[i][j].ypos=y;
                    }
                    y+=80;
                }
                game.paintTable(g);
                game.paintScore(g);
            }
        }
        ;
        f.add(p);
        f.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent ke) 
            {
                int code = ke.getKeyCode();
                Integer val = Integer.valueOf(code);
                if (pressedKeys.contains(val))
                {
                    return;
                }
                else 
                {
                    switch(code)
                    {
                        case KeyEvent.VK_LEFT:direction="left";break;
                   
                        case KeyEvent.VK_RIGHT:direction="right";break;
                   
                        case KeyEvent.VK_UP:direction="up";break;
                   
                        case KeyEvent.VK_DOWN:direction="down";break;
                    }
                }
            }
        }
        );
        
        a = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                   if(game.noMovement(direction))
                    {
                        if(!game.check()&&!game.movementPossible())
                            System.exit(0);
                    }
                    else if(direction.equalsIgnoreCase("UP"))
                    {
                        game.table=game.Up(game.table);
                        game.generate();
                        direction="";
                    }
                    else if(direction.equalsIgnoreCase("Down"))
                    {
                        game.table=game.Down(game.table);
                        game.generate();
                        direction="";
                    }
                    else if(direction.equalsIgnoreCase("left"))
                    {
                        game.table=game.Left(game.table);
                        game.generate();
                        direction="";
                    }
                    else if(direction.equalsIgnoreCase("right"))
                    {
                        game.table=game.Right(game.table);
                        game.generate();
                        direction="";
                    }
                game.getScore();        
                p.repaint();
                end.repaint();
            }
        }
        ;
        if(addEnd)
            f.add(end);
        
        t = new javax.swing.Timer(50,a);
        t.start();
    }
    public static void main(String[] args)
    {
         new TwoZeroFourEight();
    }
}
class Game
{    
    Tile[][] table = new Tile[4][4];
    int score=0;
    Game()
    {
        init();
        generate();generate();
    }
    void paintTable(Graphics g)
    {
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                table[i][j].paint(g);
    }
    void input(int[][] a)
    {
       for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                table[i][j]=new Tile(a[i][j]); 
    }
    boolean movementPossible()
    {
        boolean r = false;
        for(int i=1;i<3;i++)
            for(int j=1;j<3;j++)
            
                if(table[i][j].data==table[i][j+1].data||table[i][j].data==table[i][j-1].data||
                table[i][j].data==table[i+1][j].data||table[i][j].data==table[i-1][j].data||
                table[0][0].data==table[0][1].data||table[0][0].data==table[1][0].data||
                table[0][3].data==table[0][2].data||table[0][3].data==table[1][3].data||
                table[3][0].data==table[2][0].data||table[3][0].data==table[3][1].data||
                table[3][3].data==table[3][2].data||table[3][3].data==table[2][3].data)
                {
                    r=true;
                    break;
                }
        return r;        
    }
    void getScore()
    {
        int sum=0;
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                sum+=table[i][j].data;
        score=sum;        
    }
    void paintScore(Graphics g)
    {
        g.setColor(Color.BLUE);
        Font f = new Font( "SansSerif", Font.PLAIN, 30 );
        g.setFont(f);
        g.drawString("Score:"+score,25,390);
    }
    boolean noMovement(String d)
    {
        if(d.equalsIgnoreCase("Up"))
        {
            Tile[][] t= new Tile[4][4];
            for(int i=0;i<4;i++)
                for(int j=0;j<4;j++)
                    t[i][j]=new Tile(table[i][j].data);
            t=Up(t);
            if(sameTables(t,table))
                return true;
            else
                return false;
        }
        else if(d.equalsIgnoreCase("down"))
        {
            Tile[][] t= new Tile[4][4];
            for(int i=0;i<4;i++)
                for(int j=0;j<4;j++)
                    t[i][j]=new Tile(table[i][j].data);
            t=Down(t);
            if(sameTables(t,table))
                return true;
            else
                return false;
        }
        else if(d.equalsIgnoreCase("left"))
        {
            Tile[][] t= new Tile[4][4];
            for(int i=0;i<4;i++)
                for(int j=0;j<4;j++)
                    t[i][j]=new Tile(table[i][j].data);
            t=Left(t);
            if(sameTables(t,table))
                return true;
            else
                return false;
        }
        else if(d.equalsIgnoreCase("Right"))
        {
            Tile[][] t= new Tile[4][4];
            for(int i=0;i<4;i++)
                for(int j=0;j<4;j++)
                    t[i][j]=new Tile(table[i][j].data);
            t=Right(t);
            if(sameTables(t,table))
                return true;
            else
                return false;
        }
        else
            return false;
    }
    boolean sameTables(Tile[][] t1,Tile[][] t2)
    {
        boolean r=false;
        outer:
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                if(t1[i][j].data==t2[i][j].data)
                    r=true;
                else
                {
                    r=false;
                    break outer;
                }
        return r;        
    }
    boolean check()
    {
        boolean ret=false;
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                if(table[i][j].data!=0)
                    ret=false;
                else
                {
                    ret=true;
                    break;
                }
        return ret;    
    }
    void init()
    {
        int c=0;
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
            {
                table[i][j]=new Tile(0);
            }
    }
    void display()
    {
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                String s = String.valueOf(table[i][j].data);
                int spaces = 4-s.length();
                String space = createSpaces(spaces);
                System.out.print(table[i][j].data+space+" ");
            }
            System.out.println("\n\n");
        }    
        getScore();
        System.out.println("Score: "+score);
    }
    String createSpaces(int s)
    {
        String r="";
        for(int i=0;i<s;i++)
            r+=" ";
        return r;    
    }
    void generate()
    {
        int c=0;
        ArrayList<Integer> a = new ArrayList<>();
        int[][] square = new int[4][4];
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                square[i][j]=c++;
        c=0;        
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
            {
                if(table[i][j].data==0)
                    a.add(c);
                c++;    
            }
        int x = a.get(randomRanged(0,a.size()-1));
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                if(x==square[i][j])
                {
                    table[i][j].data=number();
                    break;
                }
    }
    int randomRanged(int min, int max)
    {
        return ((int)(Math.random() * (Math.abs(max - min) + 1)) + (min <= max ? min : max));
    }
    int number()
    {
        if((int)(Math.random()*2)==0)
            return 2;
        else
            return 4;
    }
    Tile[][] Left(Tile[][] t)
    {
        for(int i=0;i<4;i++)
        {    
            for(int j=0;j<3;j++)
            {
                t[i]=shiftLeft(t[i]);
                if(t[i][j].data==t[i][j+1].data)
                {
                    t[i][j].data=t[i][j+1].data+t[i][j].data;
                    t[i][j+1].data=0;
                }
            }
        }
        return t;
    }
    Tile[][] Right(Tile[][] t)
    {
        for(int i=0;i<4;i++)
        {    
            for(int j=3;j>0;j--)
            {
                t[i]=shiftRight(t[i]);
                if(t[i][j].data==t[i][j-1].data)
                {
                    t[i][j].data=t[i][j-1].data+t[i][j].data;
                    t[i][j-1].data=0;
                }
            }
        }
        return t;
    }
    Tile[][] Up(Tile[][] a)
    {
        int[][] t = new int[4][4];
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                t[i][j]=a[i][j].data;
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                a[i][j].data=t[j][i];        
        a=Left(a);
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                t[i][j]=a[i][j].data;
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                a[i][j].data=t[j][i];
        return a;
    }
    Tile[][] Down(Tile[][] a)
    {
        int[][] t = new int[4][4];
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                t[i][j]=a[i][j].data;
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                a[i][j].data=t[j][i];        
        a=Right(a);
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                t[i][j]=a[i][j].data;
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                a[i][j].data=t[j][i];
        return a;
    }
    Tile[] shiftLeft(Tile[] a)
    {
        int r[] = new int[a.length];
        int c=0;
        for(int i=0;i<a.length;i++)
            if(a[i].data!=0)
                r[c++]=a[i].data;
        for(int i=0;i<a.length;i++)
            a[i].data=r[i];        
        return a;        
    }
    Tile[] shiftRight(Tile[] a)
    {
        int r[] = new int[a.length];
        int c=r.length-1;
        for(int i=a.length-1;i>=0;i--)
            if(a[i].data!=0)
                r[c--]=a[i].data;
        for(int i=0;i<a.length;i++)
            a[i].data=r[i];        
        return a;
    }
}
class Tile
{
    int data;
    int xpos,ypos;
    Tile(int n)
    {
        data=n;
    }
    void paint(Graphics g)
    {
        g.setColor(new Color(255,255,0));
        g.fillRect(xpos,ypos,75,75);
        g.setColor(Color.RED);
        Font f = new Font( "SansSerif", Font.PLAIN, 32 );
        g.setFont(f);
        String s = String.valueOf(data);
        int spaces = 4-s.length();
        String space = createSpaces(spaces);
        if(data!=0)
        g.drawString(this.data+space,xpos+10,ypos+50);
    }
    String createSpaces(int s)
    {
        String r="";
        for(int i=0;i<s;i++)
            r+=" ";
        return r;    
    }
}