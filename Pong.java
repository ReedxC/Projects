import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class PingPong
{
    JFrame frame;
    JPanel canvas;
    Timer timer;
    Pong pong;
    Paddle left,right;
    Score leftsc,rightsc;
    PingPong()
    {
        pong = new Pong(500,250);
        left = new Paddle(20,(650/2)-25);
        right = new Paddle(940,(650/2)-25);
        leftsc = new Score(20,600);
        rightsc = new Score(940,600);
        frame = new JFrame("Bounce Back");
        frame.setMinimumSize(new Dimension(1000,650));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas = new JPanel(true)
        {
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                setBackground(Color.BLACK);
                pong.paint(g);
                left.paint(g);
                right.paint(g);
                g.setColor(new Color(250,100,150));
                g.drawString("Left || Right",20,20);
                g.drawString("Up || Down",900,20);
                leftsc.paint(g);
                rightsc.paint(g);
            }
        }
        ;
        frame.add(canvas);
        frame.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent evt)
            {
                switch(evt.getKeyCode())
               {
                   case KeyEvent.VK_LEFT:left.speed=-20;break;
                   
                   case KeyEvent.VK_RIGHT:left.speed=20;break;
                   
                   case KeyEvent.VK_UP:right.speed=-20;break;
                   
                   case KeyEvent.VK_DOWN:right.speed=20;break;
               }
            }
            @Override
            public void keyReleased(KeyEvent evt)
            {
                switch(evt.getKeyCode())
                {
                   case KeyEvent.VK_LEFT:left.speed=0;break;
                   
                   case KeyEvent.VK_RIGHT:left.speed=0;break;
                   
                   case KeyEvent.VK_UP:right.speed=0;break;
                   
                   case KeyEvent.VK_DOWN:right.speed=0;break;
                }
            }
        }
        );
        pong.d=0+(int)(Math.random()*(360-0+1));
        ActionListener f = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                pong.bounce();
                if(((pong.xpos-left.xpos<=20)&&((pong.ypos>=left.ypos)&&(pong.ypos+30<=left.ypos+50)))||((right.xpos-pong.xpos<=35)&&((pong.ypos>=right.ypos)&&(pong.ypos+30<=right.ypos+50))))
                    pong.touchPaddle=true;
                else 
                    pong.touchPaddle=false;
                canvas.repaint();
                left.ypos+=left.speed;
                right.ypos+=right.speed;
                if(pong.xpos>1000)
                {
                    leftsc.points++;
                    pong.setPos();
                    pong.d=0+(int)(Math.random()*(360-0+1));
                }
                else if(pong.xpos<-30)
                {
                    rightsc.points++;
                    pong.setPos();
                    pong.d=0+(int)(Math.random()*(360-0+1));
                }
                if(rightsc.points==10||leftsc.points==10)
                    System.exit(0);            
            }
        }
        ;
        timer = new Timer(50,f);
        timer.start();
    }
}
class Pong
{
    int xpos, ypos,d;
    boolean touchPaddle=false;
    Pong(int x,int y)
    {
        xpos=x;
        ypos=y;
    }
    public void paint(Graphics g)
    {
        g.setColor(new Color(150,250,50));
        g.fillOval(xpos,ypos,30,30);
    }
    private int xspeed=0;private int yspeed=0;
    void moveTowardsDirection()
    {
        if(d==0||d==360)
        {
            xspeed=90;yspeed=0;
        }
        else if(d>0&&d<90)
        {
            xspeed=90-d;yspeed=d;
        }
        else if(d==90)
        {
            xspeed=0;yspeed=90;
        }
        else if(d>90&&d<180)
        {
            xspeed=90-d;yspeed=180-d;
        }
        else if(d==180)
        {
            xspeed=-90;yspeed=0;
        }
        else if(d>180&&d<270)
        {
            xspeed=d-270;yspeed=180-d;
        }
        else if(d==270)
        {
            xspeed=0;yspeed=-90;
        }
        else if(d>270&&d<360)
        {
            xspeed=d-270;yspeed=d-360;
        }
        else if(d==360)
        {
            d=0;xspeed=90;yspeed=0;
        }
        else if(d>360)
            System.err.println("direction cannot be > 360");
        xpos+=(xspeed/10)*3;
        ypos+=(yspeed/10)*3;
    }
    void bounce()
    {
        if(ypos>575)
        {
            if(d>0&&d<180)
                d=360-d;    
        }
        else if(ypos<=0)
        {
            if(d>180&&d<360)
                d=360-d;
        }
        if(touchPaddle)
        {
            if((d>180&&d<270)||(d<360&&d>270))
                d=540-d;
            else if((d<180&&d>90)||(d>0&&d<90))
                d=180-d;
            else if(d==180||d==0)
                d=180-d;
        }
        this.moveTowardsDirection();
    }
    void setPos()
    {
        xpos=500;ypos=650/2-25;
    }
}
class Paddle
{
    int xpos, ypos;
    int speed;
    Paddle(int x, int y)
    {
        xpos=x;
        ypos=y;
    }
    void paint(Graphics g)
    {
        g.setColor(Color.BLUE);
        g.fillRect(xpos,ypos,15,50);
    }    
}
class Score
{
    int xpos, ypos; int points = 0;
    Score(int x, int y)
    {
        xpos=x;
        ypos=y;
        points = 0;
    }
    public void paint(Graphics g)
    {
        g.setColor(Color.YELLOW);
        g.drawString(""+points+"",xpos,ypos);
    }
}