package es.upv.simulator;

import java.awt.*;

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

public class Mensaje extends Dialog
{

    Button btnAceptar;
    private Label lMensaje;
    private static Frame fFrame;
    boolean aceptar;

    private Mensaje(Frame parent, String text, String caption, boolean aceptar)
    {
        super(parent, caption, aceptar);
        setModal(aceptar);
        btnAceptar = new Button("Aceptar");
        lMensaje = new Label(text, 1);
        add(btnAceptar);
        add(lMensaje);
        this.aceptar = aceptar;
    }

    public void addNotify()
    {
        super.addNotify();
        setLayout(null);
        setSize();
        btnAceptar.setVisible(aceptar);
        lMensaje.setVisible(true);
    }

    public synchronized void closeDialog()
    {
        setVisible(false);
        dispose();
    }

    public void setSize()
    {
        FontMetrics fm = getFontMetrics(getFont());
        int ancho = lMensaje.getPreferredSize().width;
        int alto = lMensaje.getMinimumSize().height;
        if(aceptar)
        {
            alto += btnAceptar.getMinimumSize().height;
        }
        setSize(ancho + 20, alto + 50);
        lMensaje.setLocation(10, 20);
        lMensaje.setSize(lMensaje.getPreferredSize());
        ancho = btnAceptar.getMinimumSize().width;
        btnAceptar.setLocation(getSize().width / 2 - ancho / 2, lMensaje.getMinimumSize().height + 20);
        btnAceptar.setSize(btnAceptar.getMinimumSize());
        Dimension dim = getToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
    }

    public void setText(String text)
    {
        lMensaje.setText(text);
        btnAceptar.setVisible(aceptar);
    }

    public static synchronized Mensaje makeDialog(String text, String caption, boolean modal)
    {
        if(fFrame == null)
        {
            fFrame = new Frame();
        }
        Mensaje mens = new Mensaje(fFrame, text, caption, modal);
        mens.setVisible(true);
        return mens;
    }
}
