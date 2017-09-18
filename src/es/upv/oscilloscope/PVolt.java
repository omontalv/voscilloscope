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
//            ButtonVolt, CommutatorACDC, PFondo

public class PVolt extends Panel
{

    public InterruptBitmap BtnCH12;
    public InterruptBitmap BtnDual;
    public InterruptBitmap BtnAdd;
    public ButtonVolt BtnVolt1;
    public StaticBitmapButton BtnCanal1;
    public InterruptBitmap BtnInv1;
    public CommutatorACDC BtnAcDc1;
    public RotateBitmap BtnYPos1;
    public InterruptBitmap BtnCiclica1;
    public ButtonVolt BtnVolt2;
    public StaticBitmapButton BtnCanal2;
    public InterruptBitmap BtnInv2;
    public CommutatorACDC BtnAcDc2;
    public RotateBitmap BtnYPos2;
    public InterruptBitmap BtnCiclica2;

    public PVolt(URL DirImagenes)
    {
        setBackground(PFondo.fondo);
        setForeground(PFondo.panel);
        setLayout(null);
        construirElementos(DirImagenes);
    }

    public void iniciarElementos()
    {
        BtnCH12.setValor(false);
        BtnDual.setValor(false);
        BtnAdd.setValor(false);
        BtnVolt1.setValor(20F);
        BtnInv1.setValor(false);
        BtnAcDc1.setValor(2);
        BtnYPos1.setValor(0.0F);
        BtnCiclica1.setValor(false);
        BtnVolt2.setValor(20F);
        BtnInv2.setValor(false);
        BtnAcDc2.setValor(2);
        BtnYPos2.setValor(0.0F);
        BtnCiclica2.setValor(false);
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
        String Voltios[] = {
            "ba26.gif", "ba29.gif", "ba33.gif", "ba37.gif", "ba40.gif", "ba43.gif", "ba01.gif", "ba05.gif", "ba09.gif", "ba13.gif",
            "ba17.gif", "ba20.gif"
        };
        String canal1[] = {
            "canal1.gif"
        };
        String canal2[] = {
            "canal2.gif"
        };
        String pulsador[] = {
            "Bc01.gif", "Bc00.gif"
        };
        String AcDc[] = {
            "AcDc02.gif", "AcDc01.gif", "AcDc00.gif"
        };
        float ValTension[] = {
            0.005F, 0.01F, 0.02F, 0.05F, 0.1F, 0.2F, 0.5F, 1.0F, 2.0F, 5F,
            10F, 20F
        };
        float ValoresFinos[] = new float[45];
        for(int i = 0; i < 45; i++)
        {
            ValoresFinos[i] = (float)((double)i * 0.5D);
        }

        float ValoresYPos[] = new float[291];
        for(int i = 0; i < 291; i++)
        {
            ValoresYPos[i] = i - 145;
        }

        Mensaje mens = Mensaje.makeDialog("              Panel 2 de 3. Cargando bot\363n 1 de 12: bot\363n de INV I        " +
"     "
, "Informaci\363n", false);
        BtnInv1 = new InterruptBitmap(DirImagenes, pulsador);
        add(BtnInv1);
        BtnInv1.putTitulo("Inv. I");
        mens.setText("Panel 2 de 3. Cargando bot\363n 2 de 15: bot\363n de INV II");
        BtnInv2 = new InterruptBitmap(DirImagenes, pulsador);
        add(BtnInv2);
        BtnInv2.putTitulo("Inv. II");
        mens.setText("Panel 2 de 3. Cargando bot\363n 3 de 15: bot\363n de CH I/II");
        BtnCH12 = new InterruptBitmap(DirImagenes, pulsador);
        add(BtnCH12);
        BtnCH12.putTitulo("CH I/II");
        mens.setText("Panel 2 de 3. Cargando bot\363n 4 de 15: bot\363n de DUAL");
        BtnDual = new InterruptBitmap(DirImagenes, pulsador);
        add(BtnDual);
        BtnDual.putTitulo("Dual");
        mens.setText("Panel 2 de 3. Cargando bot\363n 5 de 15: bot\363n de ADD");
        BtnAdd = new InterruptBitmap(DirImagenes, pulsador);
        add(BtnAdd);
        BtnAdd.putTitulo("Add");
        mens.setText("Panel 2 de 3. Cargando bot\363n 6 de 15: bot\363n de CICLICA I");
        BtnCiclica1 = new InterruptBitmap(DirImagenes, pulsador);
        add(BtnCiclica1);
        BtnCiclica1.putTitulo("Ciclica I");
        BtnCiclica2 = new InterruptBitmap(DirImagenes, pulsador);
        mens.setText("Panel 2 de 3. Cargando bot\363n 7 de 15: bot\363n de CICLICA II");
        add(BtnCiclica2);
        BtnCiclica2.putTitulo("Ciclica II");
        mens.setText("Panel 2 de 3. Cargando bot\363n 8 de 15: potenci\363metro de YPOS I");
        BtnYPos1 = new RotateBitmap(DirImagenes, fino, ValoresYPos);
        add(BtnYPos1);
        BtnYPos1.putTitulo("YPos I");
        mens.setText("Panel 2 de 3. Cargando bot\363n 9 de 15: potenci\363metro de YPOS II");
        BtnYPos2 = new RotateBitmap(DirImagenes, fino, ValoresYPos);
        add(BtnYPos2);
        BtnYPos2.putTitulo("YPos II");
        mens.setText("Panel 2 de 3. Cargando bot\363n 10 de 15: potenci\363metro de V/DIV I");
        BtnVolt1 = new ButtonVolt(DirImagenes, Voltios, ValTension, fino, ValoresFinos);
        add(BtnVolt1);
        BtnVolt1.putTitulo("V/DIV I");
        mens.setText("Panel 2 de 3. Cargando bot\363n 11 de 15: potenci\363metro de V/DIV II");
        BtnVolt2 = new ButtonVolt(DirImagenes, Voltios, ValTension, fino, ValoresFinos);
        add(BtnVolt2);
        BtnVolt2.putTitulo("V/DIV II");
        mens.setText("Panel 2 de 3. Cargando bot\363n 12 de 15: conmutador de AC/DC/GND I");
        BtnAcDc1 = new CommutatorACDC(DirImagenes, AcDc);
        add(BtnAcDc1);
        BtnAcDc1.setJustificacion(true);
        mens.setText("Panel 2 de 3. Cargando bot\363n 13 de 15: conmutador de AC/DC/GND II");
        BtnAcDc2 = new CommutatorACDC(DirImagenes, AcDc);
        add(BtnAcDc2);
        BtnAcDc2.setJustificacion(false);
        mens.setText("Panel 2 de 3. Cargando bot\363n 14 de 15: ENTRADA I");
        BtnCanal1 = new StaticBitmapButton(DirImagenes, canal1);
        add(BtnCanal1);
        BtnCanal1.putTitulo("Ent. I");
        mens.setText("Panel 2 de 3. Cargando bot\363n 15 de 15: ENTRADA II");
        BtnCanal2 = new StaticBitmapButton(DirImagenes, canal2);
        add(BtnCanal2);
        BtnCanal2.putTitulo("Ent. II");
        mens.closeDialog();
    }

    public void posicionarElementos()
    {
        BtnYPos1.setLocation(1, 3);
        BtnYPos2.setLocation(347, 3);
        BtnAcDc1.setLocation(4, 71);
        BtnAcDc2.setLocation(357, 71);
        BtnCanal1.setLocation(4, 119);
        BtnCanal2.setLocation(350, 119);
        BtnVolt1.setLocation(59, 3);
        BtnVolt2.setLocation(200, 3);
        BtnCiclica1.setLocation(57, 156);
        BtnCiclica2.setLocation(299, 156);
        BtnInv1.setLocation(104, 156);
        BtnInv2.setLocation(265, 156);
        BtnCH12.setLocation(140, 156);
        BtnDual.setLocation(184, 156);
        BtnAdd.setLocation(223, 156);
    }

    public Dimension getMinimumSize()
    {
        return new Dimension(396, 197);
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
    }

    public void DibujaFondo(Graphics g)
    {
        g.setColor(PFondo.relleno);
        g.fillRoundRect(0, 0, 396, 197, 10, 10);
    }
}
