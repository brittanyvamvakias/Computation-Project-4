import java.awt.*;
import org.opensourcephysics.display.*;

public class MovingKObject implements Drawable {
 	    
	   double cof,dt,t; 
	   double x,y,vx,vy,E;
	   private double ax,ay,r,r2;      
	   Trail trail = new Trail();
	   double nspeed;

       public MovingKObject(){System.out.println("A new moving object is created.");					      
       }		     	      
//------------------object properties       
	   public void energy(){
			E=0.5*(vx*vx+vy*vy)-1./Math.sqrt(x*x+y*y);
	   }
	   public void accel(){
			r2 =x*x+y*y;
			r  =Math.sqrt(r2);
			ax=-x/r/r2;
			ay=-y/r/r2;
	   }
//------------------object motion       
	   public void positionstep(double cof){
	    			      x = x+vx*dt*cof; 
	    			      y = y+vy*dt*cof; 
	   }
	   public void velocitystep(double cof){
	                      accel();
	    			      vx = vx+ax*dt*cof; 
	    			      vy = vy+ay*dt*cof; 
	   }
	   public void doStep(double cof){
			  positionstep(0.5*cof);
			  velocitystep(1.0*cof); 
			  positionstep(0.5*cof);
			  t=t+dt;
              trail.addPoint(x, y);
	   }
       public void draw(DrawingPanel panel, Graphics g) {
			int irad=8;
			int nspeed = 10;
            int xpix = panel.xToPix(0.0)-irad;
            int ypix = panel.yToPix(0.0)-irad;   //sun at the origin
            g.setColor(Color.BLUE);
            g.fillOval(xpix, ypix, 2*irad, 2*irad);
	        irad=5;            //smaller moving planet
            xpix = panel.xToPix(x)-irad;
            ypix = panel.yToPix(y)-irad;
            g.setColor(Color.RED);
            g.fillOval(xpix, ypix, 2*irad, 2*irad);
            trail.draw(panel, g);
       }

}
