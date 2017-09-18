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

// Referenced classes of package es.upv.scope:
//            PFondo

public class PCalidad extends Panel
{

    public StaticBitmapButton BtnCal;
    public InterruptBitmap BtnX10;
    public RotateBitmap BtnCalidad;
    public InterruptBitmap BtnDigital;

    public PCalidad(URL DirImagenes)
    {
        setBackground(PFondo.fondo);
        setForeground(PFondo.panel);
        setLayout(null);
        construirElementos(DirImagenes);
    }

    public void iniciarElementos()
    {
        BtnX10.setValor(false);
        BtnCalidad.setValor(1.0F);
        BtnDigital.setValor(false);
    }

    public void addNotify()
    {
        super.addNotify();
        posicionarElementos();
        iniciarElementos();
    }

    private void construirElementos(URL DirImagenes)
    {
        String fino[] = {
            "bb00.gif", "bb01.gif", "bb02.gif", "bb03.gif", "bb04.gif", "bb05.gif", "bb06.gif", "bb07.gif", "bb08.gif", "bb09.gif",
            "bb10.gif", "bb11.gif", "bb12.gif", "bb13.gif", "bb14.gif", "bb15.gif", "bb16.gif", "bb17.gif", "bb18.gif", "bb19.gif",
            "bb20.gif", "bb21.gif", "bb22.gif", "bb23.gif", "bb24.gif", "bb25.gif", "bb26.gif", "bb27.gif", "bb28.gif", "bb29.gif",
            "bb31.gif", "bb32.gif", "bb33.gif", "bb34.gif", "bb35.gif", "bb36.gif", "bb37.gif", "bb38.gif", "bb39.gif", "bb40.gif",
            "bb41.gif", "bb42.gif", "bb43.gif", "bb44.gif", "bb45.gif"
        };
        String pulsador[] = {
            "Bc01.gif", "Bc00.gif"
        };
        String Cal[] = {
            "Ajuste.gif"
        };
        float ValoresCalidad[] = new float[10];
        for(int i = 0; i < 10; i++)
        {
            ValoresCalidad[i] = i + 1;
        }

        Mensaje mens = Mensaje.makeDialog("              Panel 3 de 3. Cargando bot\363n 1 de 4: bot\363n de X-MAG         " +
"    "
, "Informaci\363n", false);
        BtnX10 = new InterruptBitmap(DirImagenes, pulsador);
        add(BtnX10);
        BtnX10.putTitulo("X-Mag");
        mens.setText("Panel 3 de 3. Cargando bot\363n 2 de 4: bot\363n de ANALOG/DIGITAL");
        BtnDigital = new InterruptBitmap(DirImagenes, pulsador);
        add(BtnDigital);
        BtnDigital.putTitulo("Analog/Digital");
        mens.setText("Panel 3 de 3. Cargando bot\363n 3 de 4: potenci\363metro de CALIDAD");
        BtnCalidad = new RotateBitmap(DirImagenes, fino, ValoresCalidad);
        add(BtnCalidad);
        mens.setText("Panel 3 de 3. Cargando bot\363n 4 de 4: bot\363n de CALIBRACI\323N");
        BtnCal = new StaticBitmapButton(DirImagenes, Cal);
        add(BtnCal);
        mens.closeDialog();
    }

    public void posicionarElementos()
    {
        BtnCalidad.setLocation(7, 1);
        BtnDigital.setLocation(114, 1);
        BtnX10.setLocation(207, 1);
        BtnCal.setLocation(261, 1);
    }

    public Dimension getMinimumSize()
    {
        return new Dimension(345, 52);
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

    public void update(Graphics g)
    {
        paint(g);
    }

    public void paint(Graphics g)
    {
        DibujaFondo(g);
        g.setColor(Color.orange);
        g.drawString("Calidad", 60, 15);
        g.drawString("de la", 60, 30);
        g.drawString("se\361al", 60, 45);
        g.drawString("CAL", 280, 14);
        g.drawString("0.2V", 280, 28);
        g.drawString("2V", 282, 45);
        g.drawLine(300, 45, 300, 35);
        g.drawLine(300, 35, 310, 35);
        g.drawLine(310, 35, 310, 45);
        g.drawLine(310, 45, 320, 45);
        g.drawLine(320, 45, 320, 35);
    }

    public void DibujaFondo(Graphics g)
    {
        g.setColor(PFondo.relleno);
        g.fillRoundRect(0, 0, 345, 52, 10, 10);
    }
}
