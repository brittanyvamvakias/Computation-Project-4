import java.awt.*;
import org.opensourcephysics.display.*;

public class MovingTObject implements Drawable {
 	    
	   double cof,dt,t; 
       double x,y,vx,vy,E;
       double x1,x2,y1,y2;
       double d1x,d1y,d2x,d2y,d12,d22,d1,d2;
    //    private double ax,ay,r,r2;
       double ax,ay,r,r2;       
	   Trail trail = new Trail();
       double nspeed, xrot, yrot;
        double rcos = Math.cos(t);
        double rsin = Math.sin(t);
        // double xrot = rcos*x+rsin*y;
        // double yrot = -rsin*x+rcos*y;

       public MovingTObject(){System.out.println("A new moving object is created.");					      
       }		     	      
//------------------object properties       
	   public void energy(){
			E=0.5*(vx*vx+vy*vy)-1./Math.sqrt(x*x+y*y);
	   }
	   public void accel(double t){
			// r2 = x*x+y*y;
            // r  = Math.sqrt(r2);

            x1 = 0.5 * Math.cos(t); // massive body 1
            y1 = 0.5 * Math.sin(t);
            x2 = -x1; // massive body 2
            y2 = -y1;

            d1x = xrot - x1;
            d1y = yrot - y1;
            d2x = xrot - x2;
            d2y = yrot - y2;

            d12 = d1x*d1x + d1y*d1y;
            d22 = d2x*d2x + d2y*d2y;
            d1 = Math.sqrt(d12);
            d2 = Math.sqrt(d22);

			// ax = -x/r/r2;
            // ay = -y/r/r2;
            ax = -0.5 * ((d1x/(d1*d1*d1))+(d2x/(d2*d2*d2)));
            ay = -0.5 * ((d1y/d1/d12)+(d2y/d2/d22));
	   }
//------------------object motion       
	   public void positionstep(double cof){
	    			      x = x+vx*dt*cof; 
                          y = y+vy*dt*cof; 
                          xrot = rcos*x+rsin*y;
                          yrot = -rsin*x+rcos*y;
                          
	   }
	   public void velocitystep(double cof){
	                      accel(t);
	    			      vx = vx+ax*dt*cof; 
	    			      vy = vy+ay*dt*cof; 
	   }
	   public void doStep(double cof){
                        positionstep(0.5*cof);
                        t=t+0.5*cof*dt;
                        velocitystep(1.0*cof);
                        positionstep(0.5*cof);
                        t=t+0.5*dt*cof;
                        trail.addPoint(xrot, yrot);
	   }
       public void draw(DrawingPanel panel, Graphics g) {
			int irad=8;
			int nspeed = 10;
            int xpix = panel.xToPix(0.5)-irad;
            int ypix = panel.yToPix(0)-irad;   //sun at the origin
            g.setColor(Color.BLUE);
            g.fillOval(xpix, ypix, 2*irad, 2*irad);
            irad = 8;
            xpix = panel.xToPix(-0.5)-irad;
            ypix = panel.yToPix(0)-irad;   //second sun
            g.setColor(Color.GREEN);
            g.fillOval(xpix, ypix, 2*irad, 2*irad);
	        irad=5;            //smaller moving planet
            xpix = panel.xToPix(xrot)-irad;
            ypix = panel.yToPix(yrot)-irad;
            g.setColor(Color.RED);
            g.fillOval(xpix, ypix, 2*irad, 2*irad);
            trail.draw(panel, g);
       }

}
