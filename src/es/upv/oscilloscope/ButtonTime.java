package es.upv.oscilloscope;

import es.upv.simulator.*;
import java.awt.*;
import java.net.URL;

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

public class ButtonTime extends DoubleRotateBitmap
{

    public ButtonTime(URL path, String ArchivosGrueso[], float ValoresGrueso[], String ArchivosFino[], float ValoresFino[])
    {
        super(path, ArchivosGrueso, ValoresGrueso, ArchivosFino, ValoresFino);
    }

    public void dibujaValores(Graphics g)
    {
        String SValores[] = {
            "200", "100", "50", "20", "10", "5", "2", "1", ".5", ".2",
            ".1", "50", "20", "10", "5", "2", "1", ".5"
        };
        int x[] = {
            9, 0, 0, 0, 5, 18, 29, 42, 55, 70,
            86, 100, 110, 120, 123, 120, 115, 105
        };
        int y[] = {
            112, 96, 80, 65, 50, 36, 25, 18, 14, 14,
            17, 24, 36, 50, 65, 80, 96, 107
        };
        int n = SValores.length - 1;
        g.setColor(getBackground());
        g.drawLine(92, 19, 112, 0);
        g.drawLine(93, 20, 113, 0);
        g.drawLine(94, 21, 114, 0);
        g.setColor(Color.orange);
        g.drawString("ms", 4, 10);
        g.drawString("\265s", getSize().width - 17, 10);
        for(int i = 0; i <= n; i++)
        {
            if(i == getPosicion())
            {
                g.setColor(Color.red);
            } else
            {
                g.setColor(Color.orange);
            }
            g.drawString(SValores[n - i], x[n - i], y[n - i]);
        }

    }

    public void btnMove(int x, int y)
    {
        double angulo = getAngulo(x, y, 73, 60);
        if(angulo < 0.91239999999999999D && angulo > 0.60640000000000005D)
        {
            setPosicion(17);
        }
        if(angulo < 0.60640000000000005D && angulo > 0.3634D)
        {
            setPosicion(16);
        }
        if(angulo < 0.3634D && angulo > 0.1537D)
        {
            setPosicion(15);
        }
        if(angulo < 0.1537D || angulo > 6.2138D)
        {
            setPosicion(14);
        }
        if(angulo < 6.2138D && angulo > 5.9889999999999999D)
        {
            setPosicion(13);
        }
        if(angulo < 5.9889999999999999D && angulo > 5.6532D)
        {
            setPosicion(12);
        }
        if(angulo < 5.6532D && angulo > 5.3764000000000003D)
        {
            setPosicion(11);
        }
        if(angulo < 5.3764000000000003D && angulo > 5.1344000000000003D)
        {
            setPosicion(10);
        }
        if(angulo < 5.1344000000000003D && angulo > 4.7907000000000002D)
        {
            setPosicion(9);
        }
        if(angulo < 4.7907000000000002D && angulo > 4.4958D)
        {
            setPosicion(8);
        }
        if(angulo < 4.4958D && angulo > 4.2568999999999999D)
        {
            setPosicion(7);
        }
        if(angulo < 4.2568999999999999D && angulo > 3.8879999999999999D)
        {
            setPosicion(6);
        }
        if(angulo < 3.8879999999999999D && angulo > 3.5560999999999998D)
        {
            setPosicion(5);
        }
        if(angulo < 3.5560999999999998D && angulo > 3.2728999999999999D)
        {
            setPosicion(4);
        }
        if(angulo < 3.2728999999999999D && angulo > 2.9918D)
        {
            setPosicion(3);
        }
        if(angulo < 2.9918D && angulo > 2.7027000000000001D)
        {
            setPosicion(2);
        }
        if(angulo < 2.7027000000000001D && angulo > 2.4445999999999999D)
        {
            setPosicion(1);
        }
        if(angulo < 2.4445999999999999D && angulo > 2.0964D)
        {
            setPosicion(0);
        }
        super.posImagen = getPosicion();
    }
}

