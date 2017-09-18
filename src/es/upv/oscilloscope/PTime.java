package es.upv.oscilloscope;

import es.upv.simulator.*;
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
//            ButtonTime, PFondo

public class PTime extends Panel implements MouseListener
{

    public ButtonTime btnTime;
    public InterruptBitmap btnAtNormal;
    public RotateBitmap btnLevel;
    public InterruptBitmap btnPower;
    public BooleanBitmap btnLed;
    public RotateBitmap btnIntens;
    public RotateBitmap btnFoco;
    public RotateBitmap btnXPos;
    public InterruptBitmap btnXY;
    public RotateBitmap btnHoldOff;
    public InterruptBitmap btnMasMenos;
    public Scrollbar btnPosicion;
    Label lPosicion;

    public PTime(URL DirImagenes)
    {
        setBackground(PFondo.fondo);
        setForeground(PFondo.panel);
        setLayout(null);
        construirElementos(DirImagenes);
    }

    public void iniciarElementos()
    {
        btnTime.setValor(0.2F);
        btnTime.setValorFino(0.0F);
        btnAtNormal.setValor(false);
        btnLevel.setValor(0.0F);
        btnPower.setValor(false);
        btnLed.setValor(btnPower.getValor());
        btnIntens.setValor(255F);
        btnFoco.setValor(2.0F);
        btnXPos.setValor(19F);
        btnXY.setValor(false);
        btnHoldOff.setValor(0.0F);
        btnMasMenos.setValor(false);
    }

    public void addNotify()
    {
        super.addNotify();
        posicionarElementos();
        iniciarElementos();
    }

    public void mouseClicked(MouseEvent e)
    {
        if(e.getSource() == btnPower)
        {
            btnLed.setValor(btnPower.getValor());
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

    private void construirElementos(URL DirImagenes)
    {
        String fino[] = {
            "bb00.gif", "bb01.gif", "bb02.gif", "bb03.gif", "bb04.gif", "bb05.gif", "bb06.gif", "bb07.gif", "bb08.gif", "bb09.gif",
            "bb10.gif", "bb11.gif", "bb12.gif", "bb13.gif", "bb14.gif", "bb15.gif", "bb16.gif", "bb17.gif", "bb18.gif", "bb19.gif",
            "bb20.gif", "bb21.gif", "bb22.gif", "bb23.gif", "bb24.gif", "bb25.gif", "bb26.gif", "bb27.gif", "bb28.gif", "bb29.gif",
            "bb31.gif", "bb32.gif", "bb33.gif", "bb34.gif", "bb35.gif", "bb36.gif", "bb37.gif", "bb38.gif", "bb39.gif", "bb40.gif",
            "bb41.gif", "bb42.gif", "bb43.gif", "bb44.gif", "bb45.gif"
        };
        String Tiempos[] = {
            "ba29.gif", "ba31.gif", "ba33.gif", "ba35.gif", "ba37.gif", "ba39.gif", "ba41.gif", "ba43.gif", "ba45.gif", "ba01.gif",
            "ba03.gif", "ba05.gif", "ba07.gif", "ba09.gif", "ba11.gif", "ba13.gif", "ba15.gif", "ba17.gif"
        };
        String pulsador[] = {
            "Bc01.gif", "Bc00.gif"
        };
        String Led[] = {
            "led1.gif", "led0.gif"
        };
        float ValoresTiempo[] = {
            5E-007F, 1E-006F, 2E-006F, 5E-006F, 1E-005F, 2E-005F, 5E-005F, 0.0001F, 0.0002F, 0.0005F,
            0.001F, 0.002F, 0.005F, 0.01F, 0.02F, 0.05F, 0.1F, 0.2F
        };
        float ValoresFinos[] = new float[46];
        for(int i = 0; i < 46; i++)
        {
            ValoresFinos[i] = (float)((double)i * 0.5D);
        }

        float ValoresIntens[] = new float[25];
        for(int i = 0; i < 25; i++)
        {
            ValoresIntens[i] = (i * 255) / 24;
        }

        float ValoresFoco[] = new float[18];
        for(int i = 0; i < 18; i++)
        {
            ValoresFoco[i] = i;
        }

        float ValoresXPos[] = new float[40];
        for(int i = 0; i < 40; i++)
        {
            ValoresXPos[i] = i - 20;
        }

        float ValoresHold[] = new float[46];
        for(int i = 0; i < 46; i++)
        {
            ValoresHold[i] = (i * 100) / 45;
        }

        float ValoresLevel[] = new float[93];
        for(int i = 0; i < 93; i++)
        {
            ValoresLevel[i] = (i * 100) / 46 - 100;
        }

        Mensaje mens = Mensaje.makeDialog("              Panel 1 de 3. Cargando bot\363n 1 de 12: bot\363n de POWER        " +
"     "
, "Informaci\363n", false);
        btnPower = new InterruptBitmap(DirImagenes, pulsador);
        add(btnPower);
        btnPower.putTitulo("POWER");
        btnPower.addMouseListener(this);
        mens.setText("Panel 1 de 3. Cargando bot\363n 2 de 12: bot\363n de XY");
        btnXY = new InterruptBitmap(DirImagenes, pulsador);
        add(btnXY);
        btnXY.putTitulo("XY");
        btnXY.addMouseListener(this);
        mens.setText("Panel 1 de 3. Cargando bot\363n 3 de 12: bot\363n de AT/NORMAL");
        btnAtNormal = new InterruptBitmap(DirImagenes, pulsador);
        add(btnAtNormal);
        btnAtNormal.putTitulo("At/Normal");
        btnAtNormal.addMouseListener(this);
        mens.setText("Panel 1 de 3. Cargando bot\363n 4 de 12: bot\363n de +/-");
        btnMasMenos = new InterruptBitmap(DirImagenes, pulsador);
        add(btnMasMenos);
        btnMasMenos.putTitulo("+/-");
        btnMasMenos.addMouseListener(this);
        mens.setText("Panel 1 de 3. Cargando bot\363n 5 de 12: Luz de encendido");
        btnLed = new BooleanBitmap(DirImagenes, Led);
        add(btnLed);
        btnLed.addMouseListener(this);
        mens.setText("Panel 1 de 3. Cargando bot\363n 6 de 12: potenci\363metro de INTENSIDAD");
        btnIntens = new RotateBitmap(DirImagenes, fino, ValoresIntens);
        add(btnIntens);
        btnIntens.putTitulo("Intens");
        btnIntens.addMouseListener(this);
        mens.setText("Panel 1 de 3. Cargando bot\363n 7 de 12: potenci\363metro de FOCO");
        btnFoco = new RotateBitmap(DirImagenes, fino, ValoresFoco);
        add(btnFoco);
        btnFoco.putTitulo("Foco");
        btnFoco.addMouseListener(this);
        mens.setText("Panel 1 de 3. Cargando bot\363n 8 de 12: potenci\363metro de X POS");
        btnXPos = new RotateBitmap(DirImagenes, fino, ValoresXPos);
        add(btnXPos);
        btnXPos.putTitulo("XPos");
        btnXPos.addMouseListener(this);
        mens.setText("Panel 1 de 3. Cargando bot\363n 9 de 12: potenci\363metro de HOLD OFF");
        btnHoldOff = new RotateBitmap(DirImagenes, fino, ValoresHold);
        add(btnHoldOff);
        btnHoldOff.putTitulo("HoldOff");
        btnHoldOff.addMouseListener(this);
        mens.setText("Panel 1 de 3. Cargando bot\363n 10 de 12: potenci\363metro de LEVEL");
        btnLevel = new RotateBitmap(DirImagenes, fino, ValoresLevel);
        add(btnLevel);
        btnLevel.putTitulo("Level");
        btnLevel.addMouseListener(this);
        mens.setText("Panel 1 de 3. Cargando bot\363n 11 de 12: potenci\363metro de TIME");
        btnTime = new ButtonTime(DirImagenes, Tiempos, ValoresTiempo, fino, ValoresFinos);
        add(btnTime);
        btnTime.putTitulo("TIME");
        btnTime.addMouseListener(this);
        mens.setText("Panel 1 de 3. Cargando bot\363n 12 de 12: barra de POSICI\323N");
        btnPosicion = new Scrollbar(0);
        add(btnPosicion);
        btnPosicion.setVisible(false);
        lPosicion = new Label("POSICI\323N");
        add(lPosicion);
        lPosicion.setVisible(false);
        lPosicion.setForeground(Color.orange);
        lPosicion.setBackground(PFondo.relleno);
        mens.closeDialog();
    }

    public void posicionarElementos()
    {
        btnPower.setLocation(8, 2);
        btnLed.setLocation(58, 21);
        btnXY.setLocation(88, 2);
        btnTime.setLocation(167, 2);
        btnAtNormal.setLocation(335, 2);
        btnIntens.setLocation(8, 38);
        btnFoco.setLocation(8, 104);
        btnXPos.setLocation(88, 38);
        btnHoldOff.setLocation(85, 104);
        btnLevel.setLocation(344, 51);
        btnMasMenos.setLocation(357, 128);
        btnPosicion.setBounds(142, 145, 240, 15);
        lPosicion.setLocation(77, 142);
        lPosicion.setSize(lPosicion.getMinimumSize());
    }

    public Dimension getMinimumSize()
    {
        return new Dimension(396, 168);
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
        g.fillRoundRect(0, 0, 396, 168, 10, 10);
    }
}
