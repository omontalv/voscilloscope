package es.upv.oscilloscope;

import es.upv.simulator.BooleanBitmap;
import es.upv.simulator.RotateBitmap;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.EventObject;

/**
 * <p>Title: Virtual Oscilloscope.</p>
 * <p>Description: A Oscilloscope simulator</p>
 * <p>Copyright (C) 2003 José Manuel Gómez Soriano</p>
 * <h2>License</h2>
 * <p>
 This file is part of Virtual Oscilloscope.

 Virtual Oscilloscope is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 Virtual Oscilloscope is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Virtual Oscilloscope; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.
 * </p>
 */

// Referenced classes of package es.upv.scope:
//            PTime, PVolt, PCalidad, Pantalla

public class PFondo extends Panel
    implements MouseListener
{

    public static Color relleno;
    public static Color fondo;
    public static Color panel;
    public PTime pTime;
    public PVolt pVolt;
    public PCalidad pCalidad;
    public Screen pantalla;

    public PFondo(URL dirImagenes)
    {
        relleno = new Color(90, 90, 90);
        fondo = new Color(120, 120, 120);
        panel = new Color(80, 80, 100);
        setBackground(Color.white);
        setForeground(panel);
        setLayout(null);
        pTime = new PTime(dirImagenes);
        add(pTime);
        pTime.setLocation(373, 12);
        pVolt = new PVolt(dirImagenes);
        add(pVolt);
        pVolt.setLocation(373, 189);
        pCalidad = new PCalidad(dirImagenes);
        add(pCalidad);
        pCalidad.setLocation(15, 335);
        pCalidad.BtnDigital.addMouseListener(this);
        pantalla = new Screen();
        add(pantalla);
        pantalla.setBounds(38, 20, 300, 300);
        addMouseListener(this);
    }

    public void addNotify()
    {
        super.addNotify();
    }

    public Dimension getMinimumSize()
    {
        return new Dimension(780, 400);
    }

    public Dimension getPreferredSize()
    {
        return getMinimumSize();
    }

    public void setLocation(int x, int y)
    {
        super.setLocation(x, y);
        setSize(getMinimumSize());
    }

    public void paint(Graphics g)
    {
        dibujaFondo(g);
    }

    public void mouseClicked(MouseEvent e)
    {
        if(e.getSource() == pCalidad.BtnDigital)
        {
            pVolt.BtnCiclica1.setVisible(!pCalidad.BtnDigital.getValor());
            pVolt.BtnCiclica2.setVisible(!pCalidad.BtnDigital.getValor());
            pTime.btnXPos.setValor(19F);
            pTime.btnXPos.setVisible(!pCalidad.BtnDigital.getValor());
            pTime.btnHoldOff.setVisible(!pCalidad.BtnDigital.getValor());
            pTime.btnMasMenos.setVisible(!pCalidad.BtnDigital.getValor());
            pTime.btnLevel.setVisible(!pCalidad.BtnDigital.getValor());
            pTime.btnAtNormal.setVisible(!pCalidad.BtnDigital.getValor());
            pTime.btnPosicion.setVisible(pCalidad.BtnDigital.getValor());
            pTime.lPosicion.setVisible(pCalidad.BtnDigital.getValor());
        }
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
    }

    public void mouseExited(MouseEvent mouseevent)
    {
    }

    public void mousePressed(MouseEvent mouseevent)
    {
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
    }

    public void dibujaFondo(Graphics g)
    {
        g.setColor(relleno);
        g.fillRoundRect(0, 0, 780, 400, 45, 45);
        g.setColor(fondo);
        g.fillRoundRect(4, 4, 772, 392, 45, 45);
        g.setColor(relleno);
        g.fillRoundRect(25, 7, 327, 327, 20, 20);
        int x1[] = {
            14, 25, 25
        };
        int y1[] = {
            172, 17, 324
        };
        g.fillPolygon(x1, y1, 3);
        int x2[] = {
            363, 352, 352
        };
        int y2[] = {
            172, 17, 324
        };
        g.fillPolygon(x2, y2, 3);
        g.setColor(fondo);
        g.fillRoundRect(29, 11, 319, 319, 20, 20);
        g.setColor(Color.black);
        g.fillRoundRect(33, 15, 310, 310, 15, 15);
    }

    public void update(Graphics g)
    {
        paint(g);
    }
}
