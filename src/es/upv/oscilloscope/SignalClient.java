package es.upv.oscilloscope;

import es.upv.simulator.SuspendThread;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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

public class SignalClient extends SuspendThread
{

    private byte datos[];
    private boolean senyal;
    static final int ADPORT = 5454;
    private GestionSenyales senyales = null;

    public SignalClient(GestionSenyales senyales)
    {
        this.senyales = senyales;
    }

    public synchronized void borraSenyal()
    {
        senyal = false;
        datos = null;
    }

    public synchronized boolean hasSenyal()
    {
        return senyal;
    }

    public synchronized int getNMuestras()
    {
        return senyal ? datos.length : 0;
    }

    public synchronized byte[] getDatos()
    {
        return datos;
    }

    public synchronized void init()
    {
        borraSenyal();
    }

    public void run()
    {
        try
        {
            ServerSocket ss = new ServerSocket(5454);
            ss.setSoTimeout(0);
            do
            {
                System.out.println("Esperando conexi\363n");
                Socket s = ss.accept();
                System.out.println("Conexi\363n aceptada");
                BufferedInputStream in = new BufferedInputStream(s.getInputStream());
                ByteArrayOutputStream out = new ByteArrayOutputStream();

                synchronized(this)
                {
                    borraSenyal();
                    do
                    {
                        byte datos[] = new byte[in.available()];
                        System.out.println(datos.length);
                        if(in.read(datos) == -1) break;

                        out.write(datos);

                    }
                    while(true);
                    datos = out.toByteArray();
System.out.println(datos.length);
                    setSignal(datos);
                    senyal = true;
                }
                in.close();
                s.close();
            } while(true);
        }
        catch(IOException e)
        {
            System.out.println("Error en la conexi\363n");
        }
    }

    private void setSignal(byte datos[])
    {
        if(senyales==null){
            System.out.println("[Error] La variable senyales es nula!!!");
            return;
        }
        if(!senyales.isSuspend())
        {
            senyales.suspender();
        }
        senyales.canal1.conectaSenyal(datos);
        senyales.rebobinar();
        if(senyales.isSuspend())
        {
            senyales.reanudar();
        }
    }
}

