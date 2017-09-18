package es.upv.oscilloscope;

import java.awt.*;
import java.awt.event.*;
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
//            Preview, Senyal

public class DlgOpAdq extends Dialog
{
    class DlgOpAdqMouseAdapter extends MouseAdapter
    {

        public void mouseClicked(MouseEvent e)
        {
            if(e.getSource() == btnCancelar)
            {
                opcion = DlgOpAdq.CANCELAR;
            }
            if(e.getSource() == btnArchivo)
            {
                opcion = DlgOpAdq.ARCHIVO;
            }
            if(e.getSource() == btnPuerto)
            {
                opcion = DlgOpAdq.PUERTO;
            }
            prevPantalla.stop();
            setVisible(false);
        }

        DlgOpAdqMouseAdapter()
        {
        }
    }

    class DlgOpAdqWindowAdapter extends WindowAdapter
    {

        public void windowClosing(WindowEvent e)
        {
            prevPantalla.stop();
            opcion = DlgOpAdq.CANCELAR;
            setVisible(false);
        }

        DlgOpAdqWindowAdapter()
        {
        }
    }


    private static Frame fFrame;
    public static int CANCELAR = 0;
    public static int PUERTO = 1;
    public static int ARCHIVO = 2;
    private int opcion;
    private Preview prevPantalla;
    private Button btnPuerto;
    private Button btnArchivo;
    private Button btnCancelar;

    private DlgOpAdq(Frame parent)
    {
        super(parent, "Adquirir una se\361al", false);
        opcion = CANCELAR;
        prevPantalla = new Preview();
        btnPuerto = new Button("Adquirir a trav\351s del puerto");
        btnArchivo = new Button("Adquirir a trav\351s de un archivo");
        btnCancelar = new Button("Cancelar");
        add(prevPantalla);
        add(btnPuerto);
        add(btnArchivo);
        add(btnCancelar);
        DlgOpAdqMouseAdapter mouseAdapter = new DlgOpAdqMouseAdapter();
        addWindowListener(new DlgOpAdqWindowAdapter());
        btnPuerto.addMouseListener(mouseAdapter);
        btnArchivo.addMouseListener(mouseAdapter);
        btnCancelar.addMouseListener(mouseAdapter);
    }

    public void addNotify()
    {
        super.addNotify();
        setSize(433, 269);
        setLayout(null);
        Dimension dim = getToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
        prevPantalla.setLocation(248, 27);
        btnPuerto.setBounds(7, 27, 234, 69);
        btnArchivo.setBounds(7, 103, 234, 69);
        btnCancelar.setBounds(7, 178, 234, 69);
        prevPantalla.start();
    }

    public synchronized int getOpcion()
    {
        return opcion;
    }

    public synchronized int showModal()
    {
        setModal(true);
        setVisible(true);
        return opcion;
    }

    public synchronized int showModal(Signal sig)
    {
        prevPantalla.setSenyal(sig);
        return showModal();
    }

    public static synchronized DlgOpAdq makeDialog()
    {
        if(fFrame == null)
        {
            fFrame = new Frame();
        }
        DlgOpAdq result = new DlgOpAdq(fFrame);
        return result;
    }
}
