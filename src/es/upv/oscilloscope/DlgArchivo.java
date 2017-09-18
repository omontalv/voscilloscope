package es.upv.oscilloscope;

import es.upv.simulator.Tokenizer;
import java.awt.Button;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Label;
import java.awt.List;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StreamTokenizer;
import java.net.MalformedURLException;
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
//            Preview, Senyal

public class DlgArchivo extends Dialog
{
    class DlgArchivoMouseAdapter extends MouseAdapter
    {

        public void mouseClicked(MouseEvent e)
        {
            if(e.getSource() == btnAceptar)
            {
                if(listArchivos.getSelectedIndex() == -1)
                {
                    aceptar = false;
                } else
                {
                    aceptar = true;
                }
                prevPantalla.stop();
                setVisible(false);
            }
            if(e.getSource() == btnCancelar)
            {
                aceptar = false;
                prevPantalla.stop();
                setVisible(false);
            }
            if(e.getSource() == listArchivos)
            {
                try
                {
                    if(listArchivos.getSelectedIndex() != -1)
                    {
                        Signal sig = new Signal((new URL(dir + listArchivos.getSelectedItem())).openStream());
                        prevPantalla.setSenyal(sig);
                    }
                }
                catch(MalformedURLException mue)
                {
                    System.out.println("Archivo no encontrado.\n" + mue);
                }
                catch(IOException ioe)
                {
                    System.out.println("Error al abrir el archivo.\n" + ioe);
                }
            }
        }

        DlgArchivoMouseAdapter()
        {
        }
    }

    class DlgArchivoWindowAdapter extends WindowAdapter
    {

        public void windowClosing(WindowEvent e)
        {
            prevPantalla.stop();
            aceptar = false;
            setVisible(false);
        }

        DlgArchivoWindowAdapter()
        {
        }
    }


    private Button btnAceptar;
    private Button btnCancelar;
    private List listArchivos;
    private static Frame fFrame;
    private boolean aceptar;
    private URL dir;
    private Preview prevPantalla;
    private TextField txtTitulo;
    private TextArea txtDesc;
    private Label lArchivos;
    private Label lTitulo;
    private Label lDesc;

    public DlgArchivo(Frame parent, URL direc)
    {
        super(parent, "Adquisici\363n de una se\361al a trav\351s de un archivo", false);
        dir = direc;
        btnAceptar = new Button("Aceptar");
        btnCancelar = new Button("Cancelar");
        listArchivos = new List(2, false);
        prevPantalla = new Preview();
        txtTitulo = new TextField("Sin t\355tulo");
        txtDesc = new TextArea("Sin descripci\363n");
        lArchivos = new Label("Se\361ales:");
        lTitulo = new Label("T\355tulo:");
        lDesc = new Label("Descripci\363n:");
        add(btnAceptar);
        add(btnCancelar);
        add(listArchivos);
        add(prevPantalla);
        add(txtTitulo);
        add(txtDesc);
        add(lArchivos);
        add(lTitulo);
        add(lDesc);
        DlgArchivoMouseAdapter mouseAdapter = new DlgArchivoMouseAdapter();
        btnAceptar.addMouseListener(mouseAdapter);
        btnCancelar.addMouseListener(mouseAdapter);
        listArchivos.addMouseListener(mouseAdapter);
        addWindowListener(new DlgArchivoWindowAdapter());
    }

    public void addNotify()
    {
        super.addNotify();
        int margenlat = getInsets().left;
        int margensup = getInsets().top;
        setSize(489 + margenlat + getInsets().right, 297 + margensup + getInsets().bottom);
        setLayout(null);
        Dimension dim = getToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
        prevPantalla.setLocation(312 + margenlat, 24 + margensup);
        btnAceptar.setBounds(312 + margenlat, 248 + margensup, 81, 41);
        btnCancelar.setBounds(403 + margenlat, 248 + margensup, 81, 41);
        listArchivos.setBounds(5 + margenlat, 24 + margensup, 297, 113);
        txtTitulo.setBounds(8 + margenlat, 160 + margensup, 297, 21);
        txtDesc.setBounds(8 + margenlat, 200 + margensup, 297, 89);
        lArchivos.setLocation(8 + margenlat, 4 + margensup);
        lArchivos.setSize(lArchivos.getPreferredSize());
        lTitulo.setLocation(8 + margenlat, 140 + margensup);
        lTitulo.setSize(lTitulo.getPreferredSize());
        lDesc.setLocation(8 + margenlat, 180 + margensup);
        lDesc.setSize(lDesc.getPreferredSize());
        listarDirectorio();
        prevPantalla.start();
    }

    public void listarDirectorio()
    {
        try
        {
            Tokenizer dir = new Tokenizer((new URL(this.dir + "directorio.txt")).openStream());
            listArchivos.removeAll();
            do
            {
                if(dir.buscarArchivo(".sig") != null)
                {
                    listArchivos.add(((StreamTokenizer) (dir)).sval);
                }
            } while(((StreamTokenizer) (dir)).ttype != -1);
        }
        catch(IOException e)
        {
            System.out.println("No puedo abrir el flujo");
        }
    }

    public synchronized boolean showModal()
    {
        aceptar = false;
        setModal(true);
        setVisible(true);
        return aceptar;
    }

    public static synchronized DlgArchivo makeDialog(URL dir)
    {
        if(fFrame == null)
        {
            fFrame = new Frame();
        }
        DlgArchivo result = new DlgArchivo(fFrame, dir);
        return result;
    }

    public String getFile()
    {
        return listArchivos.getItem(listArchivos.getSelectedIndex());
    }
}

